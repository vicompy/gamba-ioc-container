package org.gro.connectionpool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

	private final Properties props;

	public PropertiesLoader(final String propertiesFile) {
		final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(
				propertiesFile);
		if (is == null) {
			throw new GroPoolingException("properites file not found");
		}
		props = new Properties();
		try {
			props.load(is);
		} catch (final IOException e) {
			throw new GroPoolingException("properites file not found");
		}
	}

	public String getProperty(final String name) {
		final String r = props.getProperty(name);
		if (r == null) {
			throw new GroPoolingException("property not found: " + name);
		}
		return r;
	}

}