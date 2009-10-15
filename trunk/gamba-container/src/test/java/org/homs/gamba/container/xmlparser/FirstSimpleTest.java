package org.homs.gamba.container.xmlparser;

import junit.framework.Assert;

import org.homs.gamba.container.context.GambaContext;
import org.homs.gamba.container.test.ents.B;
import org.homs.gamba.container.test.ents.C;
import org.homs.gamba.container.test.ents.IA;
import org.junit.Test;

public class FirstSimpleTest {

	@Test
	public void testA() {

		final IA a = (IA) new GambaContext("first-simple-context.xml").getBean("a");

		Assert.assertEquals("m. homs", a.getName());
		Assert.assertEquals(Integer.valueOf(27), a.getAge());
	}

	@Test
	public void testB() {

		final B b = (B) new GambaContext("first-simple-context.xml").getBean("b");

		Assert.assertEquals("m. homs", b.getA().getName());
		Assert.assertEquals(Integer.valueOf(27), b.getA().getAge());
	}

	@Test
	public void testC() {

		final C c = (C) new GambaContext("first-simple-context.xml").getBean("c");

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
