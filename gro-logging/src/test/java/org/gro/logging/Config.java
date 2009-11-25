package org.gro.logging;

import java.util.logging.Level;

public class Config implements IGroConfig {

	/**
	 * @see org.gro.logging.IGroConfig#getTimeFormat()
	 */
	public String getTimeFormat() {
		return "HH:mm:SSS dd/MMM/yyy |";
	}

	/**
	 * @see org.gro.logging.IGroConfig#getMainLevel()
	 */
	public Level getMainLevel() {
		return Level.FINEST;
	}

	public String getOutputFileName() {
		return "./log.txt";
	}

	public String warningMessageIfConfigFileNotFound() {
		return null;
	}
}
