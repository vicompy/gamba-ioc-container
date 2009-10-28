package org.homs.gamba.stub.delegator;

public class CyclicSequence implements IDelegator {

	private int index = 0;
	private final Object[] sequence;

	public CyclicSequence(final Object... sequence) {
		this.sequence = sequence;
	}

	public Object delegates(final Object... args) {
		if (index >= sequence.length) {
			index = 0;
		}
		return sequence[index++];
	}

}
