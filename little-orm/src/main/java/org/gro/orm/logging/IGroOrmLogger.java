package org.gro.orm.logging;

public interface IGroOrmLogger {

	void error(Class<?> author, String message);
	void info(Class<?> author, String message);
	void debug(Class<?> author, String message);

}
