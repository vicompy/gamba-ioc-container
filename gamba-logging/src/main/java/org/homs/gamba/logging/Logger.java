package org.homs.gamba.logging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Logger {

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

	private static final String[] levelTags = new String[] { "FATAL", "ERROR", "WARNING", "INFO", "DEBUG" };

	private final List<ILogHandler> handlerList;
	private boolean enabled;
	private int logLevel;
	private boolean showDate;

	public Logger addHandler(final ILogHandler handler) {
		handlerList.add(handler);
		return this;
	}

	public Logger setLevel(final int level) {
		logLevel = level;
		return this;
	}

	public Logger setDisabled() {
		enabled = false;
		return this;
	}

	public Logger disableDateShowing() {
		showDate = false;
		return this;
	}

	public List<ILogHandler> getHandlerList() {
		return handlerList;
	}

	public Logger cleanConfig() {
		handlerList.clear();
		enabled = true;
		logLevel = 6;
		showDate = true;
		return this;
	}

	// ===============================================================
	//
	// SINGLETON
	//
	// ===============================================================

	private Logger() {
		handlerList = new ArrayList<ILogHandler>();
	}

	private static class SingletonHolder {
		private static final Logger INSTANCE = new Logger().cleanConfig();
	}

	public static Logger getLogger() {
		return SingletonHolder.INSTANCE;
	}

	public void fatal(final String msg) {
		sendMessage(0, msg);
	}

	public void error(final String msg) {
		sendMessage(1, msg);
	}

	public void warning(final String msg) {
		sendMessage(2, msg);
	}

	public void info(final String msg) {
		sendMessage(3, msg);
	}

	public void debug(final String msg) {
		sendMessage(4, msg);
	}

	private void sendMessage(final int level, final String msg) {
		if (enabled && level <= logLevel) {

			final StringBuffer rendMsg = new StringBuffer();
			rendMsg.append('[');
			rendMsg.append(levelTags[level]);
			rendMsg.append("] ");
			if (showDate) {
				rendMsg.append(new Date().toString());
				rendMsg.append(' ');
			}
			rendMsg.append(msg);

			for (final ILogHandler h : handlerList) {
				h.sendMessage(rendMsg.toString());
			}
		}

	}

}
