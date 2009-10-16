package org.homs.gamba.container.benchmark;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.homs.gamba.container.context.GambaContainer;
import org.homs.gamba.container.context.GambaContext;
import org.homs.gamba.container.test.ents.C;
import org.junit.AfterClass;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class MainBenchMarkTest {

	private static final Log LOG = LogFactory.getLog(MainBenchMarkTest.class.getName());

	private static final int GET_BEAN_N_REPS = 10000;

	private static long springGetBeanTestTime;
	private static long gambaGetBeanTestTime;

	@AfterClass
	public static void showStatistics() {
		LOG.info("Spring getBean benckmark; elapsed time: " + springGetBeanTestTime + " ms.");
		LOG.info("Gamba getBean benckmark; elapsed time: " + gambaGetBeanTestTime + " ms.");
		LOG.info("Gamba is " + (springGetBeanTestTime / gambaGetBeanTestTime) + " times faster than Spring");
	}

	@Test
	public void springBenchMarkTest() {
		final ClassPathResource res = new ClassPathResource("benchmark-spring-context.xml");
		final XmlBeanFactory factory = new XmlBeanFactory(res);

		C c = null;
		final long t1 = System.currentTimeMillis();
		for (int i = 0; i < GET_BEAN_N_REPS; i++) {
			c = (C) factory.getBean("c");
		}
		springGetBeanTestTime = System.currentTimeMillis() - t1;

		Assert.assertEquals("m. homs", c.getA().getName());
		Assert.assertEquals(Integer.valueOf(27), c.getA().getAge());

		Assert.assertEquals("m. homs", c.getB().getA().getName());
		Assert.assertEquals(Integer.valueOf(27), c.getB().getA().getAge());

		Assert.assertEquals("m. santos", c.getName());
		Assert.assertEquals(Integer.valueOf(25), c.getAge());

		// singleton test
		Assert.assertTrue(c.getA() == c.getB().getA());
	}

	@Test
	public void gambaBenchMarkTest() {

		final GambaContext context = GambaContainer.getContext("benchmark-gamba-context.xml");

		C c = null;
		final long t1 = System.currentTimeMillis();
		for (int i = 0; i < GET_BEAN_N_REPS; i++) {
			c = (C) context.getBean("c");
		}
		gambaGetBeanTestTime = System.currentTimeMillis() - t1;

		Assert.assertEquals("m. homs", c.getA().getName());
		Assert.assertEquals(Integer.valueOf(27), c.getA().getAge());

		Assert.assertEquals("m. homs", c.getB().getA().getName());
		Assert.assertEquals(Integer.valueOf(27), c.getB().getA().getAge());

		Assert.assertEquals("m. santos", c.getName());
		Assert.assertEquals(Integer.valueOf(25), c.getAge());

		// singleton test
		Assert.assertTrue(c.getA() == c.getB().getA());
	}

}

