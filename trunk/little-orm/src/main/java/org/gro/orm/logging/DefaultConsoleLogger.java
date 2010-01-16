package org.gro.orm.logging;

public class DefaultConsoleLogger implements IGroOrmLogger {

	public void error(final Class<?> author, final String message) {
		System.err.println("[ERROR] " + author.getSimpleName() + ": " + message);
	}

	public void info(final Class<?> author, final String message) {
		System.out.println("[INFO] " + author.getSimpleName() + ": " + message);
	}

	public void debug(final Class<?> author, final String message) {
		System.out.println("[DEBUG] " + author.getSimpleName() + ": " + message);
	}

}
