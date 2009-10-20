package org.homs.gamba.logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.homs.gamba.logging.exception.GambaException;

class ConfigLoader implements IConfigLoader {

	final static int defaultLogLevel = Logger.INFO;

	private final Properties props;

	public ConfigLoader() {
		final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(
				"logging-config.properties");
		props = new Properties();
		try {
			props.load(is);
		} catch (final IOException e) {
			throw new GambaException("error carregant configuració de propietats");
			// TODO si error, aplicar config per defecte!
		}
	}

	/* (non-Javadoc)
	 * @see org.homs.gamba.logging.IConfigLoader#isDisabled()
	 */
	public boolean isDisabled() {
		final String disabled = props.getProperty("disabled");

		return "true".equals(disabled.toLowerCase());
	}

	/* (non-Javadoc)
	 * @see org.homs.gamba.logging.IConfigLoader#getLogLevel()
	 */
	public int getLogLevel() {
		final String logLevel = props.getProperty("log-level").toLowerCase();

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
		return defaultLogLevel;
	}

	/* (non-Javadoc)
	 * @see org.homs.gamba.logging.IConfigLoader#showTime()
	 */
	public boolean showTime() {
		final String showTime = props.getProperty("show-time");

		return "true".equals(showTime.toLowerCase());
	}

	/* (non-Javadoc)
	 * @see org.homs.gamba.logging.IConfigLoader#timeFormat()
	 */
	public String timeFormat() {
		return props.getProperty("time-format");
	}

	/* (non-Javadoc)
	 * @see org.homs.gamba.logging.IConfigLoader#getHandlerList()
	 */
	@SuppressWarnings("unchecked")
	public List<ILogHandler> getHandlerList() {
		final List<ILogHandler> r = new ArrayList<ILogHandler>();

		System.out.println(props.getProperty("handler-classes").replaceAll("\\s*", ""));
		final String handlers[] = props.getProperty("handler-classes").replaceAll("\\s*", "").split(",");

		for (final String h : handlers) {
			try {
				final Class<ILogHandler> c = (Class<ILogHandler>) Class.forName(h);
				r.add(c.newInstance());
			} catch (final ClassNotFoundException e) {
				throw new GambaException("Classe ILogHandler no trobada: " + h);
			} catch (final InstantiationException e) {
				throw new GambaException("error instanciant: " + h);
			} catch (final IllegalAccessException e) {
				throw new GambaException("error d'accés instanciant: " + h);
			}
		}
		return r;
	}

	// disabled=false
	// log-level=debug
	// show-time=true
	// handler-classes=org.homs.gamba.logging.handlers.ConsoleHandler

}
