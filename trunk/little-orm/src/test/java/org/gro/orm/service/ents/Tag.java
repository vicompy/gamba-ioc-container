package org.gro.orm.service.ents;

import java.util.Arrays;

public class Tag {

	private Integer idTag;
	private String name;
	private Url[] urls;

	public Tag() {
	}

	public Tag(final Integer idTag, final String name) {
		super();
		this.idTag = idTag;
		this.name = name;
	}

	public Integer getIdTag() {
		return idTag;
	}

	public void setIdTag(final Integer idTag) {
		this.idTag = idTag;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Url[] getUrls() {
		return urls;
	}

	public void setUrls(final Url[] urls) {
		this.urls = urls;
	}

	@Override
	public String toString() {
		return "Tag [idTag=" + idTag + ", name=" + name + ", urls=" + Arrays.toString(urls) + "]";
	}

}
