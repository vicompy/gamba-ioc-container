package org.lechuga.mvc.validation;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 7976904176151850115L;

//	public BindingException() {
//		super();
//	}

	public ValidationException(final String msg) {
		super(msg);
	}

//	public BindingException(final Throwable exc) {
//		super(exc);
//	}

	public ValidationException(final String msg, final Throwable exc) {
		super(msg, exc);
	}

}
