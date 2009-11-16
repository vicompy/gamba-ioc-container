package org.gamba.mocks.test;

import static org.gamba.mocks.fluent.Mocker.createMock;
import static org.gamba.mocks.fluent.Mocker.obtainCallingLog;
import static org.gamba.mocks.fluent.Mocker.replay;
import static org.gamba.mocks.fluent.Mocker.thenDelegate;
import static org.gamba.mocks.fluent.Mocker.thenReturn;
import static org.gamba.mocks.fluent.Mocker.thenThrow;
import static org.junit.Assert.assertEquals;

import org.gamba.mocks.ents.IAdder;
import org.gamba.mocks.ents.IConcater;
import org.gamba.mocks.exception.GambaMockException;
import org.gamba.mocks.sequences.ISequence;
import org.junit.Test;

public class StubTest {

	@Test
	public void test1() {

		final IAdder adderStub = createMock(IAdder.class);
		thenReturn(3).when(adderStub).add(1, 2);
		thenReturn(5).when(adderStub).add(2, 3);

		replay(adderStub);
		System.out.println(obtainCallingLog(adderStub));

		assertEquals(Integer.valueOf(3), adderStub.add(1, 2));
		assertEquals(Integer.valueOf(5), adderStub.add(2, 3));
	}

	// es fa una crida no registrada
	@Test(expected = GambaMockException.class)
	public void test2() {

		final IAdder adderStub = createMock(IAdder.class);
		thenReturn(3).when(adderStub).add(1, 2);
		thenReturn(5).when(adderStub).add(2, 3);
		replay(adderStub);
		adderStub.add(3, 4);
	}

	// en la definició falta la crida al mètode a simular
	@Test(expected = GambaMockException.class)
	public void test3() {

		final IAdder adderStub = createMock(IAdder.class);
		thenReturn(3).when(adderStub); // .add(1, 2);
		replay(adderStub);
		adderStub.add(1, 2);
	}

	@Test
	public void test4() {

		final IConcater c = createMock(IConcater.class);
		thenReturn("hello world").when(c).concat("hello ", "world");
		replay(c);

		assertEquals("hello world", c.concat("hello ", "world"));
		System.out.println(obtainCallingLog(c));
	}

	@Test(expected = NullPointerException.class)
	public void test5() {

		final IConcater c = createMock(IConcater.class);
		thenThrow(new NullPointerException("jou")).when(c).concat("hello ", null);
		replay(c);

		assertEquals("hello world", c.concat("hello ", null));
	}

	// // TODO és un problema interceptar els mètodes propis de Object!!
	// (toString, getClass...) ????
	// @Test//(expected = NullPointerException.class)
	// public void test6() {
	//
	// final IConcater c = (IConcater) createStub(IConcater.class);
	// thenThrow(new NullPointerException("jou")).when(c).getClass(); // Claro,
	// retorna la Class del proxy!!
	// play(c);
	//
	// // assertEquals("hello world", c.concat("hello ", null));
	// System.out.println(c.getClass());
	// }

	@Test(expected = NullPointerException.class)
	public void test7() {

		final IConcater c = createMock(IConcater.class);
		thenThrow(new NullPointerException("jou")).when(c).getMessage();
		replay(c);

		System.out.println(c.getMessage());
	}

	@Test
	public void test8() {

		final IConcater c = createMock(IConcater.class);
		thenReturn("jou").when(c).getMessage();
		replay(c);

		assertEquals("jou", c.getMessage());
	}

	@Test
	public void test9() {

		final IConcater c = createMock(IConcater.class);
		thenDelegate(new DelegatorConcat()).when(c).concat("hello ", "world");
		replay(c);

		assertEquals("hello world", c.concat("hello ", "world"));
		System.out.println(obtainCallingLog(c));
	}

}

class DelegatorConcat implements ISequence {

	public Object getNext(final Object... os) {
		return os[0].toString() + os[1].toString();
	}

	public void reset() {
	}

	public boolean testIsFinished() {
		return true;
	}

}
