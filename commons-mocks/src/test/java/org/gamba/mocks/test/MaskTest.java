package org.gamba.mocks.test;

import static org.gamba.mocks.fluent.Mocker.createMock;
import static org.gamba.mocks.fluent.Mocker.maskBy;
import static org.gamba.mocks.fluent.Mocker.obtainCallConfig;
import static org.gamba.mocks.fluent.Mocker.obtainCallingLog;
import static org.gamba.mocks.fluent.Mocker.replay;
import static org.gamba.mocks.fluent.Mocker.thenReturnList;
import static org.gamba.mocks.fluent.Mocker.verify;

import org.gamba.mocks.ents.IAdder;
import org.junit.Test;

import static org.junit.Assert.*;

public class MaskTest {

	@Test
	public void test1() {

		final IAdder adderStub = createMock(IAdder.class);

		thenReturnList(1, 2, 3).when(adderStub, maskBy("*-")).add(0, 2);
		thenReturnList(4, 5, 6).when(adderStub, maskBy("-*")).add(2, 0);

		replay(adderStub);

		assertEquals(Integer.valueOf(1), adderStub.add(7, 2));
		assertEquals(Integer.valueOf(2), adderStub.add(8, 2));
		assertEquals(Integer.valueOf(3), adderStub.add(9, 2));

		assertEquals(Integer.valueOf(4), adderStub.add(2, 7));
		assertEquals(Integer.valueOf(5), adderStub.add(2, 8));
		assertEquals(Integer.valueOf(6), adderStub.add(2, 9));

		System.out.println(obtainCallConfig(adderStub));
		System.out.println(obtainCallingLog(adderStub));

		assertEquals(6, obtainCallingLog(adderStub).countGroupingBy("add"));
		assertEquals(1, obtainCallingLog(adderStub).countGroupingBy("add", 2, 9));
		assertEquals(3, obtainCallingLog(adderStub).countGroupingBy(maskBy("-*"), "add", 2, 9));

		verify(adderStub);
	}

	@Test
	public void test2() {
		final IAdder adderStub = createMock(IAdder.class);

		// si length(màscara) != length(args), no dóna error, ja que l'array de
		// màscara s'ajusta a la
		// llargada dels arguments, truncant, ó empalmant falses!! que no peta!
		thenReturnList(1, 2, 3).when(adderStub, maskBy("*-*-*-")).add(0, 2);
		thenReturnList(4, 5, 6).when(adderStub, maskBy("")).add(2, 0);
	}

}
