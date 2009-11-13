package org.homs.gamba.stub.bsyntax;

public class Mask {

	private final boolean[] mask;

	private Mask(final boolean[] mask) {
		super();
		this.mask = mask;
	}

	public static Mask mask(final boolean... is) {
		return new Mask(is);
	}

	public boolean[] getMask() {
		return mask;
	}

}
