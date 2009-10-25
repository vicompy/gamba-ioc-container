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

	protected static final String[] LEVEL_TAGS = new String[] {
		"[FATAL] ", "[ERROR] ", "[WARN]  ", "[INFO]  ", "[DEBUG] "
	};

	private static String defaultConfigFileName = "logging-config.properties";

	protected List<ILogHandler> handlerList;
	protected boolean disabled;
	protected int logLevel;
	protected boolean showDate;
	protected SimpleDateFormat dateFormat;

	protected Logger() {
		final IConfigLoader cl = new ConfigLoader(defaultConfigFileName);

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

	public ILogHandler getFirstMatchingHandler(final Class<? extends ILogHandler> handlerClass) {
		for (final ILogHandler h : handlerList) {
			if (h.getClass().equals(handlerClass)) {
				return h;
			}
		}
		return null;
	}

	public static void setDefaultConfigFileName(final String defaultConfigFileName) {
		Logger.defaultConfigFileName = defaultConfigFileName;
	}

}
