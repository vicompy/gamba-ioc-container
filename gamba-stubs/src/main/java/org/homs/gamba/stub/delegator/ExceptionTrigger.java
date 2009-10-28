package org.homs.gamba.stub.delegator;

public class ExceptionTrigger implements IDelegator {

	private final Throwable exception;

	public ExceptionTrigger(final Throwable exception) {
		this.exception = exception;
	}

	public Object delegates(final Object... args) throws Throwable {
		throw exception;
	}

}
