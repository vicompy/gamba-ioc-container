package org.homs.gamba.stub.delegator;

import org.homs.gamba.stub.IDelegator;

public abstract class PingPongDelegator implements IDelegator {

	private int dir = 1;
	private int index = 0;
	private Object[] sequence = null;

	public PingPongDelegator() {
		sequence = getSequence();
	}

	protected abstract Object[] getSequence();

	public Object delegates(final Object... os) {
		final Object r = sequence[index];
		index += dir;
		if (index <= 0 || index >= sequence.length - 1) {
			dir = -dir;
		}
		return r;
	}

}
