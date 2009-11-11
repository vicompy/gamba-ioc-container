//package org.homs.gamba.dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//import org.homs.demo.models.Artifact;
//import org.homs.gamba.binding.BeanBinder;
//import org.homs.gamba.binding.IBeanBinder;
//import org.homs.gamba.connectionpool.GambaPooling;
//
//@Deprecated
//public class Query<T> extends GenericMappingQuery {
//
//	private final IBeanBinder bm = new BeanBinder();
//
//	public List<T> execute(final String query, final List<T> list) {
//
//		try {
//			final Connection conn = GambaPooling.getInstance().getConnection();
//			final PreparedStatement ps = conn.prepareStatement(query);
//			final ResultSet rs = ps.executeQuery();
//
//			while (rs.next()) {
//				list.add(bind(rs));
//			}
//
//			GambaPooling.getInstance().releaseConnection(conn);
//
//		} catch (final SQLException exc) {
//			throw new RuntimeException(exc);
//		}
//
//		return list;
//	}
//
//	@SuppressWarnings("unchecked")
//	protected T bind(final ResultSet rs) {
//		return (T) bm.doBind(Artifact.class, getValueMap(rs));
//	}
//
//	public static Integer integerCount(final String query) {
//
//		final Integer r;
//		Connection conn = null;
//		try {
//
//			conn = GambaPooling.getInstance().getConnection();
//			final PreparedStatement ps = conn.prepareStatement(query);
//			final ResultSet rs = ps.executeQuery();
//
//			rs.next();
//			r = rs.getInt(1);
//
//			GambaPooling.getInstance().releaseConnection(conn);
//
//		} catch (final SQLException exc) {
//			throw new RuntimeException(exc);
//		}
//
//		return r;
//	}
//}
