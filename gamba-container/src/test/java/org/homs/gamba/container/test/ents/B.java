package org.homs.gamba.container.test.ents;

public class B {

	private IA a;
	private int persons;

	public B() {

	}

	public B(final IA a, final int persons) {
		super();
		this.a = a;
		this.persons = persons;
	}

	public B(final IA a) {
		super();
		this.a = a;
	}

	public IA getA() {
		return a;
	}

	public void setA(final IA a) {
		this.a = a;
	}

	public int getPersons() {
		return persons;
	}

	public void setPersons(final int persons) {
		this.persons = persons;
	}
}
