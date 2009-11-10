package org.homs.gamba.connectionpool;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class GambaPoolingTest {

	/**
	 * testa la captura i recuperació de connexions, i executa sentències
	 *
	 * @throws SQLException
	 */
	@Test
	public void test1() throws SQLException {
		final GambaPooling pool = GambaPooling.getInstance();

		Assert.assertEquals(3, pool.poolCurrentSize());

		final Connection conn = pool.getConnection();

		Assert.assertEquals(2, pool.poolCurrentSize());

		final Statement st = conn.createStatement();
		final String createSentence = "CREATE TABLE TEST (" + "	ID INTEGER PRIMARY KEY, "
				+ "	VALUE VARCHAR(100) " + ")";
		st.executeUpdate(createSentence);

		final String dropSentence = "DROP TABLE TEST";
		st.executeUpdate(dropSentence);

		pool.releaseConnection(conn);

		Assert.assertEquals(3, pool.poolCurrentSize());

		pool.clearConnections();
	}

	/**
	 * testa el reciclatge de connexions tenint en compte el tamany de pool: es
	 * controla l'overflow i underflou del tamany configurat
	 *
	 * @throws SQLException
	 */
	@Test
	public void test2() throws SQLException {
		final GambaPooling pool = GambaPooling.getInstance();

		final List<Connection> conns = new ArrayList<Connection>();

		Assert.assertEquals(3, pool.poolCurrentSize());
		conns.add(pool.getConnection());
		Assert.assertEquals(2, pool.poolCurrentSize());
		conns.add(pool.getConnection());
		Assert.assertEquals(1, pool.poolCurrentSize());
		conns.add(pool.getConnection());
		Assert.assertEquals(0, pool.poolCurrentSize());
		conns.add(pool.getConnection());
		Assert.assertEquals(0, pool.poolCurrentSize());
		conns.add(pool.getConnection());
		Assert.assertEquals(0, pool.poolCurrentSize());

		pool.releaseConnection(conns.get(0));
		conns.remove(0);
		Assert.assertEquals(1, pool.poolCurrentSize());
		pool.releaseConnection(conns.get(0));
		conns.remove(0);
		Assert.assertEquals(2, pool.poolCurrentSize());
		pool.releaseConnection(conns.get(0));
		conns.remove(0);
		Assert.assertEquals(3, pool.poolCurrentSize());
		pool.releaseConnection(conns.get(0));
		conns.remove(0);
		Assert.assertEquals(3, pool.poolCurrentSize());
		pool.releaseConnection(conns.get(0));
		conns.remove(0);
		Assert.assertEquals(3, pool.poolCurrentSize());

		pool.clearConnections();
	}

}
