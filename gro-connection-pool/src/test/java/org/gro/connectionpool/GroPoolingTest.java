package org.gro.connectionpool;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.Stack;

import org.gro.logging.GroLog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GroPoolingTest {

	private static final IPoolLogger logger = new IPoolLogger() {
		private final GroLog log = GroLog.getGroLogger(GroPooling.class);

		public void receiveMessage(final String message) {
			log.info(message);
		}
	};

	private GroPooling pool;

	@Before
	public void beforeMethod() {
		pool = GroPooling.getInstance();
	}

	@Test
	public void test1() {

		GroLog.getGroLogger(this.getClass()).config();
		pool.setLogger(logger);

		final Stack<Connection> used = new Stack<Connection>();

		assertEquals(0, pool.getConnectionsInUse());
		assertEquals(pool.getPoolMaxSize(), pool.getPoolSize());

		used.push(pool.getConnection());

		assertEquals(1, pool.getConnectionsInUse());
		assertEquals(pool.getPoolMaxSize()-1, pool.getPoolSize());

		used.push(pool.getConnection());
		used.push(pool.getConnection());
		used.push(pool.getConnection());

		assertEquals(4, pool.getConnectionsInUse());
		assertEquals(pool.getPoolMaxSize()-4, pool.getPoolSize());

		used.push(pool.getConnection());

		assertEquals(5, pool.getConnectionsInUse());
		assertEquals(0, pool.getPoolSize());

		used.push(pool.getConnection());

		assertEquals(6, pool.getConnectionsInUse());
		assertEquals(0, pool.getPoolSize());

		used.push(pool.getConnection());

		assertEquals(7, pool.getConnectionsInUse());
		assertEquals(0, pool.getPoolSize());

		used.push(pool.getConnection());

		assertEquals(8, pool.getConnectionsInUse());
		assertEquals(0, pool.getPoolSize());

		pool.releaseConnection(used.pop());
		pool.releaseConnection(used.pop());
		pool.releaseConnection(used.pop());

		assertEquals(5, pool.getConnectionsInUse());
		assertEquals(3, pool.getPoolSize());

		pool.releaseConnection(used.pop());
		pool.releaseConnection(used.pop());
		pool.releaseConnection(used.pop());

		assertEquals(2, pool.getConnectionsInUse());
		assertEquals(5, pool.getPoolSize());

		pool.releaseConnection(used.pop());
		pool.releaseConnection(used.pop());

		assertEquals(0, pool.getConnectionsInUse());
		assertEquals(5, pool.getPoolSize());
	}

	/**
	 * no asserta a cada pas, però corre un montón de vegades a veure si es perd
	 * alguna connexió
	 */
	@Test
	public void test2() {
		pool.setLogger(null);

		final Stack<Connection> used = new Stack<Connection>();

		for (int k = 0; k < 100; k++) {
			for (int i = 0; i < 10; i++) {
				used.push(pool.getConnection());
			}
			for (int i = 0; i < 10; i++) {
				pool.releaseConnection(used.pop());
			}
		}
	}

	@After
	public void afterMethod() {
		pool.clearConnections();
		pool.destroyAllConnections();
	}

}
