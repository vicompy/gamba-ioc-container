package org.gro.logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

public class ConfigLoader implements IGroConfig {

	public static final Level DEFAULT_LOG_LEVEL = Level.INFO;
	public static final String DEFAULT_DATETIME_FORMAT = "H:mm:ss:SSS";

	public static final String PROP_LOG_LEVEL = "log-level";
	public static final String PROP_TIME_FORMAT = "time-format";
	public static final String OUTPUT_FILENAME = "log-file-name";

	protected Properties props;

	private final String propertiesFile;



	public static final Map<String, Level> LEVEL_MAP = new HashMap<String, Level>();
	{
		LEVEL_MAP.put("severe", Level.SEVERE);
		LEVEL_MAP.put("warning", Level.WARNING);
		LEVEL_MAP.put("info", Level.INFO);
		LEVEL_MAP.put("fine", Level.FINE);
		LEVEL_MAP.put("finer", Level.FINER);
		LEVEL_MAP.put("finest", Level.FINEST);
		LEVEL_MAP.put("off", Level.OFF);
		LEVEL_MAP.put("all", Level.ALL);
	}

	/**
	 * no s'ha trobat el fitxer de propietats, s'aplica doncs la config per
	 * defecte; el logger accedirà aquest atribut i avisarà si cal.
	 */
	protected boolean configFileNotFound = false;


	public String warningMessageIfConfigFileNotFound() {
		if (configFileNotFound) {
			return "caution, logging config file not found: '"+this.propertiesFile+"', using default config.";
		}
		return null;
	}

	public ConfigLoader(final String propertiesFile) {
		this.propertiesFile = propertiesFile;
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
		}
		return props.getProperty(key);
	}

	public Level getMainLevel() {
		final String level = getProperty(PROP_LOG_LEVEL);
		final Level l = LEVEL_MAP.get(level);
		if (l == null) {
			return DEFAULT_LOG_LEVEL;
		}
		return l;
	}

	public String getOutputFileName() {
		final String filename = getProperty(OUTPUT_FILENAME);
		return filename;
	}

	public String getTimeFormat() {
		final String format = getProperty(PROP_TIME_FORMAT);
		if (format == null) {
			return "";
		}
		return format;
	}

}
