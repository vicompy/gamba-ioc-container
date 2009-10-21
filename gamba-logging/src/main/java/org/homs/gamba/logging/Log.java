package org.homs.gamba.logging;

import org.homs.gamba.logging.interfaces.IConfigLoader;



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

	public void resetup(final IConfigLoader cl) {
		logger.resetup(cl);
	}

}
