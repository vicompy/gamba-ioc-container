package org.homs.gamba.stub.delegator;

public class PingPongSequence implements IDelegator {

	private int dir = 1;
	private int index = 0;
	private final Object[] sequence;

	public PingPongSequence(final Object... sequence) {
		this.sequence = sequence;
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
