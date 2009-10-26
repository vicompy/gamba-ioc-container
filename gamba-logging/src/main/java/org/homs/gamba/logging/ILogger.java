package org.homs.gamba.logging;

import org.homs.gamba.logging.interfaces.ILogHandler;


interface ILogger {

	public static final int FATAL = 0;
	public static final int ERROR = 1;
	public static final int WARNING = 2;
	public static final int INFO = 3;
	public static final int DEBUG = 4;

	public abstract void sendMessage(final int level, final String label, final String msg);

	// TODO nou, testar
	public abstract void sendMessage(final int level, final String label, final Object... msgs);

	public abstract ILogHandler getFirstMatchingHandler(final Class<? extends ILogHandler> handlerClass);

}