package org.gamba.mocks.sequences;

public interface ISequence {
	Object getNext(Object... args) throws Throwable;

	void reset();

	boolean checkIfFinished();
}
