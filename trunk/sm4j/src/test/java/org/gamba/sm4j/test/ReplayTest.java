package org.gamba.sm4j.test;
//package org.gamba.mocks.test;
//
//import static org.junit.Assert.assertEquals;
//
//import org.gamba.mocks.ents.IAdder;
//import org.junit.Test;
//import static org.gamba.mocks.Mocky.*;
//import static junit.framework.Assert.*;
//
//public class ReplayTest {
//
//	@Test
//	public void test1() {
//
//		final IAdder adderStub = createMock(IAdder.class);
//
//		thenReturnSeq(1,2,3).when(adderStub).add(0, 2);
//
//		replay(adderStub);
//		final String startState1 = obtainCallConfig(adderStub);
//
//		assertEquals(Integer.valueOf(1), adderStub.add(0, 2));
//		assertEquals(Integer.valueOf(2), adderStub.add(0, 2));
//		assertEquals(Integer.valueOf(3), adderStub.add(0, 2));
//
//		final String endState1 = obtainCallConfig(adderStub);
//		replay(adderStub);
//		final String startState2 = obtainCallConfig(adderStub);
//
//		assertEquals(Integer.valueOf(1), adderStub.add(0, 2));
//		assertEquals(Integer.valueOf(2), adderStub.add(0, 2));
//		assertEquals(Integer.valueOf(3), adderStub.add(0, 2));
//
//		final String endState2 = obtainCallConfig(adderStub);
//
//		System.out.println(
//			obtainCallingLog(adderStub)
//		);
//
//		assertEquals(startState1, startState2);
//		assertEquals(endState1, endState2);
//
//	}
//
// }
