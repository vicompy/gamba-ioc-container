package org.homs.gamba.logging;

import java.lang.reflect.Constructor;

import junit.framework.Assert;

import org.homs.gamba.logging.exception.GambaLoggingException;
import org.homs.gamba.logging.handlers.DummyHandler;
import org.junit.Test;

public class LoggerTest {

	private Logger loggerNewHackedInstance() {
		Constructor<Logger> cons;
		try {
			cons = Logger.class.getDeclaredConstructor();
			cons.setAccessible(true);
			return cons.newInstance();
		} catch (final Exception e) {
			throw new GambaLoggingException("error hackejant una nova instància singletona de Logger", e);
		}
	}

	/**
	 * @param propertiesFile si <tt>null</tt>, no varia la constant
	 */
	private Logger loggingOutput(final String propertiesFile) {
		if (propertiesFile != null) {
			Logger.DEFAULT_CONFIG_FILE = propertiesFile;
		}
		final Logger log = loggerNewHackedInstance();
		final String labelMsg = this.getClass().getSimpleName();

		log.sendMessage(Logger.FATAL, labelMsg, "==================================");
		log.sendMessage(Logger.FATAL, labelMsg, "this is a FATAL log message");
		log.sendMessage(Logger.ERROR, labelMsg, "this is an ERROR log message");
		log.sendMessage(Logger.WARNING, labelMsg, "this is a WARNING log message");
		log.sendMessage(Logger.INFO, labelMsg, "this is a INFO log message");
		log.sendMessage(Logger.DEBUG, labelMsg, "this is a DEBUG log message");

		return log;
	}

	/**
	 * mostra de configuració carregada per fitxer per defecte
	 */
	@Test
	public void test1() {
		final Logger log = loggingOutput(null);
		final DummyHandler DummyHandler = (DummyHandler) log.getFirstMatchingHandler(DummyHandler.class);
		Assert.assertEquals(
				  "["
				+ "[FATAL] LoggerTest: *:*:*,* => ==================================, "
				+ "[FATAL] LoggerTest: *:*:*,* => this is a FATAL log message, "
				+ "[ERROR] LoggerTest: *:*:*,* => this is an ERROR log message, "
				+ "[WARN]  LoggerTest: *:*:*,* => this is a WARNING log message"
				+ "]",
				DummyHandler.getLogs()
				.toString().replaceAll("[0-9]+", "*"));
	}

	/**
	 * fitxer buit (config per-default)
	 */
	@Test
	public void test2() {
		final Logger log = loggingOutput("config-loader-test-1.properties");
		final DummyHandler DummyHandler = (DummyHandler) log.getFirstMatchingHandler(DummyHandler.class);

		Assert.assertEquals("[" + "[FATAL] LoggerTest: *:*:*:* ==================================, "
				+ "[FATAL] LoggerTest: *:*:*:* this is a FATAL log message, "
				+ "[ERROR] LoggerTest: *:*:*:* this is an ERROR log message, "
				+ "[WARN]  LoggerTest: *:*:*:* this is a WARNING log message, "
				+ "[INFO]  LoggerTest: *:*:*:* this is a INFO log message, "
				+ "[DEBUG] LoggerTest: *:*:*:* this is a DEBUG log message" + "]", DummyHandler.getLogs().toString()
				.replaceAll("[0-9]+", "*"));
	}

	/**
	 * carrega una configuració concreta
	 */
	@Test
	public void test3() {
		final Logger log = loggingOutput("config-loader-test-2.properties");
		final DummyHandler DummyHandler = (DummyHandler) log.getFirstMatchingHandler(DummyHandler.class);
		Assert.assertEquals("[" + "[FATAL] LoggerTest: ==================================, "
				+ "[FATAL] LoggerTest: this is a FATAL log message, "
				+ "[ERROR] LoggerTest: this is an ERROR log message, "
				+ "[WARN]  LoggerTest: this is a WARNING log message" + "]", DummyHandler.getLogs().toString());
	}

	/**
	 * mostra en config per defecte, en no trobar el fitxer especificat
	 */
	@Test
	public void test4() {
		final Logger log = loggingOutput("non-existant-config-file");
		final DummyHandler DummyHandler = (DummyHandler) log.getFirstMatchingHandler(DummyHandler.class);
		Assert.assertTrue(DummyHandler == null);
	}

}
