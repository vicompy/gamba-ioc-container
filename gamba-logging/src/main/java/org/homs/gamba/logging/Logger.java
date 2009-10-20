package org.homs.gamba.logging;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.homs.gamba.logging.handlers.ConsoleHandler;

class Logger {

	// ===============================================================
	//
	// CONFIG
	//
	// ===============================================================

	public static final int FATAL = 0;
	public static final int ERROR = 1;
	public static final int WARNING = 2;
	public static final int INFO = 3;
	public static final int DEBUG = 4;

	private static final String[] levelTags = new String[] { "[FATAL] ", "[ERROR] ", "[WARN]  ", "[INFO]  ", "[DEBUG] " };

	private final List<ILogHandler> handlerList;
	private final boolean enabled;
	private final int logLevel;
	private final boolean showDate;
	private final SimpleDateFormat dateFormat;

	// public Logger addHandler(final ILogHandler handler) {
	// handlerList.add(handler);
	// return this;
	// }
	//
	// public Logger setLevel(final int level) {
	// logLevel = level;
	// return this;
	// }
	//
	// public Logger setDisabled() {
	// enabled = false;
	// return this;
	// }
	//
	// public Logger disableDateShowing() {
	// showDate = false;
	// return this;
	// }
	//
	// public List<ILogHandler> getHandlerList() {
	// return handlerList;
	// }

	// System.out.println(DateUtils.now("dd MMMMM yyyy"));
	// System.out.println(DateUtils.now("yyyyMMdd"));
	// System.out.println(DateUtils.now("dd.MM.yy"));
	// System.out.println(DateUtils.now("MM/dd/yy"));
	// System.out.println(DateUtils.now("yyyy.MM.dd G 'at' hh:mm:ss z"));
	// System.out.println(DateUtils.now("EEE, MMM d, ''yy"));
	// System.out.println(DateUtils.now("h:mm a"));
	// System.out.println(DateUtils.now("H:mm:ss:SSS"));
	// System.out.println(DateUtils.now("K:mm a,z"));
	// System.out.println(DateUtils.now("yyyy.MMMMM.dd GGG hh:mm aaa"));

	// private Logger cleanConfig() {
	// enabled = true;
	// logLevel = 6;
	// showDate = true;
	// dateFormat = new SimpleDateFormat("H:mm:ss:SSS");
	// handlerList.clear();
	// handlerList.add(new ConsoleHandler());
	// return this;
	// }

	// ===============================================================
	//
	// SINGLETON
	//
	// ===============================================================

	private Logger() {
		// enabled = true;
		// // logLevel = 6;
		// showDate = true;
		// dateFormat = new SimpleDateFormat("H:mm:ss:SSS");
		// handlerList.clear();
		// handlerList.add(new ConsoleHandler());

		final IConfigLoader cl = new ConfigLoader();

		this.enabled = !cl.isDisabled(); // TODO negació tonta
		logLevel = cl.getLogLevel();
		showDate = cl.showTime();

		if (cl.timeFormat() != null) {
			dateFormat = new SimpleDateFormat(cl.timeFormat());
		} else {
			dateFormat = new SimpleDateFormat("H:mm:ss:SSS");
		}

		if (cl.getHandlerList() != null) {
			handlerList = cl.getHandlerList();
		} else {
			handlerList = new ArrayList<ILogHandler>();
			handlerList.add(new ConsoleHandler());
		}
	}

	private static class SingletonHolder {
		private static final Logger INSTANCE = new Logger();
	}

	public static Logger getLogger() {
		return SingletonHolder.INSTANCE;
	}

	public String getTimeStamp() {
		return dateFormat.format(Calendar.getInstance().getTime());
	}

	public void sendMessage(final int level, final Class<?> targetClass, final String msg) {
		if (enabled && level <= logLevel) {

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
