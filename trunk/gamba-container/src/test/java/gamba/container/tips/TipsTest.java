package gamba.container.tips;

import java.util.List;

import junit.framework.Assert;

import org.homs.gamba.container.context.GambaContainer;
import org.homs.gamba.container.context.GambaContext;
import org.junit.Test;

public class TipsTest {

	@SuppressWarnings("unchecked")
	@Test
	public void tip1() {
		final GambaContext gc = GambaContainer.getContext("tips-context.properties");
		final List<Integer> myList = (List<Integer>) gc.getBean("myList");

		Assert.assertTrue(myList instanceof List);

		myList.add(10);

		Assert.assertEquals(1, myList.size());
		Assert.assertEquals(Integer.valueOf(10), myList.get(0));
	}
}
