package org.homs.gamba.stub.bsyntax;

public class ForAny {

	private final int aniedArgNumber;

	private ForAny(final int aniedArgNumber) {
		super();
		this.aniedArgNumber = aniedArgNumber;
	}

	public static ForAny forAny(final int aniedArgNumber) {
		return new ForAny(aniedArgNumber);
	}

	public int getAniedArgNumber() {
		return aniedArgNumber;
	}

}
