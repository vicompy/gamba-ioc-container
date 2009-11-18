package org.gro.sm4j.sequences;

public interface ISequence {
	Object getNext(Object... args) throws Exception;

	void reset();

	boolean checkIfFinished();
}
