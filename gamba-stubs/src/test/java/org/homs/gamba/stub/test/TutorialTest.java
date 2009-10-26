package org.homs.gamba.stub.test;

import java.util.List;

import org.homs.gamba.stub.syntax.IStubber;
import org.homs.gamba.stub.syntax.Stubber;
import org.junit.Assert;
import org.junit.Test;

public class TutorialTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test1() {

		final IStubber<List> m = Stubber.createStub(List.class);
		m.doReturn("hi").when().get(0);
		final List myListStub = m.play();

		Assert.assertEquals("hi", myListStub.get(0));

		System.out.println(Stubber.obtainReport(myListStub));
	}

}
