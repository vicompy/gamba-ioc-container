package org.gamba.mocks.test;

import static org.gamba.mocks.fluent.Mocker.createMock;
import static org.gamba.mocks.fluent.Mocker.obtainCallingLog;
import static org.gamba.mocks.fluent.Mocker.replay;
import static org.gamba.mocks.fluent.Mocker.thenReturnList;
import static org.junit.Assert.assertEquals;

import org.gamba.mocks.ents.IAdder;
import org.gamba.mocks.fluent.Mocker;
import org.junit.Test;

public class ReplayTest {

	@Test
	public void test1() {

		final IAdder adderStub = createMock(IAdder.class);

		thenReturnList(1,2,3).when(adderStub).add(0, 2);

		replay(adderStub);
		final String startState1 = Mocker.obtainCallConfig(adderStub);

		assertEquals(Integer.valueOf(1), adderStub.add(0, 2));
		assertEquals(Integer.valueOf(2), adderStub.add(0, 2));
		assertEquals(Integer.valueOf(3), adderStub.add(0, 2));

		final String endState1 = Mocker.obtainCallConfig(adderStub);
		replay(adderStub);
		final String startState2 = Mocker.obtainCallConfig(adderStub);

		assertEquals(Integer.valueOf(1), adderStub.add(0, 2));
		assertEquals(Integer.valueOf(2), adderStub.add(0, 2));
		assertEquals(Integer.valueOf(3), adderStub.add(0, 2));

		final String endState2 = Mocker.obtainCallConfig(adderStub);

		System.out.println(
			obtainCallingLog(adderStub)
		);

		assertEquals(startState1, startState2);
		assertEquals(endState1, endState2);

	}

}
