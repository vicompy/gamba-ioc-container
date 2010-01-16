package org.gro.orm.service.ents;

import java.util.Date;

public class UserUrl {

	private Integer idUserUrl;
	private Date registered;
	private String title;
	private String description;

	public UserUrl() {

	}

	public Integer getIdUserUrl() {
		return idUserUrl;
	}

	public void setIdUserUrl(final Integer idUserUrl) {
		this.idUserUrl = idUserUrl;
	}

	public Date getRegistered() {
		return registered;
	}

	public void setRegistered(final Date registered) {
		this.registered = registered;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "UserUrl [description=" + description + ", idUserUrl=" + idUserUrl + ", registered="
				+ registered + ", title=" + title + "]";
	}

}
