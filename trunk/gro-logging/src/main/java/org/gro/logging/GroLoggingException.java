package org.gro.logging;

@SuppressWarnings("serial") // TODO
public class GroLoggingException extends RuntimeException {

	public GroLoggingException(final String missatge) {
		super(missatge);
	}

	public GroLoggingException(final String missatge, final Throwable excepcio) {
		super(missatge, excepcio);
	}

}
