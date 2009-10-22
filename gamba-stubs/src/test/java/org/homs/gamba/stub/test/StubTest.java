package org.homs.gamba.stub.test;

import junit.framework.Assert;

import org.homs.gamba.stub.Stub;
import org.homs.gamba.stub.Stubber;
import org.homs.gamba.stub.ents.IAdder;
import org.homs.gamba.stub.exception.GambaStubsException;
import org.junit.Test;

public class StubTest {

	@Test
	public void test1() {

		final Stub<IAdder> m = Stubber.createStub(IAdder.class);
		m.returning(3).add(1, 2);
		m.returning(5).add(2, 3);
		final IAdder ia = m.play();

		Assert.assertEquals(Integer.valueOf(3), ia.add(1, 2));
		Assert.assertEquals(Integer.valueOf(5), ia.add(2, 3));
	}

	@Test(expected = GambaStubsException.class)
	public void test2() {

		final Stub<IAdder> m = Stubber.createStub(IAdder.class);
		m.returning(3).add(1, 2);
		m.returning(5).add(2, 3);
		final IAdder ia = m.play();

		ia.add(3, 4);
	}

	@Test(expected = GambaStubsException.class)
	public void test3() {

		final Stub<IAdder> m = Stubber.createStub(IAdder.class);
		m.returning(3);
		final IAdder ia = m.play();

		Assert.assertEquals(Integer.valueOf(5), ia.add(2, 3));
	}


}
