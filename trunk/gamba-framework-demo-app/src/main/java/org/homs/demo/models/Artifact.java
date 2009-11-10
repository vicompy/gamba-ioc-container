package org.homs.demo.models;

public class Artifact {

	private int id;
	private String groupId;
	private String artifactId;
	private String version;
	private String jarName;
	private String url;
	private int dept;

	public int getDept() {
		return dept;
	}

	public void setDept(final int dept) {
		this.dept = dept;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
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
