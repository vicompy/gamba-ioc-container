package org.gro.validation.exception;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -7785825329769320885L;

	public ValidationException(final String msg) {
		super(msg);
	}

	public ValidationException(final String msg, final Throwable exc) {
		super(msg, exc);
	}

}