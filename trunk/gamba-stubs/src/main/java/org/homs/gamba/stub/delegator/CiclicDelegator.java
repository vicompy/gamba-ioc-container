package org.homs.gamba.stub.delegator;

import org.homs.gamba.stub.IDelegator;

public abstract class CiclicDelegator implements IDelegator {

	private int index = 0;
	private Object[] sequence = null;

	public CiclicDelegator() {
		sequence = getSequence();
	}

	protected abstract Object[] getSequence();

	public Object delegates(final Object... os) {
		if (index >= sequence.length) {
			index = 0;
		}
		return sequence[index++];
	}

}
