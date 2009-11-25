package org.gro.logging;

import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class GroLog {

	private static final String CONFIG_PROPERTIES_FILE_NAME = "gro-logging-config.properties";
	private final Logger logger;

	private GroLog(final Logger logger) {
		this.logger = logger;
	}

	public static GroLog getGroLogger(final Class<?> classScope) {
		return getGroLogger(classScope, Level.INFO);
	}

	public static GroLog getGroLogger(final Class<?> classScope, final Level level) {
		final Logger logger = Logger.getLogger(classScope.getName());
		logger.setLevel(level);
		return new GroLog(logger);
	}

//	public GroLog config(final IGroConfig config) {
//		return config("", config);
//	}

	/**
	 * aquesta configuració és aplicada de forma global, a tota la jerarquia de
	 * loggers
	 *
	 * @param baseLoggerTarget
	 * @param config
	 * @return
	 */
	public GroLog config() {
		return config(new ConfigLoader(CONFIG_PROPERTIES_FILE_NAME));
	}

	private GroLog config(final IGroConfig config) {

		// TODO global logging config
		final Formatter formatter = new GroFormatter(config.getTimeFormat());

		// el handler de fitxer és aplicat a tota la jerarquia
		if (config.getOutputFileName() != null) {
			try {
				Logger.getLogger("").addHandler(new FileHandler(config.getOutputFileName()));
			} catch (final Exception e) {
				throw new GroLoggingException("error adding output handler", e);
			}
		}

		// el formatter és aplicat a tots els handlers de tota la jerarquia
		final Handler[] handlers = Logger.getLogger("").getHandlers();
		for (int index = 0; index < handlers.length; index++) {
			handlers[index].setLevel(config.getMainLevel());
			handlers[index].setFormatter(formatter);
		}

		final String propNotFound = config.warningMessageIfConfigFileNotFound();
		if (propNotFound != null) {
			warning(propNotFound);
		} else {
			info("gro-logging properly configured with a global level: " + config.getMainLevel().toString());
		}

		return this;
	}

	public void severe(final Object msg) {
		this.log(Level.SEVERE, msg);
	}

	public void warning(final Object msg) {
		this.log(Level.WARNING, msg);
	}

	public void info(final Object msg) {
		this.log(Level.INFO, msg);
	}

	public void config(final Object msg) {
		this.log(Level.CONFIG, msg);
	}

	public void fine(final Object msg) {
		this.log(Level.FINE, msg);
	}

	public void finer(final Object msg) {
		this.log(Level.FINER, msg);
	}

	public void finest(final Object msg) {
		this.log(Level.FINEST, msg);
	}

	public void log(final Level level, final Object msg) {
		if (msg instanceof Throwable) {
			logger.log(level, logException((Throwable) msg));
		} else {
			logger.log(level, msg.toString());
		}
	}

	public void severe(final Object... msg) {
		this.log(Level.SEVERE, msg);
	}

	public void warning(final Object... msg) {
		this.log(Level.WARNING, msg);
	}

	public void info(final Object... msg) {
		this.log(Level.INFO, msg);
	}

	public void config(final Object... msg) {
		this.log(Level.CONFIG, msg);
	}

	public void fine(final Object... msg) {
		this.log(Level.FINE, msg);
	}

	public void finer(final Object... msg) {
		this.log(Level.FINER, msg);
	}

	public void finest(final Object... msg) {
		this.log(Level.FINEST, msg);
	}

	public void log(final Level level, final Object... msgs) {
		final StringBuffer strb = new StringBuffer();
		for (final Object msg : msgs) {
			strb.append(msg.toString());
		}
		logger.log(level, strb.toString());
	}

	private String logException(final Throwable throwable) {
		final StringBuffer strb = new StringBuffer();

		Throwable currentThrowable = throwable;
		strb.append('\n');
		do {

			// titular de la traça
			strb.append(currentThrowable.toString());
			strb.append('\n');

			// traça de crides en stack
			for (final StackTraceElement traceElement : currentThrowable.getStackTrace()) {
				strb.append("  ");
				strb.append(traceElement.toString());
				strb.append('\n');
			}

			// recupera la causa de l'excepció (una altra excepció)
			currentThrowable = currentThrowable.getCause();

		} while (currentThrowable != null);

		return strb.toString();
	}

	public Logger getWrappedJdkApiLogger() {
		return logger;
	}

	public static GroLog getBaseGroLogger() {
		return new GroLog(Logger.getLogger(""));
	}

}
