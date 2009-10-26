package org.homs.gamba.logging;

import org.homs.gamba.logging.interfaces.ILog;

public class Log implements ILog {

	protected final String messageLabel;
	protected static final ILogger LOGGER = Logger.getLogger();

	public Log(final String label) {
		messageLabel = label;
	}

	public Log() {
		this("");
	}

	public Log(final Class<?> targetClass) {
		this(targetClass.getSimpleName());
	}

	/**
	 * @see org.homs.gamba.logging.interfaces.ILog#fatal(java.lang.String)
	 */
	public void fatal(final String msg) {
		LOGGER.sendMessage(ILogger.FATAL, messageLabel, msg);
	}

	/**
	 * @see org.homs.gamba.logging.interfaces.ILog#error(java.lang.String)
	 */
	public void error(final String msg) {
		LOGGER.sendMessage(ILogger.ERROR, messageLabel, msg);
	}

	/**
	 * @see org.homs.gamba.logging.interfaces.ILog#warning(java.lang.String)
	 */
	public void warning(final String msg) {
		LOGGER.sendMessage(ILogger.WARNING, messageLabel, msg);
	}

	/**
	 * @see org.homs.gamba.logging.interfaces.ILog#info(java.lang.String)
	 */
	public void info(final String msg) {
		LOGGER.sendMessage(ILogger.INFO, messageLabel, msg);
	}

	/**
	 * @see org.homs.gamba.logging.interfaces.ILog#debug(java.lang.String)
	 */
	public void debug(final String msg) {
		LOGGER.sendMessage(ILogger.DEBUG, messageLabel, msg);
	}

	/**
	 * @see org.homs.gamba.logging.interfaces.ILog#fatal(java.lang.Exception)
	 */
	public void fatal(final Exception e) {
		renderExceptionMessage(ILogger.FATAL, e);
	}

	/**
	 * @see org.homs.gamba.logging.interfaces.ILog#error(java.lang.Exception)
	 */
	public void error(final Exception e) {
		renderExceptionMessage(ILogger.ERROR, e);
	}

	/**
	 * @see org.homs.gamba.logging.interfaces.ILog#warning(java.lang.Exception)
	 */
	public void warning(final Exception e) {
		renderExceptionMessage(ILogger.WARNING, e);
	}

	/**
	 * @see org.homs.gamba.logging.interfaces.ILog#info(java.lang.Exception)
	 */
	public void info(final Exception e) {
		renderExceptionMessage(ILogger.INFO, e);
	}

	/**
	 * @see org.homs.gamba.logging.interfaces.ILog#debug(java.lang.Exception)
	 */
	public void debug(final Exception e) {
		renderExceptionMessage(ILogger.DEBUG, e);
	}

	private void renderExceptionMessage(final int level, final Exception e) {
		final StringBuffer strb = new StringBuffer();
		strb.append(e.toString());
		for (final StackTraceElement ste : e.getStackTrace()) {
			strb.append(ste.toString());
			strb.append('\n');
		}
		for (final String msg : strb.toString().split("\n")) {
			LOGGER.sendMessage(level, messageLabel, msg);
		}
	}


	// TODO nou, preveu el renderitzat innecessari de missatges,
	// quan el logger està desactivat!!
	// TODO testar
	public void fatal(final Object... os) {
		LOGGER.sendMessage(ILogger.FATAL, messageLabel, os);
	}
	public void error(final Object... os) {
		LOGGER.sendMessage(ILogger.ERROR, messageLabel, os);
	}
	public void warning(final Object... os) {
		LOGGER.sendMessage(ILogger.WARNING, messageLabel, os);
	}
	public void info(final Object... os) {
		LOGGER.sendMessage(ILogger.INFO, messageLabel, os);
	}
	public void debug(final Object... os) {
		LOGGER.sendMessage(ILogger.DEBUG, messageLabel, os);
	}


}
