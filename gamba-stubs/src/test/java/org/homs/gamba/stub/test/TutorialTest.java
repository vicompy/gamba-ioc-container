package org.homs.gamba.stub.test;

import java.util.List;

import org.homs.gamba.stub.Stub;
import org.homs.gamba.stub.Stubber;
import org.junit.Assert;
import org.junit.Test;

public class TutorialTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test1() {

		final Stub<List> m = Stubber.createStub(List.class);
		m.returning(null).add("hi");
		m.returning("hi").get(0);
		final List myList = m.play();


		Assert.assertEquals("hi", myList.get(0));
	}

}
