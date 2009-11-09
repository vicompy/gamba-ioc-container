package org.homs.gamba.binding;

public class BindingException extends RuntimeException {

	private static final long serialVersionUID = 7976904176151850115L;

	public BindingException() {
		super();
	}

	public BindingException(final String msg) {
		super(msg);
	}

	public BindingException(final String msg, final Throwable exc) {
		super(msg, exc);
	}

}
