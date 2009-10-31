package org.homs.gamba.stub.delegator;

public class ExceptionTrigger extends Delegator {

	public ExceptionTrigger(final Throwable exception) {
		super(exception);
	}

	public Object delegates(final Object... args) throws Throwable {
		throw (Throwable) super.sequence[0];
	}

}
