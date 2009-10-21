package org.homs.gamba.logging;

import junit.framework.Assert;

import org.junit.Test;

public class ConfigLoaderTest {

	/**
	 * verifica que, si no troba el fitxer de config, retorna els valors
	 * per defecte
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

}
