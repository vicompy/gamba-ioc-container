package org.homs.gamba.container.test.tips;

import java.util.List;

import junit.framework.Assert;

import org.homs.gamba.container.context.GambaContainer;
import org.homs.gamba.container.context.GambaContext;
import org.homs.gamba.container.test.ents.A;
import org.homs.gamba.container.test.ents.B;
import org.junit.Test;

public class TipsTest {


	@SuppressWarnings("unchecked")
	@Test
	public void Tip1Test() {

		final GambaContext context = GambaContainer.getContext("tips-context.xml");
		final List<Integer> myList = (List<Integer>) context.getBean("myList");

		myList.add(Integer.valueOf(100));

		Assert.assertTrue(myList instanceof List);
		Assert.assertEquals(Integer.valueOf(100), myList.get(0));
	}

	@Test
	public void Tip2Test() {

		final GambaContext context = GambaContainer.getContext("tips-context.xml");
		final B b = (B) context.getBean("b-setter");

		Assert.assertTrue(b instanceof B);
		Assert.assertTrue(b.getA() instanceof A);
	}

	@Test
	public void Tip3Test() {

		final GambaContext context = GambaContainer.getContext("tips-context.xml");
		final B b = (B) context.getBean("b-constr");

		Assert.assertTrue(b instanceof B);
		Assert.assertTrue(b.getA() instanceof A);
	}

}

