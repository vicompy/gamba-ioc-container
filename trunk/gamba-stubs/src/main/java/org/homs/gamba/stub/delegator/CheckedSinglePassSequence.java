package org.homs.gamba.stub.delegator;

import org.homs.gamba.stub.exception.GambaStubsException;

public class CheckedSinglePassSequence extends AbstractDelegator {

	public CheckedSinglePassSequence(final Object... sequence) {
		super(sequence);
	}

	public Object delegates(final Object... args) {
		if (index >= sequence.length) {
//			index--;
			throw new GambaStubsException("OnePassSequence: end of return list reached.");
		}
		return sequence[index++];
	}

}