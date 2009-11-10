package org.homs.gamba.connectionpool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

	private final static String propertiesFile = "pool-config.properties";

	private final Properties props;

	public PropertiesLoader() {
		final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(
				propertiesFile);
		if (is == null) {
			throw new PoolException("properites file not found");
		}
		props = new Properties();
		try {
			props.load(is);
		} catch (final IOException e) {
			throw new PoolException("properites file not found");
		}
	}

	public String getProperty(final String name) {
		final String r = props.getProperty(name);
		if (r == null) {
			throw new PoolException("property not found: " + name);
		}
		return r;
	}

}