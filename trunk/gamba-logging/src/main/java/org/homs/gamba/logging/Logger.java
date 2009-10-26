package org.homs.gamba.logging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.homs.gamba.logging.interfaces.IConfigLoader;
import org.homs.gamba.logging.interfaces.ILogHandler;

class Logger implements ILogger {

	protected static final String[] LEVEL_TAGS = new String[] { "[FATAL] ", "[ERROR] ", "[WARN]  ", "[INFO]  ",
			"[DEBUG] " };

	private final static String DEFAULT_CONFIG_FILENAME = "logging-config.properties";

	protected List<ILogHandler> handlerList;
	protected boolean disabled;
	protected int logLevel;
	protected boolean showDate;
	protected SimpleDateFormat dateFormat;

	protected Logger() {
		this(new ConfigLoader(DEFAULT_CONFIG_FILENAME));
	}

	// TODO porta d'enllaç de testing
	protected Logger(final IConfigLoader customConfigLoader) {
		final IConfigLoader cl = customConfigLoader;

		disabled = cl.disableLogging();
		logLevel = cl.getLogLevel();
		showDate = cl.enableDateTime();
		dateFormat = new SimpleDateFormat(cl.getDateTimeFormat(), Locale.getDefault());
		handlerList = cl.getHandlerList();

		if (cl.isConfigFileNotFound()) {
			sendMessage(WARNING, this.getClass().getSimpleName(),
					"gamba-logging config file not found. default config is applied.");
		}
	}

	private static class SingletonHolder {
		private static final ILogger INSTANCE = new Logger();
	}

	public static ILogger getLogger() {
		return SingletonHolder.INSTANCE;
	}

	protected final String getTimeStamp() {
		return dateFormat.format(Calendar.getInstance().getTime());
	}

	/**
	 * @see org.homs.gamba.logging.ILogger#sendMessage(int, java.lang.String,
	 *      java.lang.String)
	 */
	public final void sendMessage(final int level, final String label, final String msg) {
		if (!disabled && level <= logLevel) {

			final StringBuffer rendMsg = new StringBuffer();
			rendMsg.append(LEVEL_TAGS[level]);
			rendMsg.append(label);
			rendMsg.append(": ");
			if (showDate) {
				rendMsg.append(getTimeStamp());
				rendMsg.append(' ');
			}
			rendMsg.append(msg);

			for (final ILogHandler h : handlerList) {
				h.sendMessage(rendMsg.toString());
			}
		}
	}

	// TODO nou, testar
	/**
	 * @see org.homs.gamba.logging.ILogger#sendMessage(int, java.lang.String,
	 *      java.lang.Object)
	 */
	public final void sendMessage(final int level, final String label, final Object... msgs) {
		if (!disabled && level <= logLevel) {
			final StringBuffer strb = new StringBuffer();
			for (final Object o : msgs) {
				strb.append(o.toString());
			}
			sendMessage(level, label, strb.toString());
		}
	}

	/**
	 * @see org.homs.gamba.logging.ILogger#getFirstMatchingHandler(java.lang.Class)
	 */
	public ILogHandler getFirstMatchingHandler(final Class<? extends ILogHandler> handlerClass) {
		for (final ILogHandler h : handlerList) {
			if (h.getClass().equals(handlerClass)) {
				return h;
			}
		}
		return null;
	}

}
