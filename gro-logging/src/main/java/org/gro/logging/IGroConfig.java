package org.gro.logging;

import java.util.logging.Level;

public interface IGroConfig {

	String getTimeFormat();

	Level getMainLevel();

	String getOutputFileName();

	String warningMessageIfConfigFileNotFound();
}