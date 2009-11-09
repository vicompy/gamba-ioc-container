package org.homs.demo.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.homs.gamba.connectionpool.BasicPool;

public class ArtifactDaoImpl implements IArtifactDao {

	/**
	 * @see org.homs.demo.models.IArtifactDao#findBy(java.lang.String)
	 */
	public List<Artifact> findBy(final String searchWord, final int dept) {

		final List<Artifact> l = new ArrayList<Artifact>();

		Connection conn = null;
		try {

			conn = BasicPool.getInstance().getConnection();
			final PreparedStatement ps = conn.prepareStatement("SELECT * FROM ARTIFACTS");
			final ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				final Artifact a = new Artifact();
				a.setGroupId(rs.getString("GROUPID"));
				a.setArtifactId(rs.getString("ARTIFACTID"));
				a.setVersion(rs.getString("VERSION"));
				a.setJarName(rs.getString("JARNAME"));
				a.setUrl(rs.getString("URL"));
				l.add(a);
			}

			BasicPool.getInstance().releaseConnection(conn);
			return l;

		} catch (final SQLException exc) {
			throw new RuntimeException(exc);
		}

	}

	public Long artifactsCount() {

		Connection conn = null;
		try {

			conn = BasicPool.getInstance().getConnection();
			final PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM ARTIFACTS");
			final ResultSet rs = ps.executeQuery();

			rs.next();
			final Long r = rs.getLong(1);

			BasicPool.getInstance().releaseConnection(conn);
			return r;

		} catch (final SQLException exc) {
			throw new RuntimeException(exc);
		}

	}
}
