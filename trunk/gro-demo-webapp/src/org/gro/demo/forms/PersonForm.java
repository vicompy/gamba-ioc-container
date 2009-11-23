package org.gro.demo.forms;

public class PersonForm {

	private Long id;
	private String name;
	private int age;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setAge(final int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return name + "-" + age;
	}

}
