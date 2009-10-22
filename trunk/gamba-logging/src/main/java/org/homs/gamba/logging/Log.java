package org.homs.gamba.logging;


public class Log {

	protected final String messageLabel;
	protected static final Logger logger = Logger.getLogger();

	public Log() {
		messageLabel = "";
	}

	public Log(final String label) {
		messageLabel = label;
	}

	public Log(final Class<?> targetClass) {
		messageLabel = targetClass.getSimpleName();
	}

	public void fatal(final String msg) {
		logger.fatal(messageLabel, msg);
	}

	public void error(final String msg) {
		logger.error(messageLabel, msg);
	}

	public void warning(final String msg) {
		logger.warning(messageLabel, msg);
	}

	public void info(final String msg) {
		logger.info(messageLabel, msg);
	}

	public void debug(final String msg) {
		logger.debug(messageLabel, msg);
	}
	// TODO falta per loggejar excepcions amb l'stacktrace

}
