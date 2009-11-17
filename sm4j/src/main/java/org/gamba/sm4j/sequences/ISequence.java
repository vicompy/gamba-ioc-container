package org.gamba.sm4j.sequences;

public interface ISequence {
	Object getNext(Object... args) throws Throwable;

	void reset();

	boolean checkIfFinished();
}
