package org.homs.gamba.stub.test;

import org.homs.gamba.stub.IDelegator;
import org.homs.gamba.stub.ents.IAdder;
import org.homs.gamba.stub.ents.IConcater;
import org.homs.gamba.stub.exception.GambaStubsException;
import org.homs.gamba.stub.syntax.IStubber;
import org.homs.gamba.stub.syntax.Stubber;
import org.junit.Assert;
import org.junit.Test;

public class StubTest {

	@Test
	public void test1() {

		final IStubber<IAdder> adderStubber = Stubber.createStub(IAdder.class);
		adderStubber.doReturn(3).when().add(1, 2);
		adderStubber.doReturn(5).when().add(2, 3);
		final IAdder adderStub = adderStubber.play();

		Assert.assertEquals(Integer.valueOf(3), adderStub.add(1, 2));
		Assert.assertEquals(Integer.valueOf(5), adderStub.add(2, 3));

		System.out.println(Stubber.obtainReport(adderStub));
	}

	@Test(expected = GambaStubsException.class)
	public void test2() {

		final IStubber<IAdder> m = Stubber.createStub(IAdder.class);
		m.doReturn(3).when().add(1, 2);
		m.doReturn(5).when().add(2, 3);
		final IAdder ia = m.play();

		ia.add(3, 4);
	}

	@Test(expected = GambaStubsException.class)
	public void test3() {

		final IStubber<IAdder> m = Stubber.createStub(IAdder.class);
		m.doReturn(3);
		final IAdder ia = m.play();

		Assert.assertEquals(Integer.valueOf(5), ia.add(2, 3));
	}

	@Test
	public void test4() {

		final IStubber<IConcater> m = Stubber.createStub(IConcater.class);
		m.doReturn("hello world").when().concat("hello ", "world");
		final IConcater ia = m.play();

		Assert.assertEquals("hello world", ia.concat("hello ", "world"));
		System.out.println(Stubber.obtainReport(ia));
	}

	@Test(expected = NullPointerException.class)
	public void test5() {

		final IStubber<IConcater> m = Stubber.createStub(IConcater.class);
		m.doThrow(new NullPointerException("jou")).when().concat("hello ", null);
		final IConcater ia = m.play();

		Assert.assertEquals("hello world", ia.concat("hello ", null));
	}

	@Test
	public void test6() {

		final IStubber<IConcater> m = Stubber.createStub(IConcater.class);
		m.doDelegate(new DelegatorConcat()).when().concat("hello ", "world");
		final IConcater ia = m.play();

		Assert.assertEquals("hello world", ia.concat("hello ", "world"));
		System.out.println(Stubber.obtainReport(ia));
	}

}

class DelegatorConcat implements IDelegator {

	public Object delegates(final Object... os) {
		return os[0].toString() + os[1].toString();
	}

}
