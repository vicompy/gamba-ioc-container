package org.homs.gamba.logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.homs.gamba.logging.exception.GambaException;
import org.homs.gamba.logging.handlers.ConsoleHandler;
import org.homs.gamba.logging.interfaces.IConfigLoader;
import org.homs.gamba.logging.interfaces.ILogHandler;

class ConfigLoader implements IConfigLoader {

	public static final String DEFAULT_DATETIME_FORMAT = "H:mm:ss:SSS";
	public static final int DEFAULT_LOG_LEVEL = Logger.INFO;
	public static final String DEFAULT_CONFIG_FILE = "logging-config.properties";

	public static final String PROP_DISABLED = "disabled";
	public static final String PROP_LOG_LEVEL = "log-level";
	public static final String PROP_SHOW_TIME = "show-time";
	public static final String PROP_TIME_FORMAT = "time-format";
	public static final String PROP_HANDLER_CLASSES = "handler-classes";
	public static final ILogHandler DEFAULT_HANDLER = new ConsoleHandler();

	protected final Properties props;

	/**
	 * no s'ha trobat el fitxer de propietats, s'aplica doncs la config per
	 * defecte; el logger accedirà aquest atribut i avisarà si cal.
	 */
	protected boolean configFileNotFound = false;

	public ConfigLoader() {
		final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAULT_CONFIG_FILE);
		props = new Properties();
		try {
			props.load(is);
		} catch (final IOException e) {
			configFileNotFound = true;
		}
	}

	public ConfigLoader(final String propertiesFile) {
		final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFile);
		if (is == null) {
			configFileNotFound = true;
			props = null;
			return;
		}
		props = new Properties();
		try {
			props.load(is);
		} catch (final IOException e) {
			configFileNotFound = true;
		}
	}

	private String getProperty(final String key) {
		if (props == null) {
			return null;
		} else {
			return props.getProperty(key);
		}
	}

	/**
	 * @see org.homs.gamba.logging.interfaces.IConfigLoader#disableLogging()
	 */
	public boolean disableLogging() {
		final String disabled = getProperty(PROP_DISABLED);
		if (disabled == null) {
			return false;
		} else {
			return "true".equals(disabled.toLowerCase());
		}
	}

	/**
	 * @see org.homs.gamba.logging.interfaces.IConfigLoader#getLogLevel()
	 */
	public int getLogLevel() {
		String logLevel = getProperty(PROP_LOG_LEVEL);
		if (logLevel == null) {
			return DEFAULT_LOG_LEVEL;
		}
		logLevel = logLevel.toLowerCase();

		if ("fatal".equals(logLevel)) {
			return Logger.FATAL;
		} else if ("error".equals(logLevel)) {
			return Logger.ERROR;
		} else if ("warning".equals(logLevel)) {
			return Logger.WARNING;
		} else if ("info".equals(logLevel)) {
			return Logger.INFO;
		} else if ("debug".equals(logLevel)) {
			return Logger.DEBUG;
		}
		return DEFAULT_LOG_LEVEL;
	}

	/**
	 * @see org.homs.gamba.logging.interfaces.IConfigLoader#enableDateTime()
	 */
	public boolean enableDateTime() {
		final String showTime = getProperty(PROP_SHOW_TIME);
		if (showTime == null) {
			return true;
		}
		return "true".equals(showTime.toLowerCase());
	}

	/**
	 * @see org.homs.gamba.logging.interfaces.IConfigLoader#getDateTimeFormat()
	 */
	public String getDateTimeFormat() {
		final String format = getProperty(PROP_TIME_FORMAT);
		if (format == null) {
			return DEFAULT_DATETIME_FORMAT;
		} else {
			return format;
		}
	}

	/**
	 * @see org.homs.gamba.logging.interfaces.IConfigLoader#getHandlerList()
	 */
	@SuppressWarnings("unchecked")
	public List<ILogHandler> getHandlerList() {
		final List<ILogHandler> r = new ArrayList<ILogHandler>();

		if (getProperty(PROP_HANDLER_CLASSES) == null) {
			r.add(DEFAULT_HANDLER);
			return r;
		}

		final String handlers[] = getProperty(PROP_HANDLER_CLASSES).replaceAll("\\s*", "").split(",");

		for (final String h : handlers) {
			try {
				final Class<ILogHandler> c = (Class<ILogHandler>) Class.forName(h);
				r.add(c.newInstance());
			} catch (final ClassNotFoundException e) {
				throw new GambaException("Classe ILogHandler no trobada: " + h, e);
			} catch (final InstantiationException e) {
				throw new GambaException("error instanciant: " + h, e);
			} catch (final IllegalAccessException e) {
				throw new GambaException("error d'accés instanciant: " + h, e);
			}
		}
		return r;
	}

	public boolean isConfigFileNotFound() {
		return configFileNotFound;
	}

}
