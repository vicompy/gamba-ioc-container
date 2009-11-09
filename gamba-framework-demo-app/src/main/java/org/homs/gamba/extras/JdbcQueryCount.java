package org.homs.gamba.extras;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.homs.gamba.connectionpool.BasicConnectionPool;

public class JdbcQueryCount {

	public Integer integerCount(final String query) {

		final Integer r;
		Connection conn = null;
		try {

			conn = BasicConnectionPool.getInstance().getConnection();
			final PreparedStatement ps = conn.prepareStatement(query);
			final ResultSet rs = ps.executeQuery();

			rs.next();
			r = rs.getInt(1);

			BasicConnectionPool.getInstance().releaseConnection(conn);

		} catch (final SQLException exc) {
			throw new RuntimeException(exc);
		}

		return r;
	}
}
