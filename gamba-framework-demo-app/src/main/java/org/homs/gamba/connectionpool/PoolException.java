package org.homs.gamba.connectionpool;

public class PoolException extends RuntimeException {

	private static final long serialVersionUID = 7976904176151850115L;

	public PoolException() {
		super();
	}

	public PoolException(final String msg) {
		super(msg);
	}

	public PoolException(final Throwable exc) {
		super(exc);
	}

	public PoolException(final String msg, final Throwable exc) {
		super(msg, exc);
	}

}
