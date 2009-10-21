package org.homs.gamba.logging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.homs.gamba.logging.interfaces.IConfigLoader;
import org.homs.gamba.logging.interfaces.ILogHandler;

class Logger extends LoggerLevelConstants {

	protected final List<ILogHandler> handlerList;
	protected final boolean disabled;
	protected final int logLevel;
	protected final boolean showDate;
	protected final SimpleDateFormat dateFormat;

	private Logger() {
		final IConfigLoader cl = new ConfigLoader();

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

	protected String getTimeStamp() {
		return dateFormat.format(Calendar.getInstance().getTime());
	}

	public void sendMessage(final int level, final String label, final String msg) {
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

}
