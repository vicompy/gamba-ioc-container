package org.homs.gamba.stub.test;

import static org.homs.gamba.stub.bsyntax.Stubber.createStub;
import static org.homs.gamba.stub.bsyntax.Stubber.obtainReport;
import static org.homs.gamba.stub.bsyntax.Stubber.play;
import static org.homs.gamba.stub.bsyntax.Stubber.willLoop;
import static org.homs.gamba.stub.bsyntax.Stubber.willPingPongLoop;
import static org.homs.gamba.stub.bsyntax.Stubber.willReturn;
import static org.homs.gamba.stub.bsyntax.Stubber.willSinglePass;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TutorialTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test1() {

		// final IStubber<List> m = Stubber.createStub(List.class);
		// m.doReturn(5).when().get(0);
		// final List myListStub = m.play();

		final List l = (List) createStub(List.class);
		willReturn(5).when(l).get(0);
		play(l);

		Assert.assertEquals(5, l.get(0));
		Assert.assertEquals(5, l.get(0));
		Assert.assertEquals(5, l.get(0));
		Assert.assertEquals(5, l.get(0));

		System.out.println(obtainReport(l));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test2() {

		// final IStubber<List> m = Stubber.createStub(List.class);
		// m.doLoop(0, 1).when().get(0);
		// final List myListStub = m.play();

		final List l = (List) createStub(List.class);
		willLoop(0, 1).when(l).get(0);
		play(l);

		Assert.assertEquals(0, l.get(0));
		Assert.assertEquals(1, l.get(0));
		Assert.assertEquals(0, l.get(0));
		Assert.assertEquals(1, l.get(0));

		System.out.println(obtainReport(l));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test3() {

//		final IStubber<List> m = Stubber.createStub(List.class);
//		m.doSinglePass(0, 1).when().get(0);
//		final List myListStub = m.play();

		final List l = (List) createStub(List.class);
		willSinglePass(0, 1).when(l).get(0);
		play(l);

		Assert.assertEquals(0, l.get(0));
		Assert.assertEquals(1, l.get(0));
		Assert.assertEquals(1, l.get(0));
		Assert.assertEquals(1, l.get(0));

		System.out.println(obtainReport(l));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test4() {

//		final IStubber<List> m = Stubber.createStub(List.class);
//		m.doPingPongLoop(0, 1, 2).when().get(0);
//		final List myListStub = m.play();

		final List l = (List) createStub(List.class);
		willPingPongLoop(0, 1, 2).when(l).get(0);
		play(l);


		Assert.assertEquals(0, l.get(0));
		Assert.assertEquals(1, l.get(0));
		Assert.assertEquals(2, l.get(0));
		Assert.assertEquals(1, l.get(0));
		Assert.assertEquals(0, l.get(0));
		Assert.assertEquals(1, l.get(0));
		Assert.assertEquals(2, l.get(0));
		Assert.assertEquals(1, l.get(0));
		Assert.assertEquals(0, l.get(0));

		System.out.println(obtainReport(l));
	}

}
