package org.homs.gamba.stub.delegator;

public abstract class Delegator implements IDelegator {

	protected int index = 0;
	protected final Object[] sequence;

	public Delegator(final Object single) {
		this.sequence = new Object[] { single };
	}

	public Delegator(final Object... sequence) {
		this.sequence = sequence;
	}

//	public abstract Object delegates(final Object... args);

}
