package org.gamba.mocks.sequences;

import org.gamba.mocks.exception.GambaMockException;

public class ExceptionSequence extends AbstractSequence {

	public ExceptionSequence(final Object...throwables) {
		super(throwables);
	}

	public Object getNext(final Object... args) throws Throwable {
		if (index >= sequence.length) {
			throw new GambaMockException("unsatisfied expectation: end of return list reached; " + this.toString());
		}
		throw (Throwable) super.sequence[index++];
	}

}
