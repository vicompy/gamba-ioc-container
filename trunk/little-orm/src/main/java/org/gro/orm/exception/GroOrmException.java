package org.gro.orm.exception;

public class GroOrmException extends RuntimeException {

	private static final long serialVersionUID = 351194564362727677L;

	public GroOrmException() {
		super();
	}

	public GroOrmException(final String msg) {
		super(msg);
	}

	public GroOrmException(final Throwable exc) {
		super(exc);
	}

	public GroOrmException(final String msg, final Throwable exc) {
		super(msg, exc);
	}

}
