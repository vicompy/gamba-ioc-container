package org.homs.gamba.stub.test;

import static org.homs.gamba.stub.bsyntax.Stubber.createStub;
import static org.homs.gamba.stub.bsyntax.Stubber.obtainReport;
import static org.homs.gamba.stub.bsyntax.Stubber.play;
import static org.homs.gamba.stub.bsyntax.Stubber.willDelegate;
import static org.homs.gamba.stub.bsyntax.Stubber.willReturn;
import static org.homs.gamba.stub.bsyntax.Stubber.willThrow;

import org.homs.gamba.stub.delegator.IDelegator;
import org.homs.gamba.stub.ents.IAdder;
import org.homs.gamba.stub.ents.IConcater;
import org.homs.gamba.stub.exception.GambaStubsException;
import org.junit.Assert;
import org.junit.Test;


public class StubTest {

	@Test
	public void test1() {
		final IAdder adderStub = (IAdder) createStub(IAdder.class);
		willReturn(3).when(adderStub).add(1, 2);
		willReturn(5).when(adderStub).add(2, 3);
		play(adderStub);
		System.out.println(obtainReport(adderStub));

//		final IStubber<IAdder> adderStubber = Stubber.createStub(IAdder.class);
//		adderStubber.doReturn(3).when().add(1, 2);
//		adderStubber.doReturn(5).when().add(2, 3);
//		final IAdder adderStub = adderStubber.play();
//		System.out.println(Stubber.obtainReport(adderStub));
//
		Assert.assertEquals(Integer.valueOf(3), adderStub.add(1, 2));
		Assert.assertEquals(Integer.valueOf(5), adderStub.add(2, 3));


	}

	@Test(expected = GambaStubsException.class)
	public void test2() {

//		final IStubber<IAdder> m = Stubber.createStub(IAdder.class);
//		m.doReturn(3).when().add(1, 2);
//		m.doReturn(5).when().add(2, 3);
//		final IAdder ia = m.play();
//		ia.add(3, 4);

		final IAdder adderStub = (IAdder) createStub(IAdder.class);
		willReturn(3).when(adderStub).add(1, 2);
		willReturn(5).when(adderStub).add(2, 3);
		play(adderStub);
		adderStub.add(3, 4);
	}

//	@Test(expected = GambaStubsException.class)
	public void test3() {

//		final IStubber<IAdder> m = Stubber.createStub(IAdder.class);
//		m.doReturn(3);
//		final IAdder ia = m.play();

		final IAdder adderStub = (IAdder) createStub(IAdder.class);
		willReturn(3).when(adderStub);
		play(adderStub);

		Assert.assertEquals(Integer.valueOf(5), adderStub.add(2, 3));
	}
//	@Test(expected = GambaStubsException.class)
	public void test32() {

//		final IStubber<IAdder> m = Stubber.createStub(IAdder.class);
//		m.doReturn(3);
//		final IAdder ia = m.play();

		final IAdder adderStub = (IAdder) createStub(IAdder.class);
		willReturn(3);
		play(adderStub);

		Assert.assertEquals(Integer.valueOf(5), adderStub.add(2, 3));
	}

	@Test
	public void test4() {

//		final IStubber<IConcater> m = Stubber.createStub(IConcater.class);
//		m.doReturn("hello world").when().concat("hello ", "world");
//		final IConcater ia = m.play();

		final IConcater c = (IConcater) createStub(IConcater.class);
		willReturn("hello world").when(c).concat("hello ", "world");
		play(c);

		Assert.assertEquals("hello world", c.concat("hello ", "world"));
		System.out.println(obtainReport(c));
	}

	@Test(expected = NullPointerException.class)
	public void test5() {

//		final IStubber<IConcater> m = Stubber.createStub(IConcater.class);
//		m.doThrow(new NullPointerException("jou")).when().concat("hello ", null);
//		final IConcater ia = m.play();

		final IConcater c = (IConcater) createStub(IConcater.class);
		willThrow(new NullPointerException("jou")).when(c).concat("hello ", null);
		play(c);

		Assert.assertEquals("hello world", c.concat("hello ", null));
	}

	@Test
	public void test6() {

//		final IStubber<IConcater> m = Stubber.createStub(IConcater.class);
//		m.doDelegate(new DelegatorConcat()).when().concat("hello ", "world");
//		final IConcater ia = m.play();

		final IConcater c = (IConcater) createStub(IConcater.class);
		willDelegate(new DelegatorConcat()).when(c).concat("hello ", "world");
		play(c);

		Assert.assertEquals("hello world", c.concat("hello ", "world"));
		System.out.println(obtainReport(c));
	}

}

class DelegatorConcat implements IDelegator {

	public Object delegates(final Object... os) {
		return os[0].toString() + os[1].toString();
	}

}
