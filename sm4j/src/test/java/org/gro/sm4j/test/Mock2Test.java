package org.gro.sm4j.test;

import static junit.framework.Assert.*;
import static org.gro.sm4j.Mocky.*;

import org.gro.sm4j.exception.SimpleMockingException;
import org.junit.Test;

public class Mock2Test {

	@Test
	public void test1() {

		final II i = createMock(II.class);

		thenReturnSeq(1, 2, 3);			// no fa re, ja que no es commiteja
		thenReturnSeq(1, 2, 3).when(i); // no fa re, ja que no es commiteja


		thenReturnSeq(1, 2, 3).when(i).mitjana(anyInt(), eq(10L));

		for (int k = 0; k < 3; k++) {

    		replay(i);

    		assertEquals(1, i.mitjana(5, 10L));

    		try {
    			verify(i);
    		} catch (final SimpleMockingException e) {
    			assertEquals(
    				"unsatisfied expectation: ObjectSequence [1,(2),3]",
    				e.getMessage().replaceAll("\\n", "")
    			);
    		}

    		assertEquals(2, i.mitjana(6, 10L));
    		assertEquals(3, i.mitjana(7, 10L));

    		try {
    			i.mitjana(5, 10L);
    			fail("");
    		} catch (final SimpleMockingException e) {
    			assertEquals(
    				"unsatisfied expectation: end of return list reached; ObjectSequence [1,2,3()]",
    				e.getMessage().replaceAll("\\n", "")
    			);
    		}

    		try {
    			i.mitjana(5, 12L);
    			fail("");
    		} catch (final SimpleMockingException e) {
    			assertEquals(
    				"undefined expectation, caused by an undefined method call: mitjana([5, 12])",
    				e.getMessage().replaceAll("\\n", "")
    			);
    		}

    		verify(i);
		}

		System.out.println(obtainCallConfig(i).toString());
	}

	@Test
	public void test2() {

		final II i = createMock(II.class);

		thenReturnSeq(1).when(i).mitjana(anyInt(), eq(10L));
		thenReturnSeq(2).when(i).mitjana(10, 5L);

		replay(i);

		i.mitjana(10, 5L);
		i.mitjana(10, 10L);

		verify(i);
	}
}

interface II {
	int mitjana(int a, long b);
}