package org.gro.orm;

public class Cat {

	private Integer id;
	private String name;
	private Integer age;

	public Cat() {
	}

	public Cat(final Integer id, final String name, final Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Dog [age=" + age + ", id=" + id + ", name=" + name + "]";
	}

}