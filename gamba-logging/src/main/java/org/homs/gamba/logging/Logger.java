package org.homs.gamba.logging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.homs.gamba.logging.interfaces.IConfigLoader;
import org.homs.gamba.logging.interfaces.ILogHandler;

class Logger extends LoggerLevelConstants {

	// ===============================================================
	//
	// CONFIG
	//
	// ===============================================================

	protected final List<ILogHandler> handlerList;
	protected final boolean disabled;
	protected final int logLevel;
	protected final boolean showDate;
	protected final SimpleDateFormat dateFormat;

	// dd MMMMM yyyy
	// yyyyMMdd
	// dd.MM.yy
	// MM/dd/yy
	// yyyy.MM.dd G 'at' hh:mm:ss z
	// EEE, MMM d, ''yy
	// h:mm a
	// H:mm:ss:SSS
	// K:mm a,z
	// yyyy.MMMMM.dd GGG hh:mm aaa

	// ===============================================================
	//
	// SINGLETON
	//
	// ===============================================================

	private Logger() {
		final IConfigLoader cl = new ConfigLoader();

		if (cl.isConfigFileNotFound()) {
			sendMessage(WARNING, Logger.class, "gamba-logging config file not found. default config is applied.");
		}

		disabled = cl.disableLogging();
		logLevel = cl.getLogLevel();
		showDate = cl.enableDateTime();
		dateFormat = new SimpleDateFormat(cl.getDateTimeFormat(), Locale.getDefault());
		handlerList = cl.getHandlerList();
	}

	private static class SingletonHolder {
		private static final Logger INSTANCE = new Logger();
	}

	public static Logger getLogger() {
		return SingletonHolder.INSTANCE;
	}

	protected String getTimeStamp() {
		return dateFormat.format(Calendar.getInstance().getTime());
	}

	public void sendMessage(final int level, final Class<?> targetClass, final String msg) {
		if (!disabled && level <= logLevel) {

			final StringBuffer rendMsg = new StringBuffer();
			rendMsg.append(levelTags[level]);
			rendMsg.append(targetClass.getSimpleName());
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

}
