package org.homs.gamba.container.benchmark;

import junit.framework.Assert;

import org.homs.gamba.container.context.GambaContainer;
import org.homs.gamba.container.context.GambaContext;
import org.homs.gamba.container.test.ents.C;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * 15-34 vegades més ràpid que Spring, de mitjana 20.
 *
 * @author mhoms
 */
public class MainBenchMarkTest {

	private static final int GET_BEAN_N_REPS = 30000;

	private static long springGetBeanTestTime = 0;
	private static long gambaGetBeanTestTime = 0;

	public static void main(final String[] args) {
		final MainBenchMarkTest m = new MainBenchMarkTest();
		for (int i = 0; i < 10; i++) {
			System.gc();
			m.springBenchMarkTest();
			System.gc();
			m.gambaBenchMarkTest();
		}

		System.out.println("mitjana ==> " + (springGetBeanTestTime / gambaGetBeanTestTime) + " times faster than Spring");
		System.out.println("Spring getBean benckmark; elapsed time: " + springGetBeanTestTime + " ms.");
		System.out.println("Gamba getBean benckmark; elapsed time: " + gambaGetBeanTestTime + " ms.");
	}

	@Test
	public void benchmarkTest() {

		springBenchMarkTest();
		gambaBenchMarkTest();

		System.out.println((springGetBeanTestTime / gambaGetBeanTestTime) + " times faster than Spring");
		System.out.println("Spring getBean benckmark; elapsed time: " + springGetBeanTestTime + " ms.");
		System.out.println("Gamba getBean benckmark; elapsed time: " + gambaGetBeanTestTime + " ms.");
	}

	public void springBenchMarkTest() {

		final ClassPathResource res = new ClassPathResource("benchmark-spring-context.xml");
		final XmlBeanFactory factory = new XmlBeanFactory(res);

		C c = null;
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < GET_BEAN_N_REPS; i++) {
			c = (C) factory.getBean("c");
		}
		t1 = System.currentTimeMillis() - t1;
		springGetBeanTestTime += t1;

		Assert.assertEquals("m. homs", c.getA().getName());
		Assert.assertEquals(Integer.valueOf(27), c.getA().getAge());

		Assert.assertEquals("m. homs", c.getB().getA().getName());
		Assert.assertEquals(Integer.valueOf(27), c.getB().getA().getAge());

		Assert.assertEquals("m. santos", c.getName());
		Assert.assertEquals(Integer.valueOf(25), c.getAge());

		// singleton test
		Assert.assertTrue(c.getA() == c.getB().getA());
	}

	public void gambaBenchMarkTest() {

		final GambaContext context = GambaContainer.getContext("benchmark-gamba-context.xml");

		C c = null;
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < GET_BEAN_N_REPS; i++) {
			c = (C) context.getBean("c");
		}
		t1 = System.currentTimeMillis() - t1;
		gambaGetBeanTestTime += t1;

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
