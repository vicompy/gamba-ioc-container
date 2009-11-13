package org.homs.gamba.stub.delegator;

public class SinglePassSequence extends AbstractDelegator {

	public SinglePassSequence(final Object... sequence) {
		super(sequence);
	}

	public Object delegates(final Object... args) {
		if (index >= sequence.length) {
			index--;
		}
		return sequence[index++];
	}

}