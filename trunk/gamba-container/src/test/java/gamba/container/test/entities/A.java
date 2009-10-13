package gamba.container.test.entities;

public class A {
	private B b;

	public A() {
	}

	public A(final B b) {
		super();
		this.b = b;
	}

	public B getB() {
		return b;
	}

	public void setB(final B b) {
		this.b = b;
	}

	@Override
	public String toString() {
		return this.b.getMsg();
	}

}
