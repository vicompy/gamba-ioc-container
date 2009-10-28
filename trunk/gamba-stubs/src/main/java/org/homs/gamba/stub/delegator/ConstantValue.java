package org.homs.gamba.stub.delegator;

public class ConstantValue implements IDelegator {

	private final Object sequence;

	public ConstantValue(final Object theUniqueValue) {
		this.sequence = theUniqueValue;
	}

	public Object delegates(final Object... args) {
		return sequence;
	}

}
