package org.homs.gamba.stub.delegator;

public class PingPongSequence extends AbstractDelegator {

	private int dir = 1;

	public PingPongSequence(final Object... sequence) {
		super(sequence);
	}

	public Object delegates(final Object... args) {
		final Object returnValue = sequence[index];
		index += dir;
		if (index <= 0 || index >= sequence.length - 1) {
			dir = -dir;
		}
		return returnValue;
	}

}

