package org.homs.demo.models.example;

public class Person {

	private Long id;
	private String name;
	private int age;

	public Person() {

	}

	public Person(final String name, final int age) {
		super();
		this.id = null;
		this.name = name;
		this.age = age;
	}

	public Person(final Long id, final String name, final int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setAge(final int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return id+"-"+name+"-"+age;
	}

}
