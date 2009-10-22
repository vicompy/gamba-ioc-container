package org.homs.gamba.logging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.homs.gamba.logging.interfaces.IConfigLoader;
import org.homs.gamba.logging.interfaces.ILogHandler;

class Logger {

	public static final int FATAL = 0;
	public static final int ERROR = 1;
	public static final int WARNING = 2;
	public static final int INFO = 3;
	public static final int DEBUG = 4;

	protected static final String[] levelTags = new String[] {
		"[FATAL] ", "[ERROR] ", "[WARN]  ", "[INFO]  ", "[DEBUG] "
	};

	public static String DEFAULT_CONFIG_FILE = "logging-config.properties";

	protected List<ILogHandler> handlerList;
	protected boolean disabled;
	protected int logLevel;
	protected boolean showDate;
	protected SimpleDateFormat dateFormat;

	protected Logger() {
		final IConfigLoader cl = new ConfigLoader(DEFAULT_CONFIG_FILE);

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
		private static final Logger INSTANCE = new Logger();
	}

	public static Logger getLogger() {
		return SingletonHolder.INSTANCE;
	}

	protected final String getTimeStamp() {
		return dateFormat.format(Calendar.getInstance().getTime());
	}

	private final void sendMessage(final int level, final String label, final String msg) {
		if (!disabled && level <= logLevel) {

			final StringBuffer rendMsg = new StringBuffer();
			rendMsg.append(levelTags[level]);
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

	public void fatal(final String msgLabel, final String msg) {
		sendMessage(FATAL, msgLabel, msg);
	}

	public void error(final String msgLabel, final String msg) {
		sendMessage(ERROR, msgLabel, msg);
	}

	public void warning(final String msgLabel, final String msg) {
		sendMessage(WARNING, msgLabel, msg);
	}

	public void info(final String msgLabel, final String msg) {
		sendMessage(INFO, msgLabel, msg);
	}

	public void debug(final String msgLabel, final String msg) {
		sendMessage(DEBUG, msgLabel, msg);
	}

	public ILogHandler getFirstMatchingHandler(final Class<? extends ILogHandler> handlerClass) {
		for (final ILogHandler h : handlerList) {
			if (h.getClass().equals(handlerClass)) {
				return h;
			}
		}
		return null;
	}

}
