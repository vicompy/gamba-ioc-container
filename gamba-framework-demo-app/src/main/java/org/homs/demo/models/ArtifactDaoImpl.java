package org.homs.demo.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.homs.gamba.extras.JdbcQuery;
import org.homs.gamba.extras.JdbcQueryCount;

public class ArtifactDaoImpl implements IArtifactDao {

	/**
	 * @see org.homs.demo.models.IArtifactDao#findBy(java.lang.String)
	 */
	public List<Artifact> findBy(final String searchWord, final int dept) {
		return new SelectArtifacts().execute(
			"SELECT * " +
			"FROM ARTIFACTS " +
			"WHERE " +
			"	GROUPID LIKE '%"+searchWord+"%' OR " +
			"	ARTIFACTID LIKE '%"+searchWord+"%' OR " +
			"	VERSION LIKE '%"+searchWord+"%' OR " +
			"	JARNAME LIKE '%"+searchWord+"%' "
			, new ArrayList<Artifact>());
	}

	class SelectArtifacts extends JdbcQuery<Artifact> {
		@Override
		protected Artifact bind(final ResultSet rs) throws SQLException {
			final Artifact artifact = new Artifact();
			artifact.setGroupId(rs.getString("GROUPID"));
			artifact.setArtifactId(rs.getString("ARTIFACTID"));
			artifact.setVersion(rs.getString("VERSION"));
			artifact.setJarName(rs.getString("JARNAME"));
			artifact.setUrl(rs.getString("URL"));
			return artifact;
		}
	}

	public Long artifactsCount() {
		return Long.valueOf(new JdbcQueryCount().integerCount("SELECT COUNT(*) FROM ARTIFACTS"));
	}
}
