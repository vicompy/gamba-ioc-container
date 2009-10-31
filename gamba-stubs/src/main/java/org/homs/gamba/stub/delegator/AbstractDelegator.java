package org.homs.gamba.stub.delegator;

public abstract class AbstractDelegator implements IDelegator {

	protected int index = 0;
	protected final Object[] sequence;

	public AbstractDelegator(final Object single) {
		this.sequence = new Object[] { single };
	}

	public AbstractDelegator(final Object... sequence) {
		this.sequence = sequence;
	}

//	public abstract Object delegates(final Object... args);

}
