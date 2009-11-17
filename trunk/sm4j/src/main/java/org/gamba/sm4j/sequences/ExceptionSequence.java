package org.gamba.sm4j.sequences;

import org.gamba.sm4j.exception.GambaMockingException;

public class ExceptionSequence extends AbstractSequence {

	public ExceptionSequence(final Object... throwables) {
		super(throwables);
	}

	public Object getNext(final Object... args) throws Throwable {
		if (index >= sequence.length) {
			throw new GambaMockingException("unsatisfied expectation: end of return list reached; "
					+ this.toString());
		}
		throw (Throwable) super.sequence[index++];
	}

}
