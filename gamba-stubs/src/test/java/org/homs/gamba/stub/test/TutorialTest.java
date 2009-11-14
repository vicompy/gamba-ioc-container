package org.homs.gamba.stub.test;

import static org.homs.gamba.stub.bsyntax.Stubber.*;

import java.util.List;

import org.homs.gamba.stub.exception.GambaStubsException;
import org.junit.Assert;
import org.junit.Test;

public class TutorialTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test1() {

		final List l = createStub(List.class);
		thenReturn(5).when(l).get(0);
		play(l);

		Assert.assertEquals(5, l.get(0));
		Assert.assertEquals(5, l.get(0));
		Assert.assertEquals(5, l.get(0));
		Assert.assertEquals(5, l.get(0));

		System.out.println(obtainCallingLog(l));
	}

	@SuppressWarnings("unchecked")
	@Test(expected=GambaStubsException.class) // degut a final de seqüència
	public void test0() {

		final List l = createStub(List.class);
		thenReturn(1,2,3).when(l).get(0);
		play(l);

		Assert.assertEquals(1, l.get(0));
		Assert.assertEquals(2, l.get(0));
		Assert.assertEquals(3, l.get(0));
		Assert.assertEquals(4, l.get(0));

		System.out.println(obtainCallingLog(l));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test2() {

		final List l = createStub(List.class);
		thenLoop(0, 1).when(l).get(0);
		play(l);

		Assert.assertEquals(0, l.get(0));
		Assert.assertEquals(1, l.get(0));
		Assert.assertEquals(0, l.get(0));
		Assert.assertEquals(1, l.get(0));

		System.out.println(obtainCallingLog(l));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test3() {

		final List l = createStub(List.class);
		thenUncheckedReturn(0, 1).when(l).get(0);
		play(l);

		Assert.assertEquals(0, l.get(0));
		Assert.assertEquals(1, l.get(0));
		Assert.assertEquals(1, l.get(0));
		Assert.assertEquals(1, l.get(0));

		System.out.println(obtainCallingLog(l));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test4() {

		final List l = createStub(List.class);
		thenPingPongLoop(0, 1, 2).when(l).get(0);
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

		System.out.println(obtainCallingLog(l));
	}

}
