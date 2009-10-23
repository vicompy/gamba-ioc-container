package org.homs.gamba.logging;

public class Log {

	protected final String messageLabel;
	protected static final Logger logger = Logger.getLogger();

	public Log(final String label) {
		messageLabel = label;
	}

	public Log() {
		this("");
	}

	public Log(final Class<?> targetClass) {
		this(targetClass.getSimpleName());
	}

	public void fatal(final String msg) {
		logger.sendMessage(Logger.FATAL, messageLabel, msg);
	}

	public void error(final String msg) {
		logger.sendMessage(Logger.ERROR, messageLabel, msg);
	}

	public void warning(final String msg) {
		logger.sendMessage(Logger.WARNING, messageLabel, msg);
	}

	public void info(final String msg) {
		logger.sendMessage(Logger.INFO, messageLabel, msg);
	}

	public void debug(final String msg) {
		logger.sendMessage(Logger.DEBUG, messageLabel, msg);
	}

	public void fatal(final Exception e) {
		renderExceptionMessage(Logger.FATAL, e);
	}

	public void error(final Exception e) {
		renderExceptionMessage(Logger.ERROR, e);
	}

	 public void warning(final Exception e) {
		renderExceptionMessage(Logger.WARNING, e);
	 }

	 public void info(final Exception e) {
		renderExceptionMessage(Logger.INFO, e);
	 }

	 public void debug(final Exception e) {
		renderExceptionMessage(Logger.DEBUG, e);
	 }

	private void renderExceptionMessage(final int level, final Exception e) {
		final StringBuffer strb = new StringBuffer();
		strb.append(e.toString());
		for (final StackTraceElement ste : e.getStackTrace()) {
			strb.append(ste.toString());
			strb.append('\n');
		}
		for (final String msg : strb.toString().split("\n")) {
			logger.sendMessage(level, messageLabel, msg);
		}
	}
}
