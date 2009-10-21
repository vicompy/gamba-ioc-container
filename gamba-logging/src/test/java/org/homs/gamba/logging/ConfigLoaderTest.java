package org.homs.gamba.logging;

import junit.framework.Assert;

import org.homs.gamba.logging.handlers.ConsoleHandler;
import org.homs.gamba.logging.handlers.ListHandler;
import org.junit.Test;

public class ConfigLoaderTest {

	/**
	 * verifica que, si no troba el fitxer de config, retorna els valors per
	 * defecte
	 */
	@Test
	public void test0() {
		final ConfigLoader cl = new ConfigLoader("config-loader-test-0.properties");

		Assert.assertEquals(true, cl.configFileNotFound);
		Assert.assertEquals(false, cl.disableLogging());
		Assert.assertEquals(ConfigLoader.DEFAULT_LOG_LEVEL, cl.getLogLevel());
		Assert.assertEquals(true, cl.enableDateTime());
		Assert.assertEquals(ConfigLoader.DEFAULT_DATETIME_FORMAT, cl.getDateTimeFormat());
		Assert.assertEquals(1, cl.getHandlerList().size());
		Assert.assertEquals(ConfigLoader.DEFAULT_HANDLER, cl.getHandlerList().get(0));
	}

	/**
	 * verifica que, trobant el fitxer de config en blanc, retorna els valors
	 * per defecte
	 */
	@Test
	public void test1() {
		final ConfigLoader cl = new ConfigLoader("config-loader-test-1.properties");

		Assert.assertEquals(false, cl.configFileNotFound);
		Assert.assertEquals(false, cl.disableLogging());
		Assert.assertEquals(ConfigLoader.DEFAULT_LOG_LEVEL, cl.getLogLevel());
		Assert.assertEquals(true, cl.enableDateTime());
		Assert.assertEquals(ConfigLoader.DEFAULT_DATETIME_FORMAT, cl.getDateTimeFormat());
		Assert.assertEquals(1, cl.getHandlerList().size());
		Assert.assertEquals(ConfigLoader.DEFAULT_HANDLER, cl.getHandlerList().get(0));
	}

	/**
	 * verifica que retorna els valors que hi ha al fitxer de config
	 */
	@Test
	public void test2() {
		final ConfigLoader cl = new ConfigLoader("config-loader-test-2.properties");

		Assert.assertEquals(false, cl.configFileNotFound);
		Assert.assertEquals(false, cl.disableLogging());
		Assert.assertEquals(Logger.WARNING, cl.getLogLevel());
		Assert.assertEquals(true, cl.enableDateTime());
		Assert.assertEquals("dd/MM/yyyy G 'at' hh:mm:ss z", cl.getDateTimeFormat());
		Assert.assertEquals(2, cl.getHandlerList().size());
		Assert.assertEquals(ConsoleHandler.class, cl.getHandlerList().get(0).getClass());
		Assert.assertEquals(ListHandler.class, cl.getHandlerList().get(1).getClass());
	}

}
