package org.homs.gamba.stub.test;

import static org.homs.gamba.stub.bsyntax.Stubber.*;

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
		thenReturn(3).when(adderStub).add(1, 2);
		thenReturn(5).when(adderStub).add(2, 3);


		// TODO i la sintaxi: when(adderStub.add(1, 2)).thenReturn(3); ... és insegura?



		play(adderStub);
		System.out.println(obtainReport(adderStub));

		Assert.assertEquals(Integer.valueOf(3), adderStub.add(1, 2));
		Assert.assertEquals(Integer.valueOf(5), adderStub.add(2, 3));
	}

	// es fa una crida no registrada
	@Test(expected = GambaStubsException.class)
	public void test2() {

		final IAdder adderStub = (IAdder) createStub(IAdder.class);
		thenReturn(3).when(adderStub).add(1, 2);
		thenReturn(5).when(adderStub).add(2, 3);
		play(adderStub);
		adderStub.add(3, 4);
	}

	// en la definició falta la crida al mètode a simular
	@Test(expected = GambaStubsException.class)
	public void test3() {

		final IAdder adderStub = (IAdder) createStub(IAdder.class);
		thenReturn(3).when(adderStub); //.add(1, 2);
		play(adderStub);
		adderStub.add(1, 2);
	}

	@Test
	public void test4() {

		final IConcater c = (IConcater) createStub(IConcater.class);
		thenReturn("hello world").when(c).concat("hello ", "world");
		play(c);

		Assert.assertEquals("hello world", c.concat("hello ", "world"));
		System.out.println(obtainReport(c));
	}

	@Test(expected = NullPointerException.class)
	public void test5() {

		final IConcater c = (IConcater) createStub(IConcater.class);
		thenThrow(new NullPointerException("jou")).when(c).concat("hello ", null);
		play(c);

		Assert.assertEquals("hello world", c.concat("hello ", null));
	}

//	// TODO és un problema interceptar els mètodes propis de Object!! (toString, getClass...) ????
//	@Test//(expected = NullPointerException.class)
//	public void test6() {
//
//		final IConcater c = (IConcater) createStub(IConcater.class);
//		thenThrow(new NullPointerException("jou")).when(c).getClass();  // Claro, retorna la Class del proxy!!
//		play(c);
//
////		Assert.assertEquals("hello world", c.concat("hello ", null));
//		System.out.println(c.getClass());
//	}

	@Test(expected = NullPointerException.class)
	public void test7() {

		final IConcater c = (IConcater) createStub(IConcater.class);
		thenThrow(new NullPointerException("jou")).when(c).getMessage();
		play(c);

		System.out.println(c.getMessage());
	}

	@Test
	public void test8() {

		final IConcater c = (IConcater) createStub(IConcater.class);
		thenReturn("jou").when(c).getMessage();
		play(c);

		Assert.assertEquals("jou", c.getMessage());
	}

	@Test
	public void test9() {

		final IConcater c = (IConcater) createStub(IConcater.class);
		thenDelegate(new DelegatorConcat()).when(c).concat("hello ", "world");
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
