package org.homs.demo.models;

public class Artifact {

	private String groupId;
	private String artifactId;
	private String version;
	private String jarName;
	private String url;

	public Artifact() {

	}

	public Artifact(final String groupId, final String artifactId, final String version, final String jarName, final String url) {
		super();
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
		this.jarName = jarName;
		this.url = url;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(final String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(final String artifactId) {
		this.artifactId = artifactId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(final String version) {
		this.version = version;
	}

	public String getJarName() {
		return jarName;
	}

	public void setJarName(final String jarName) {
		this.jarName = jarName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

}
