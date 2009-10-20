package org.homs.gamba.logging;

import java.util.List;

interface IConfigLoader {

	public abstract boolean isDisabled();

	public abstract int getLogLevel();

	public abstract boolean showTime();

	public abstract String timeFormat();

	public abstract List<ILogHandler> getHandlerList();
}