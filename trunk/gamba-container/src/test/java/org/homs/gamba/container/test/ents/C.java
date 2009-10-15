package org.homs.gamba.container.test.ents;

public class C {

	private IA a;
	private B b;
	private String name;
	private Integer age;
	private int persons;

	public C(final IA a, final B b, final String name, final Integer age, final int persons) {
		super();
		this.a = a;
		this.b = b;
		this.name = name;
		this.age = age;
	}

	public IA getA() {
		return a;
	}

	public void setA(final IA a) {
		this.a = a;
	}

	public B getB() {
		return b;
	}

	public void setB(final B b) {
		this.b = b;
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

	public int getPersons() {
		return persons;
	}

	public void setPersons(final int persons) {
		this.persons = persons;
	}

}
