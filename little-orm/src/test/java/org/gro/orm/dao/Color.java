package org.gro.orm.dao;

public class Color {

	private int idColor;
	private String name;

	public Color() {
	}

	public Color(final int idColor, final String name) {
		super();
		this.idColor = idColor;
		this.name = name;
	}

	public int getIdColor() {
		return idColor;
	}

	public void setIdColor(final int idColor) {
		this.idColor = idColor;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Color [idColor=" + idColor + ", name=" + name + "]";
	}

}
