package org.homs.gamba.container.benchmark;

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

	private static final int GET_BEAN_N_REPS = 50000;

	private static long springGetBeanTestTime;
	private static long gambaGetBeanTestTime;
	private static long hardCodedGetBeanTestTime;

	@AfterClass
	public static void showStatistics() {
		LOG.info("Spring getBean benckmark; elapsed time: " + springGetBeanTestTime + " ms.");
		LOG.info("Gamba getBean benckmark; elapsed time: " + gambaGetBeanTestTime + " ms.");
//		LOG.info("hard coded getBean benckmark; elapsed time: " + hardCodedGetBeanTestTime + " ms.");
		LOG.info("Gamba is " + (springGetBeanTestTime / gambaGetBeanTestTime) + " times faster than Spring");
//		if (hardCodedGetBeanTestTime > 0) {
//			LOG.info("hard-coded is " + (gambaGetBeanTestTime / hardCodedGetBeanTestTime)
//					+ " times faster than Gamba");
//		}
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

//		Assert.assertEquals("hi", c.getA().getB().getMsg());
//		Assert.assertEquals("hi", c.getB().getMsg());

	}

	@Test
	public void gambaBenchMarkTest() {

		final GambaContext context = GambaContainer.getContext("benchmark-gamba-context.xml");

		System.out.println(GambaContainer.toStringStatic());


		C c = null;
		final long t1 = System.currentTimeMillis();
		for (int i = 0; i < GET_BEAN_N_REPS; i++) {
			c = (C) context.getBean("c");
		}
		gambaGetBeanTestTime = System.currentTimeMillis() - t1;

//		Assert.assertEquals("hi", c.getA().getB().getMsg());
//		Assert.assertEquals("hi", c.getB().getMsg());
	}

//	@Test
//	public void hardCodedBenchMarkTest() {
//
//		final HardCodedContext hcc = new HardCodedContext();
//
//		C c = null;
//		final long t1 = System.currentTimeMillis();
//		for (int i = 0; i < GET_BEAN_N_REPS; i++) {
//			c = hcc.buildC();
//		}
//		hardCodedGetBeanTestTime = System.currentTimeMillis() - t1;
//
////		Assert.assertEquals("hi", c.getA().getB().getMsg());
////		Assert.assertEquals("hi", c.getB().getMsg());
//	}

}

//class HardCodedContext {
//
//	private static B b = new B();
//
//	public B buildB() {
//		return b;
//	}
//
//	public A buildA() {
//		final A a = new A();
//		a.setB(buildB());
//		return a;
//	}
//
//	public C buildC() {
//		return new C(buildA(), buildB());
//	}
//}
