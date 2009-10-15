package gamba.container.tips;

import gamba.container.test.entities.C;

import java.util.List;

import junit.framework.Assert;

import org.homs.gamba.container.context.GambaContainer;
import org.homs.gamba.container.context.GambaContext;
import org.junit.Test;

public class TipsTest {

	private final GambaContext gc;

	public TipsTest() {
		gc = GambaContainer.getContext("tips-context.properties");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void tip1() {
		// final GambaContext gc =
		// GambaContainer.getContext("tips-context.properties");
		final List<Integer> myList = (List<Integer>) gc.getBean("myList");

		Assert.assertTrue(myList instanceof List);

		myList.add(10);

		Assert.assertEquals(1, myList.size());
		Assert.assertEquals(Integer.valueOf(10), myList.get(0));
	}

	@Test
	public void tip2() {

		final C c1 = (C) gc.getBean("c1");
		final C c2 = (C) gc.getBean("c2");
		final C c3 = (C) gc.getBean("c3");
		final C c4 = (C) gc.getBean("c4");

		Assert.assertTrue(c1 instanceof C);
		Assert.assertTrue(c2 instanceof C);
		Assert.assertTrue(c3 instanceof C);
		Assert.assertTrue(c4 instanceof C);

		Assert.assertEquals("i'm b1", c1.getA().getB().getMsg());
		Assert.assertEquals("i'm b1", c1.getB().getMsg());
		Assert.assertTrue(c1.getA().getB().getMsg() == c1.getB().getMsg());

		Assert.assertEquals("i'm b2", c2.getA().getB().getMsg());
		Assert.assertEquals("i'm b2", c2.getB().getMsg());
		Assert.assertTrue(c2.getA().getB().getMsg() == c2.getB().getMsg());

		Assert.assertEquals("i'm b3", c3.getA().getB().getMsg());
		Assert.assertEquals("i'm b3", c3.getB().getMsg());

		Assert.assertEquals("i'm b4", c4.getA().getB().getMsg());
		Assert.assertEquals("i'm b4", c4.getB().getMsg());

	}

}
