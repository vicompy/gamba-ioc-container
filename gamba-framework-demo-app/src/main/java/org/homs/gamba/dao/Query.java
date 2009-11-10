package org.homs.gamba.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.homs.demo.models.Artifact;
import org.homs.gamba.binding.IBeanBinder;
import org.homs.gamba.binding.SingletonCachedBeanBinder;
import org.homs.gamba.connectionpool.GambaPooling;

public class Query<T> extends GenericMappingQuery {

	private final IBeanBinder bm = SingletonCachedBeanBinder.getInstance();

	public List<T> execute(final String query, final List<T> list) {

		try {
			final Connection conn = GambaPooling.getInstance().getConnection();
			final PreparedStatement ps = conn.prepareStatement(query);
			final ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(bind(rs));
			}

			GambaPooling.getInstance().releaseConnection(conn);

		} catch (final SQLException exc) {
			throw new RuntimeException(exc);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	protected T bind(final ResultSet rs) {
		return (T) bm.doBind(Artifact.class, getValueMap(rs));
	}

}

