package org.homs.gamba.logging;

public class Log {

	private final Class<?> targetClass;
	private final Logger logger;

	public Log(final Class<?> targetClass) {
		super();
		this.targetClass = targetClass;
		this.logger = Logger.getLogger();
	}

	public void fatal(final String msg) {
		logger.sendMessage(Logger.FATAL, targetClass, msg);
	}

	public void error(final String msg) {
		logger.sendMessage(Logger.ERROR, targetClass, msg);
	}

	public void warning(final String msg) {
		logger.sendMessage(Logger.WARNING, targetClass, msg);
	}

	public void info(final String msg) {
		logger.sendMessage(Logger.INFO, targetClass, msg);
	}

	public void debug(final String msg) {
		logger.sendMessage(Logger.DEBUG, targetClass, msg);
	}
}