package org.homs.gamba.extras;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.homs.gamba.connectionpool.Pool;

public abstract class JdbcQuery<T> {
	protected abstract T bind(final ResultSet rs) throws SQLException;

	public List<T> execute(final String query, final List<T> list) {
		Connection conn = null;
		try {

			conn = Pool.getInstance().getConnection();
			final PreparedStatement ps = conn.prepareStatement(query);
			final ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(bind(rs));
			}

			Pool.getInstance().releaseConnection(conn);

		} catch (final SQLException exc) {
			throw new RuntimeException(exc);
		}

		return list;
	}
}

