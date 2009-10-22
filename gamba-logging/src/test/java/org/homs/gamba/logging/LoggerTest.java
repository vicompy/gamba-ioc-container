package org.homs.gamba.logging;

import java.lang.reflect.Constructor;

import junit.framework.Assert;

import org.homs.gamba.logging.exception.GambaException;
import org.homs.gamba.logging.handlers.DummyHandler;
import org.junit.Test;

public class LoggerTest {

//	private static final Log log = new Log(LoggerTest.class);

	// private static final ListHandler listHandler = (ListHandler)
	// log.getFirstMatchingHandler(ListHandler.class);

	private Logger loggerNewHackedInstance() {
		Constructor<Logger> cons;
		try {
			cons = Logger.class.getDeclaredConstructor();
			cons.setAccessible(true);
			return cons.newInstance();
		} catch (final Exception e) {
			throw new GambaException("error hackejant una nova instància singletona de Logger", e);
		}
	}

	/**
	 * @param propertiesFile si <tt>null</tt>, no varia la constant
	 */
	private Logger loggingOutput(final String propertiesFile) {
		// listHandler.clear();

		if (propertiesFile != null) {
			Logger.DEFAULT_CONFIG_FILE = propertiesFile;
		}
		final Logger log = loggerNewHackedInstance();
		final String labelMsg = this.getClass().getSimpleName();

		log.fatal(labelMsg, "==================================");
		log.fatal(labelMsg, "this is a FATAL log message");
		log.error(labelMsg, "this is an ERROR log message");
		log.warning(labelMsg, "this is a WARNING log message");
		log.info(labelMsg, "this is a INFO log message");
		log.debug(labelMsg, "this is a DEBUG log message");

		return log;
	}

	/**
	 * mostra de configuració carregada per fitxer per defecte
	 */
	@Test
	public void test1() {
		final Logger log = loggingOutput(null);
		final DummyHandler listHandler = (DummyHandler) log.getFirstMatchingHandler(DummyHandler.class);
		Assert.assertEquals(
				  "["
				+ "[FATAL] LoggerTest: *:*:*,* => ==================================, "
				+ "[FATAL] LoggerTest: *:*:*,* => this is a FATAL log message, "
				+ "[ERROR] LoggerTest: *:*:*,* => this is an ERROR log message, "
				+ "[WARN]  LoggerTest: *:*:*,* => this is a WARNING log message"
				+ "]",
				listHandler.getLogs()
				.toString().replaceAll("[0-9]+", "*"));
	}

	/**
	 * fitxer buit (config per-default)
	 */
	@Test
	public void test2() {
		final Logger log = loggingOutput("config-loader-test-1.properties");
		final DummyHandler listHandler = (DummyHandler) log.getFirstMatchingHandler(DummyHandler.class);

		Assert.assertEquals("[" + "[FATAL] LoggerTest: *:*:*:* ==================================, "
				+ "[FATAL] LoggerTest: *:*:*:* this is a FATAL log message, "
				+ "[ERROR] LoggerTest: *:*:*:* this is an ERROR log message, "
				+ "[WARN]  LoggerTest: *:*:*:* this is a WARNING log message, "
				+ "[INFO]  LoggerTest: *:*:*:* this is a INFO log message, "
				+ "[DEBUG] LoggerTest: *:*:*:* this is a DEBUG log message" + "]", listHandler.getLogs().toString()
				.replaceAll("[0-9]+", "*"));
	}

	/**
	 * carrega una configuració concreta
	 */
	@Test
	public void test3() {
		final Logger log = loggingOutput("config-loader-test-2.properties");
		final DummyHandler listHandler = (DummyHandler) log.getFirstMatchingHandler(DummyHandler.class);
		Assert.assertEquals("[" + "[FATAL] LoggerTest: ==================================, "
				+ "[FATAL] LoggerTest: this is a FATAL log message, "
				+ "[ERROR] LoggerTest: this is an ERROR log message, "
				+ "[WARN]  LoggerTest: this is a WARNING log message" + "]", listHandler.getLogs().toString());
	}

	/**
	 * mostra en config per defecte, en no trobar el fitxer especificat
	 */
	@Test
	public void test4() {
		final Logger log = loggingOutput("non-existant-config-file");
		final DummyHandler listHandler = (DummyHandler) log.getFirstMatchingHandler(DummyHandler.class);
		Assert.assertTrue(listHandler == null);
	}

}
