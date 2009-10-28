package org.homs.gamba.stub.delegator;

import org.homs.gamba.stub.IDelegator;

public class CiclicSequence implements IDelegator {

	private int index = 0;
	private Object[] sequence = null;

	public CiclicSequence(final Object... sequence) {
		this.sequence = sequence;
	}

	public Object delegates(final Object... args) {
		if (index >= sequence.length) {
			index = 0;
		}
		return sequence[index++];
	}

}
