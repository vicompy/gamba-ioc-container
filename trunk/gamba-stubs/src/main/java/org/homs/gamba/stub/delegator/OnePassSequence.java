package org.homs.gamba.stub.delegator;

public class OnePassSequence extends AbstractDelegator {

	public OnePassSequence(final Object... sequence) {
		super(sequence);
	}

	public Object delegates(final Object... args) {
		if (index >= sequence.length) {
			index--;
		}
		return sequence[index++];
	}

}