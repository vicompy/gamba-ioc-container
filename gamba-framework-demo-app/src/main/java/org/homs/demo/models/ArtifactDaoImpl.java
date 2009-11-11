//package org.homs.demo.models;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.homs.gamba.dao.Query;
//
//public class ArtifactDaoImpl implements IArtifactDao {
//
//	/**
//	 * @see org.homs.demo.models.IArtifactDao#findBy(java.lang.String)
//	 */
//	public List<Artifact> findBy(final String searchWord, final int dept) {
//		return new Query<Artifact>().execute(
//			"SELECT * " +
//			"FROM ARTIFACTS " +
//			"WHERE (" +
//			"	GROUPID LIKE '%" + searchWord + "%' OR " +
//			"	ARTIFACTID LIKE '%" + searchWord + "%' OR " +
//			"	VERSION LIKE '%" + searchWord + "%' OR " +
//			"	JARNAME LIKE '%" + searchWord + "%' " +
//			") AND DEPT <= " + dept,
//			new ArrayList<Artifact>()
//		);
//	}
//
//	public Long artifactsCount() {
//		return Long.valueOf(Query.integerCount("SELECT COUNT(*) FROM ARTIFACTS"));
//	}
//}
