package org.gamba.sm4j.sequences;

import org.gamba.sm4j.exception.GambaMockingException;

public class ObjectSequence extends AbstractSequence {

	public ObjectSequence(final Object... sequence) {
		super(sequence);
	}

	public Object getNext(final Object... args) {
		if (index >= sequence.length) {
			throw new GambaMockingException("unsatisfied expectation: end of return list reached; "
					+ this.toString());
		}
		return sequence[index++];
	}

}