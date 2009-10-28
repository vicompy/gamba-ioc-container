package org.homs.gamba.stub.delegator;

import org.homs.gamba.stub.IDelegator;

public class OnePassSequence implements IDelegator {

	private int index = 0;
	private Object[] sequence = null;

	public OnePassSequence(final Object... sequence) {
		this.sequence = sequence;
	}

	public Object delegates(final Object... os) {
		if (index >= sequence.length) {
			index--;
		}
		return sequence[index++];
	}

}