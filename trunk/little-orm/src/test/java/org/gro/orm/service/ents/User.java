package org.gro.orm.service.ents;

import java.util.Arrays;

public class User {

	private Integer idUser;
	private String name;
	private String pass;
	private Url[] urls;

	public User() {

	}

	public User(final Integer idUser, final String name, final String pass) {
		super();
		this.idUser = idUser;
		this.name = name;
		this.pass = pass;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(final Integer idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(final String pass) {
		this.pass = pass;
	}

	public Url[] getUrls() {
		return urls;
	}

	public void setUrls(final Url[] urls) {
		this.urls = urls;
	}

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", name=" + name + ", pass=" + pass + ", urls="
				+ Arrays.toString(urls) + "]";
	}

}
