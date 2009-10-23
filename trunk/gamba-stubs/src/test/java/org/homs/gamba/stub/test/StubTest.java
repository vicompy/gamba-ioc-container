package org.homs.gamba.stub.test;

import org.homs.gamba.stub.IDelegator;
import org.homs.gamba.stub.Stub;
import org.homs.gamba.stub.Stubber;
import org.homs.gamba.stub.ents.IAdder;
import org.homs.gamba.stub.ents.IConcater;
import org.homs.gamba.stub.exception.GambaStubsException;
import org.junit.Assert;
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

	@Test
	public void test4() {

		final Stub<IConcater> m = Stubber.createStub(IConcater.class);
		m.returning("hello world").concat("hello ", "world");
		final IConcater ia = m.play();

		Assert.assertEquals("hello world", ia.concat("hello ", "world"));
	}

	@Test(expected = NullPointerException.class)
	public void test5() {

		final Stub<IConcater> m = Stubber.createStub(IConcater.class);
		m.throwing(new NullPointerException("jou")).concat("hello ", null);
		final IConcater ia = m.play();

		Assert.assertEquals("hello world", ia.concat("hello ", null));
	}

	@Test
	public void test6() {

		final Stub<IConcater> m = Stubber.createStub(IConcater.class);
		m.delegates(new DelegatorConcat()).concat("hello ", "world");
		final IConcater ia = m.play();

		Assert.assertEquals("hello world", ia.concat("hello ", "world"));
	}

}

class DelegatorConcat implements IDelegator {

	public Object delegates(final Object... os) {
		return os[0].toString() + os[1].toString();
	}

}
