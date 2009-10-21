package org.homs.gamba.logging;

import junit.framework.Assert;

import org.homs.gamba.logging.handlers.ListHandler;
import org.junit.Test;

public class LoggerTest {

	private static final Log log = new Log(LoggerTest.class);

//	private static final ListHandler listHandler = (ListHandler) log.getFirstMatchingHandler(ListHandler.class);

	public void loggingOutput() {
//		listHandler.clear();
		log.fatal("==================================");
		log.fatal("this is a FATAL log message");
		log.error("this is an ERROR log message");
		log.warning("this is a WARNING log message");
		log.info("this is a INFO log message");
		log.debug("this is a DEBUG log message");
	}

	/**
	 * mostra de configuració carregada per fitxer per defecte
	 */
	@Test
	public void test1() {
		loggingOutput();
		final ListHandler listHandler = (ListHandler) log.getFirstMatchingHandler(ListHandler.class);
		Assert.assertEquals(
			"[" +
			"[FATAL] LoggerTest: *:*:*,* => ==================================, " +
			"[FATAL] LoggerTest: *:*:*,* => this is a FATAL log message, " +
			"[ERROR] LoggerTest: *:*:*,* => this is an ERROR log message, " +
			"[WARN]  LoggerTest: *:*:*,* => this is a WARNING log message" +
			"]",
			listHandler.getLogs().toString().replaceAll("[0-9]+", "*"));
	}

	/**
	 * fitxer buit (config per-default)
	 */
	@Test
	public void test2() {
		log.resetup(new ConfigLoader("config-loader-test-1.properties"));
		final ListHandler listHandler = (ListHandler) log.getFirstMatchingHandler(ListHandler.class);
		loggingOutput();

		Assert.assertEquals(
			"[" +
			"[FATAL] LoggerTest: *:*:*:* ==================================, " +
			"[FATAL] LoggerTest: *:*:*:* this is a FATAL log message, " +
			"[ERROR] LoggerTest: *:*:*:* this is an ERROR log message, " +
			"[WARN]  LoggerTest: *:*:*:* this is a WARNING log message, " +
			"[INFO]  LoggerTest: *:*:*:* this is a INFO log message, " +
			"[DEBUG] LoggerTest: *:*:*:* this is a DEBUG log message" +
			"]",
			listHandler.getLogs().toString().replaceAll("[0-9]+", "*"));
	}

	/**
	 * carrega una configuració concreta
	 */
	@Test
	public void test3() {
		log.resetup(new ConfigLoader("config-loader-test-2.properties"));
		final ListHandler listHandler = (ListHandler) log.getFirstMatchingHandler(ListHandler.class);
		loggingOutput();
		Assert.assertEquals(
			"[" +
			"[FATAL] LoggerTest: ==================================, " +
			"[FATAL] LoggerTest: this is a FATAL log message, " +
			"[ERROR] LoggerTest: this is an ERROR log message, " +
			"[WARN]  LoggerTest: this is a WARNING log message" +
			"]",
			listHandler.getLogs().toString());
	}

	/**
	 * mostra en config per defecte, en no trobar el fitxer especificat
	 */
	@Test
	public void test4() {
		log.resetup(new ConfigLoader("non-existant-config-file"));
		final ListHandler listHandler = (ListHandler) log.getFirstMatchingHandler(ListHandler.class);
		loggingOutput();
		Assert.assertTrue(listHandler == null);
	}

}
