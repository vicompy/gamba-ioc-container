package org.gamba.mocks.sequences;

import org.gamba.mocks.exception.GambaMockException;

public class ObjectSequence extends AbstractSequence {

	public ObjectSequence(final Object... sequence) {
		super(sequence);
	}

	public Object getNext(final Object... args) {
		if (index >= sequence.length) {
			throw new GambaMockException("unsatisfied expectation: end of return list reached; " + this.toString());
		}
		return sequence[index++];
	}

}