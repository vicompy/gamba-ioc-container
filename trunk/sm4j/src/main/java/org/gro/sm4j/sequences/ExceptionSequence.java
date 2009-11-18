package org.gro.sm4j.sequences;

public class ExceptionSequence extends ObjectSequence {

	public ExceptionSequence(final Object... throwables) {
		super(throwables);
	}

	@Override
	public Object getNext(final Object... args) throws Exception {
		throw (Exception) super.getNext(args);
	}

}
