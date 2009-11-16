package org.gamba.mocks.fluent;

public final class Mask {

	private final boolean[] mask;

	private Mask(final boolean[] mask) {
		super();
		this.mask = mask.clone();
	}

	public static Mask mask(final boolean... booleanMask) {
		return new Mask(booleanMask);
	}

	public boolean[] getMask() {
		return mask.clone();
	}

}
