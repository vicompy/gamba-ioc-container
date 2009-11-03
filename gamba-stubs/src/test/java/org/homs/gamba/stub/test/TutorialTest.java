package org.homs.gamba.stub.test;

import static org.homs.gamba.stub.bsyntax.Stubber.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TutorialTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test1() {

		final List l = (List) createStub(List.class);
		thenReturn(5).when(l).get(0);
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

		final List l = (List) createStub(List.class);
		thenLoop(0, 1).when(l).get(0);
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

		final List l = (List) createStub(List.class);
		thenReturn(0, 1).when(l).get(0);
		play(l);



		// TODO fer comodins:
		// willReturn(0, 1).when(l).get(All(Integer.class));

		Assert.assertEquals(0, l.get(0));
		Assert.assertEquals(1, l.get(0));
		Assert.assertEquals(1, l.get(0));
		Assert.assertEquals(1, l.get(0));

		System.out.println(obtainReport(l));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test4() {

		final List l = (List) createStub(List.class);
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

		System.out.println(obtainReport(l));
	}

}