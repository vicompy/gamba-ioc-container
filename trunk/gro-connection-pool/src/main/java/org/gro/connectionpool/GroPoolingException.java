package org.gro.connectionpool;

public class GroPoolingException extends RuntimeException {

	private static final long serialVersionUID = 7976904176151850115L;

	public GroPoolingException() {
		super();
	}

	public GroPoolingException(final String msg) {
		super(msg);
	}

	public GroPoolingException(final Throwable exc) {
		super(exc);
	}

	public GroPoolingException(final String msg, final Throwable exc) {
		super(msg, exc);
	}

}
