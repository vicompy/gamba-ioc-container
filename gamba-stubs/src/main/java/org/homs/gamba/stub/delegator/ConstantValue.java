package org.homs.gamba.stub.delegator;

import org.homs.gamba.stub.IDelegator;

public class ConstantValue implements IDelegator {

	private final int index = 0;
	private Object sequence = null;

	public ConstantValue(final Object theUniqueValue) {
		this.sequence = theUniqueValue;
	}

	public Object delegates(final Object... os) {
		return sequence;
	}

}
