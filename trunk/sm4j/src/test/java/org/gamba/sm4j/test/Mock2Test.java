package org.gamba.sm4j.test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.gamba.sm4j.Mocky.anyInt;
import static org.gamba.sm4j.Mocky.createMock;
import static org.gamba.sm4j.Mocky.eq;
import static org.gamba.sm4j.Mocky.obtainCallConfig;
import static org.gamba.sm4j.Mocky.replay;
import static org.gamba.sm4j.Mocky.thenReturnSeq;
import static org.gamba.sm4j.Mocky.verify;

import org.gamba.sm4j.exception.GambaMockingException;
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
    		} catch (final GambaMockingException e) {
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
    		} catch (final GambaMockingException e) {
    			assertEquals(
    				"unsatisfied expectation: end of return list reached; ObjectSequence [1,2,3()]",
    				e.getMessage().replaceAll("\\n", "")
    			);
    		}

    		try {
    			i.mitjana(5, 12L);
    			fail("");
    		} catch (final GambaMockingException e) {
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

		thenReturnSeq(1, 2, 3).when(i).mitjana(anyInt(), eq(10L));
		thenReturnSeq(1, 2, 3).when(i).mitjana(10, 5L);

		replay(i);

		i.mitjana(10, 5L);
		i.mitjana(10, 10L);

	}
}

interface II {
	int mitjana(int a, long b);
}