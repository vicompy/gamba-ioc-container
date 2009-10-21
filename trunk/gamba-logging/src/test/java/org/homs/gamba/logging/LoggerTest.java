package org.homs.gamba.logging;

import org.junit.Test;

public class LoggerTest {

	private static final Log log = new Log(LoggerTest.class);

	/**
	 * mostra de configuració carregada per fitxer per defecte
	 */
	@Test
	public void test1() {
		log.fatal("this is a FATAL log message");
		log.error("this is an ERROR log message");
		log.warning("this is a WARNING log message");
		log.info("this is a INFO log message");
		log.debug("this is a DEBUG log message");
	}

	/**
	 * fitxer buit (config per-default)
	 */
	@Test
	public void test2() {
		log.resetup(new ConfigLoader("config-loader-test-1.properties"));

		log.fatal("this is a FATAL log message");
		log.error("this is an ERROR log message");
		log.warning("this is a WARNING log message");
		log.info("this is a INFO log message");
		log.debug("this is a DEBUG log message");
	}

	/**
	 * carrega una configuració concreta
	 */
	@Test
	public void test3() {
		log.resetup(new ConfigLoader("config-loader-test-2.properties"));

		log.fatal("this is a FATAL log message");
		log.error("this is an ERROR log message");
		log.warning("this is a WARNING log message");
		log.info("this is a INFO log message");
		log.debug("this is a DEBUG log message");
	}

	/**
	 * mostra en config per defecte, en no trobar el fitxer especificat
	 */
	@Test
	public void test4() {
		log.resetup(new ConfigLoader("non-existant-config-file"));

		log.fatal("this is a FATAL log message");
		log.error("this is an ERROR log message");
		log.warning("this is a WARNING log message");
		log.info("this is a INFO log message");
		log.debug("this is a DEBUG log message");
	}

}
