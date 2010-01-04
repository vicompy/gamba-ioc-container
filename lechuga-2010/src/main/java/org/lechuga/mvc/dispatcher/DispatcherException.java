package org.lechuga.mvc.dispatcher;

public class DispatcherException extends RuntimeException {

	private static final long serialVersionUID = 7976904176151850115L;

//	public BindingException() {
//		super();
//	}

	public DispatcherException(final String msg) {
		super(msg);
	}

//	public BindingException(final Throwable exc) {
//		super(exc);
//	}

	public DispatcherException(final String msg, final Throwable exc) {
		super(msg, exc);
	}

}
