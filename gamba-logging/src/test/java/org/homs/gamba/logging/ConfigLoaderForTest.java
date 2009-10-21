package org.homs.gamba.logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class ConfigLoaderForTest extends ConfigLoader {

	// això només fa falta per als testos
	public ConfigLoaderForTest(final String propertiesFile) {
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

}
