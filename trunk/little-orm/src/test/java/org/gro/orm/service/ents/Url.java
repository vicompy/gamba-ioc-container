package org.gro.orm.service.ents;

import java.util.Arrays;

public class Url {

	private Integer idUrl;
	private String url;
	private Tag[] tags;
	private User[] users;

	public Url() {

	}

	public Url(final Integer idUrl, final String url, final Tag[] tags) {
		super();
		this.idUrl = idUrl;
		this.url = url;
		this.tags = tags;
	}

	public Integer getIdUrl() {
		return idUrl;
	}

	public void setIdUrl(final Integer idUrl) {
		this.idUrl = idUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public Tag[] getTags() {
		return tags;
	}

	public void setTags(final Tag[] tags) {
		this.tags = tags;
	}

	public User[] getUsers() {
		return users;
	}

	public void setUsers(final User[] users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Url [idUrl=" + idUrl + ", tags=" + Arrays.toString(tags) + ", url=" + url + ", users="
				+ Arrays.toString(users) + "]";
	}

}
