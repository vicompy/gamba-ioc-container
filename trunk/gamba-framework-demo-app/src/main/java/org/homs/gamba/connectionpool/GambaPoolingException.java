package org.homs.gamba.connectionpool;

public class GambaPoolingException extends RuntimeException {

	private static final long serialVersionUID = 7976904176151850115L;

	public GambaPoolingException() {
		super();
	}

	public GambaPoolingException(final String msg) {
		super(msg);
	}

	public GambaPoolingException(final Throwable exc) {
		super(exc);
	}

	public GambaPoolingException(final String msg, final Throwable exc) {
		super(msg, exc);
	}

}
