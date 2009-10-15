package org.homs.gamba.container.test.ents;

public class A implements IA {

	private String name;
	private Integer age;

	public A() {

	}

	public A(final String name, final Integer age) {
		super();
		this.name = name;
		this.age = age;
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

}
