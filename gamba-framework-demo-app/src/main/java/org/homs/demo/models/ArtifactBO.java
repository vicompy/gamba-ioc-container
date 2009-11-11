//package org.homs.demo.models;
//
//import java.sql.Connection;
//import java.util.List;
//
//import org.homs.gamba.connectionpool.GambaPooling;
//
//public class ArtifactBO {
//
////	final IArtifactDao artifactDao = new ArtifactDaoImpl();
//
//	public List<Artifact> search(final String searchWord, final int dept) {
//		final Connection conn = GambaPooling.getInstance().getConnection();
//
//		final List<Artifact> r = new ArtifactDaoImpl().findBy(searchWord, dept);
//
//		GambaPooling.getInstance().releaseConnection(conn);
//
//		return r;
//	}
//
//	public Long artifactsCount() {
//		return new ArtifactDaoImpl().artifactsCount();
//	}
//
//}
