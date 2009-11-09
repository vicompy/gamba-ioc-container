package org.homs.demo.models;

import java.util.List;

public class ArtifactService {

	final IArtifactDao artifactDao = new ArtifactDaoImpl();

	public List<Artifact> search(final String searchWord, final int dept) {
		return artifactDao.findBy(searchWord, dept);
	}

	public Long artifactsCount() {
		return artifactDao.artifactsCount();
	}

}
