package org.homs.gamba.logging;

import org.homs.gamba.logging.handlers.ListHandler;
import org.homs.gamba.logging.interfaces.ILogHandler;
import org.junit.Test;

public class LoggerTest {

	private static final Log log = new Log(LoggerTest.class);
//	private static final ILogHandler listHandler = log.getFirstMatchingHandler(ListHandler.class);

	public void loggingOutput() {
		log.fatal("this is a FATAL log message <==============================================");
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
	}

	/**
	 * fitxer buit (config per-default)
	 */
	@Test
	public void test2() {
		log.resetup(new ConfigLoader("config-loader-test-1.properties"));
		loggingOutput();

//		listHandler. // TODO testar la listHandler
	}

	/**
	 * carrega una configuració concreta
	 */
	@Test
	public void test3() {
		log.resetup(new ConfigLoader("config-loader-test-2.properties"));
		loggingOutput();
	}

	/**
	 * mostra en config per defecte, en no trobar el fitxer especificat
	 */
	@Test
	public void test4() {
		log.resetup(new ConfigLoader("non-existant-config-file"));
		loggingOutput();
	}

}
