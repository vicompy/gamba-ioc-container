package org.homs.gamba.logging;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.homs.gamba.logging.exception.GambaLoggingException;
import org.homs.gamba.logging.handlers.DummyHandler;
import org.homs.gamba.logging.interfaces.IConfigLoader;
import org.homs.gamba.logging.interfaces.ILogHandler;
import org.homs.gamba.stub.syntax.IStubber;
import org.homs.gamba.stub.syntax.Stubber;
import org.junit.Assert;
import org.junit.Test;

public class LoggerTest {

	private ILogger loggerNewHackedInstance(final IConfigLoader configLoader) {
		Constructor<Logger> cons;
		try {
			cons = Logger.class.getDeclaredConstructor(IConfigLoader.class);
			cons.setAccessible(true);
			return cons.newInstance(configLoader);
		} catch (final Exception e) {
			throw new GambaLoggingException("error hackejant una nova instància singletona de Logger", e);
		}
	}
//	private ILogger loggerNewHackedInstance() {
//		Constructor<Logger> cons;
//		try {
//			cons = Logger.class.getDeclaredConstructor();
//			cons.setAccessible(true);
//			return cons.newInstance();
//		} catch (final Exception e) {
//			throw new GambaLoggingException("error hackejant una nova instància singletona de Logger", e);
//		}
//	}

//	/**
//	 * @param propertiesFile si <tt>null</tt>, no varia la constant
//	 */
//	private ILogger loggingOutput(final String propertiesFile) {
//		final ILogger log;
//		if (propertiesFile != null) {
//			log = loggerNewHackedInstance(new ConfigLoader(propertiesFile));
//		} else {
//			log = loggerNewHackedInstance();
//		}
//		final String labelMsg = this.getClass().getSimpleName();
//
//		log.sendMessage(ILogger.FATAL, labelMsg, "==================================");
//		log.sendMessage(ILogger.FATAL, labelMsg, "this is a FATAL log message");
//		log.sendMessage(ILogger.ERROR, labelMsg, "this is an ERROR log message");
//		log.sendMessage(ILogger.WARNING, labelMsg, "this is a WARNING log message");
//		log.sendMessage(ILogger.INFO, labelMsg, "this is a INFO log message");
//		log.sendMessage(ILogger.DEBUG, labelMsg, "this is a DEBUG log message");
//
//		return log;
//	}


	@Test
	public void test1() {
		final List<ILogHandler> handlerList = new ArrayList<ILogHandler>();
		handlerList.add(new DummyHandler());

		final IStubber<IConfigLoader> lcStubber = Stubber.createStub(IConfigLoader.class);
		lcStubber.doReturn(false).when().disableLogging();
		lcStubber.doReturn(ILogger.INFO).when().getLogLevel();
		lcStubber.doReturn(false).when().enableDateTime();
		lcStubber.doReturn("").when().getDateTimeFormat();
		lcStubber.doReturn(handlerList).when().getHandlerList();
		lcStubber.doReturn(false).when().isConfigFileNotFound();
		final IConfigLoader configLoaderStub = lcStubber.play();

		final Logger logger = (Logger) loggerNewHackedInstance(configLoaderStub);

		logger.sendMessage(ILogger.WARNING, this.getClass().getSimpleName(), "hi");
		logger.sendMessage(ILogger.WARNING, this.getClass().getSimpleName(), "1","2","3");

		final List<String> outlogs = ((DummyHandler) handlerList.get(0)).getLogs();

		Assert.assertEquals(
			"[" +
			"[WARN]  LoggerTest: hi, " +
			"[WARN]  LoggerTest: 123" +
			"]",
			outlogs.toString()
		);

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
	}


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