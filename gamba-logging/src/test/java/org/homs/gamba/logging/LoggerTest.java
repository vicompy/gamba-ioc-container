package org.homs.gamba.logging;

import java.util.ArrayList;
import java.util.List;

import org.homs.gamba.logging.handlers.DummyHandler;
import org.homs.gamba.logging.interfaces.IConfigLoader;
import org.homs.gamba.logging.interfaces.ILogHandler;
import org.homs.gamba.utils.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import static org.homs.gamba.stub.bsyntax.Stubber.*;

public class LoggerTest {

	@Test
	public void test1() {

//		final IAdder adderStub = (IAdder) createStub(IAdder.class);
//		thenReturn(3).when(adderStub).add(1, 2);
//		thenReturn(5).when(adderStub).add(2, 3);
//		play(adderStub);

		/*
		 * stub behavior definition
		 */
		final List<ILogHandler> handlerList = new ArrayList<ILogHandler>();
		handlerList.add(new DummyHandler());

//		final IStubber<IConfigLoader> lcStubber = Stubber.createStub(IConfigLoader.class);
//		lcStubber.doReturn(false).when().disableLogging();
//		lcStubber.doReturn(ILogger.INFO).when().getLogLevel();
//		lcStubber.doReturn(false).when().enableDateTime();
//		lcStubber.doReturn("").when().getDateTimeFormat();
//		lcStubber.doReturn(handlerList).when().getHandlerList();
//		lcStubber.doReturn(false).when().isConfigFileNotFound();
//		final IConfigLoader configLoaderStub = lcStubber.play();

		final IConfigLoader configLoader = (IConfigLoader) createStub(IConfigLoader.class);
		thenReturn(false).when(configLoader).disableLogging();
		thenReturn(ILogger.INFO).when(configLoader).getLogLevel();
		thenReturn(false).when(configLoader).enableDateTime();
		thenReturn("").when(configLoader).getDateTimeFormat();
		thenReturn(handlerList).when(configLoader).getHandlerList();
		thenReturn(false).when(configLoader).isConfigFileNotFound();
		play(configLoader);

		/*
		 * hacked singleton instance
		 */
		final Logger logger = (Logger) TestUtils.newHackedInstance(Logger.class, configLoader);

		/*
		 * run testing code
		 */
		logger.sendMessage(ILogger.WARNING, this.getClass().getSimpleName(), "hi");
		logger.sendMessage(ILogger.WARNING, this.getClass().getSimpleName(), "1","2","3");

		/*
		 * assertions
		 */
		final List<String> outlogs = ((DummyHandler) handlerList.get(0)).getLogs();

		Assert.assertEquals(
			"[" +
			"[WARN]  LoggerTest: hi, " +
			"[WARN]  LoggerTest: 123" +
			"]",
			outlogs.toString()
		);

	}

//		final DummyHandler DummyHandler = (DummyHandler) log.getFirstMatchingHandler(DummyHandler.class);
//		Assert.assertEquals(
//				  "["
//				+ "[FATAL] LoggerTest: *:*:*,* => ==================================, "
//				+ "[FATAL] LoggerTest: *:*:*,* => this is a FATAL log message, "
//				+ "[ERROR] LoggerTest: *:*:*,* => this is an ERROR log message, "
//				+ "[WARN]  LoggerTest: *:*:*,* => this is a WARNING log message"
//				+ "]",
//				DummyHandler.getLogs()
//				.toString().replaceAll("[0-9]+", "*"));


//	/**
//	 * mostra de configuració carregada per fitxer per defecte
//	 */
//	@Test
//	public void test1() {
//		final Logger log = loggingOutput(null);
//		final DummyHandler DummyHandler = (DummyHandler) log.getFirstMatchingHandler(DummyHandler.class);
//		Assert.assertEquals(
//				  "["
//				+ "[FATAL] LoggerTest: *:*:*,* => ==================================, "
//				+ "[FATAL] LoggerTest: *:*:*,* => this is a FATAL log message, "
//				+ "[ERROR] LoggerTest: *:*:*,* => this is an ERROR log message, "
//				+ "[WARN]  LoggerTest: *:*:*,* => this is a WARNING log message"
//				+ "]",
//				DummyHandler.getLogs()
//				.toString().replaceAll("[0-9]+", "*"));
//	}
//
//	/**
//	 * fitxer buit (config per-default)
//	 */
//	@Test
//	public void test2() {
//		final Logger log = loggingOutput("config-loader-test-1.properties");
//		final DummyHandler DummyHandler = (DummyHandler) log.getFirstMatchingHandler(DummyHandler.class);
//
//		Assert.assertEquals("[" + "[FATAL] LoggerTest: *:*:*:* ==================================, "
//				+ "[FATAL] LoggerTest: *:*:*:* this is a FATAL log message, "
//				+ "[ERROR] LoggerTest: *:*:*:* this is an ERROR log message, "
//				+ "[WARN]  LoggerTest: *:*:*:* this is a WARNING log message, "
//				+ "[INFO]  LoggerTest: *:*:*:* this is a INFO log message, "
//				+ "[DEBUG] LoggerTest: *:*:*:* this is a DEBUG log message" + "]", DummyHandler.getLogs().toString()
//				.replaceAll("[0-9]+", "*"));
//	}
//
//	/**
//	 * carrega una configuració concreta
//	 */
//	@Test
//	public void test3() {
//		final Logger log = loggingOutput("config-loader-test-2.properties");
//		final DummyHandler DummyHandler = (DummyHandler) log.getFirstMatchingHandler(DummyHandler.class);
//		Assert.assertEquals("[" + "[FATAL] LoggerTest: ==================================, "
//				+ "[FATAL] LoggerTest: this is a FATAL log message, "
//				+ "[ERROR] LoggerTest: this is an ERROR log message, "
//				+ "[WARN]  LoggerTest: this is a WARNING log message" + "]", DummyHandler.getLogs().toString());
//	}
//
//	/**
//	 * mostra en config per defecte, en no trobar el fitxer especificat
//	 */
//	@Test
//	public void test4() {
//		final Logger log = loggingOutput("non-existant-config-file");
//		final DummyHandler DummyHandler = (DummyHandler) log.getFirstMatchingHandler(DummyHandler.class);
//		Assert.assertTrue(DummyHandler == null);
//	}

}