package org.gro.orm.dao;

import java.util.Arrays;

public class Dog {

	private int idDog;
	private String name;
	private Integer age;
	private Color[] colors;

	public Dog() {

	}

	public Dog(final int idDog, final String name, final Integer age, final Color[] colors) {
		super();
		this.idDog = idDog;
		this.name = name;
		this.age = age;
		this.colors = colors;
	}

	public int getIdDog() {
		return idDog;
	}

	public void setIdDog(final int idDog) {
		this.idDog = idDog;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(final Integer age) {
		this.age = age;
	}

	public Color[] getColors() {
		return colors;
	}

	public void setColors(final Color[] colors) {
		this.colors = colors;
	}

	public String toString2() {
		return "Dog [age=" + age + ", colors=" + Arrays.toString(colors) + ", idDog=" + idDog + ", name="
				+ name + "]";
	}

	@Override
	public String toString() {
		return "Dog [age=" + age + ", colors=" + colors + ", idDog=" + idDog + ", name=" + name + "]";
	}


}
