package org.homs.gamba.stub.delegator;

public class UncheckedSinglePassSequence extends AbstractDelegator {

	public UncheckedSinglePassSequence(final Object... sequence) {
		super(sequence);
	}

	public Object delegates(final Object... args) {
		if (index >= sequence.length) {
			index--;
		}
		return sequence[index++];
	}

}