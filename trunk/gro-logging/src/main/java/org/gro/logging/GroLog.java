package org.gro.logging;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class GroLog {

	private final static Level GLOBAL_FILTERING_LEVEL = Level.ALL;

	private final Logger logger;

	private GroLog(final Logger logger) {
		this.logger = logger;
	}

	public static GroLog getGroLogger(final Class<?> classScope) {
		return getGroLogger(classScope, Level.INFO);
	}

	public static GroLog getGroLogger(final Class<?> classScope, final Level level) {
		final Logger logger = Logger.getLogger(classScope.getName());

		// local logging config
		logger.setLevel(level);

		// TODO global logging config
		final Handler[] handlers = Logger.getLogger("").getHandlers();
		for (int index = 0; index < handlers.length; index++) {
			handlers[index].setLevel(GLOBAL_FILTERING_LEVEL);
			handlers[index].setFormatter(new GroFormatter());
		}

		return new GroLog(logger);
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
			// tracaError.append("\n\n");

			// recupera la causa de l'excepció (una altra excepció)
			currentThrowable = currentThrowable.getCause();

		} while (currentThrowable != null);

		return strb.toString();
	}

}
