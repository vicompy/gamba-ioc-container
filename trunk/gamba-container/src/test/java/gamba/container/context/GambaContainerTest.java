package gamba.container.context;

import gamba.container.test.entities.A;
import gamba.container.test.entities.B;
import gamba.container.test.entities.C;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

public class GambaContainerTest {

	private static GambaContext gc = null;

	@BeforeClass
	public static void init() {
		gc = GambaContainer.getContext("PropertiesParser-test-context.properties");
//		System.out.println(gc.toString());
	}

	@Test
	public void test1() {

		final A a = (A) gc.getBean("a");

		Assert.assertTrue(a instanceof A);
		Assert.assertTrue(a.getB() instanceof B);
		Assert.assertTrue(a.getB().getMsg() instanceof String);

		Assert.assertEquals("jou", a.getB().getMsg());
	}

	@Test
	public void test2() {

		final A a = (A) gc.getBean("ba");

		Assert.assertTrue(a instanceof A);
		Assert.assertTrue(a.getB() instanceof B);
		Assert.assertTrue(a.getB().getMsg() instanceof String);

		Assert.assertEquals("jou", a.getB().getMsg());
	}

	@Test
	public void test3() {

		final C c = (C) gc.getBean("c");

		Assert.assertEquals("jou", c.getA().getB().getMsg());
		Assert.assertEquals("jou", c.getB().getMsg());

		Assert.assertTrue(c.getA().getB() == c.getB());
		Assert.assertEquals(c.getA().getB(), c.getB());
	}

	@Test
	public void test4() {

		final C c = (C) gc.getBean("cab");

		Assert.assertEquals("jou", c.getA().getB().getMsg());
		Assert.assertEquals("jou", c.getB().getMsg());

		Assert.assertTrue(c.getA().getB() == c.getB());
		Assert.assertEquals(c.getA().getB(), c.getB());
	}

}
