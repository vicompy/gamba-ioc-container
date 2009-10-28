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
		m.doReturn(5).when().get(0);
		final List myListStub = m.play();

		Assert.assertEquals(5, myListStub.get(0));
		Assert.assertEquals(5, myListStub.get(0));
		Assert.assertEquals(5, myListStub.get(0));
		Assert.assertEquals(5, myListStub.get(0));

		System.out.println(Stubber.obtainReport(myListStub));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test2() {

		final IStubber<List> m = Stubber.createStub(List.class);
		m.doLoop(0, 1).when().get(0);
		final List myListStub = m.play();

		Assert.assertEquals(0, myListStub.get(0));
		Assert.assertEquals(1, myListStub.get(0));
		Assert.assertEquals(0, myListStub.get(0));
		Assert.assertEquals(1, myListStub.get(0));

		System.out.println(Stubber.obtainReport(myListStub));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test3() {

		final IStubber<List> m = Stubber.createStub(List.class);
		m.doSinglePass(0, 1).when().get(0);
		final List myListStub = m.play();

		Assert.assertEquals(0, myListStub.get(0));
		Assert.assertEquals(1, myListStub.get(0));
		Assert.assertEquals(1, myListStub.get(0));
		Assert.assertEquals(1, myListStub.get(0));

		System.out.println(Stubber.obtainReport(myListStub));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test4() {

		final IStubber<List> m = Stubber.createStub(List.class);
		m.doPingPongLoop(0, 1, 2).when().get(0);
		final List myListStub = m.play();

		Assert.assertEquals(0, myListStub.get(0));
		Assert.assertEquals(1, myListStub.get(0));
		Assert.assertEquals(2, myListStub.get(0));
		Assert.assertEquals(1, myListStub.get(0));
		Assert.assertEquals(0, myListStub.get(0));
		Assert.assertEquals(1, myListStub.get(0));
		Assert.assertEquals(2, myListStub.get(0));
		Assert.assertEquals(1, myListStub.get(0));
		Assert.assertEquals(0, myListStub.get(0));

		System.out.println(Stubber.obtainReport(myListStub));
	}

}


