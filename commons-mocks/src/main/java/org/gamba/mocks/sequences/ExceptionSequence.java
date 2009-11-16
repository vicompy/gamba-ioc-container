package org.gamba.mocks.sequences;

import org.gamba.mocks.exception.GambaMockException;

public class ExceptionSequence extends AbstractSequence {

	public ExceptionSequence(final Object...throwables) {
		super(throwables);
	}

	public Object getNext(final Object... args) throws Throwable {
		if (index >= sequence.length) {
			throw new GambaMockException("OnePassSequence: end of return list reached.");
		}
		throw (Throwable) super.sequence[index++];
	}

}
