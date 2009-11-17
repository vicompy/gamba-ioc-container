package org.gamba.sm4j.test;



import org.gamba.sm4j.ents.IAdder;
import org.junit.Test;

import static org.gamba.sm4j.Mocky.*;
import static junit.framework.Assert.*;

public class MaskTest {

	@Test
	public void test1() {

		final IAdder adderStub = createMock(IAdder.class);

		thenReturnSeq(1, 2, 3).when(adderStub).add(anyInt(), eq(2));
		thenReturnSeq(4, 5, 6).when(adderStub).add(eq(2), anyInt());

		replay(adderStub);

		assertEquals(Integer.valueOf(1), adderStub.add(7, 2));
		assertEquals(Integer.valueOf(2), adderStub.add(8, 2));
		assertEquals(Integer.valueOf(3), adderStub.add(9, 2));

		assertEquals(Integer.valueOf(4), adderStub.add(2, 7));
		assertEquals(Integer.valueOf(5), adderStub.add(2, 8));
		assertEquals(Integer.valueOf(6), adderStub.add(2, 9));

		verify(adderStub);
	}

 }
