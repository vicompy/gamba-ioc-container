package org.homs.gamba.logging.interfaces;

import java.util.List;

public interface IConfigLoader {

	public boolean disableLogging();

	public int getLogLevel();

	public boolean enableDateTime();

	public String getDateTimeFormat();

	public List<ILogHandler> getHandlerList();

	public boolean isConfigFileNotFound();

}