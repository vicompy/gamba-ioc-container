package org.homs.gamba.stub.delegator;

public class CyclicSequence extends Delegator {

	public CyclicSequence(final Object... sequence) {
		super(sequence);
	}

	public Object delegates(final Object... args) {
		if (index >= sequence.length) {
			index = 0;
		}
		return sequence[index++];
	}

}
