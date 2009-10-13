package gamba.container.test.entities;

public class C {

	private A a;
	private B b;

	public C() {
	}

	public C(final A a, final B b) {
		super();
		this.a = a;
		this.b = b;
	}

	public A getA() {
		return a;
	}

	public B getB() {
		return b;
	}

	public void setA(final A a) {
		this.a = a;
	}

	public void setB(final B b) {
		this.b = b;
	}

}
