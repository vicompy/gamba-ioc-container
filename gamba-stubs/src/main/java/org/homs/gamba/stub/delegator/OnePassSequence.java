package org.homs.gamba.stub.delegator;

public class OnePassSequence implements IDelegator {

	private int index = 0;
	private final Object[] sequence;

	public OnePassSequence(final Object... sequence) {
		this.sequence = sequence;
	}

	public Object delegates(final Object... args) {
		if (index >= sequence.length) {
			index--;
		}
		return sequence[index++];
	}

}