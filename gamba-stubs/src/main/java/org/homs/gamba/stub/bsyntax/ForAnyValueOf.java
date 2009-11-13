package org.homs.gamba.stub.bsyntax;

public class ForAnyValueOf {

	private final int aniedArgNumber;

	private ForAnyValueOf(final int aniedArgNumber) {
		super();
		this.aniedArgNumber = aniedArgNumber;
	}

	public static ForAnyValueOf argNum(final int aniedArgNumber) {
		return new ForAnyValueOf(aniedArgNumber);
	}

	public static ForAnyValueOf firstArg() {
		return new ForAnyValueOf(0);
	}

	public static ForAnyValueOf secondArg() {
		return new ForAnyValueOf(1);
	}

	public static ForAnyValueOf thirdArg() {
		return new ForAnyValueOf(2);
	}

	public static ForAnyValueOf fourthArg() {
		return new ForAnyValueOf(3);
	}

	public static ForAnyValueOf fifhtArg() {
		return new ForAnyValueOf(4);
	}

	public int getAniedArgNumber() {
		return aniedArgNumber;
	}

}
