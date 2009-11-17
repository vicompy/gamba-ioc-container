package mock2;

import static org.gamba.mocks.Mocky.*;
import static junit.framework.Assert.*;

import org.gamba.mocks.exception.GambaMockException;
import org.junit.Test;

public class Mock2Test {

	@Test
	public void test1() {
		final I i = createMock(I.class);

		// Mocky.thenReturn(3).when(i).getHashValue(100);

		// ===> invoking: setSeq
		// ===> invoking: addMaskValue
		// ===> invoking: addMaskValue
		// ===> invoking: mitjana

		thenReturnSeq(1, 2, 3).when(i).mitjana(anyInt(), eq(10L));

		for (int k = 0; k < 3; k++) {
    		replay(i);

    		assertEquals(1, i.mitjana(5, 10L));
    		assertEquals(2, i.mitjana(6, 10L));
    		assertEquals(3, i.mitjana(7, 10L));

    		try {
    			i.mitjana(5, 10L);
    			fail("");
    		} catch (final GambaMockException e) {
    			assertEquals(
    				"unsatisfied expectation: end of return list reached; ObjectSequence [1,2,3()]",
    				e.getMessage().replaceAll("\\n", "")
    			);
    		}

    		try {
    			i.mitjana(5, 12L);
    			fail("");
    		} catch (final GambaMockException e) {
    			assertEquals(
    				"undefined expectation, caused by an undefined method call: mitjana([5, 12])",
    				e.getMessage().replaceAll("\\n", "")
    			);
    		}
		}



	}
}

interface I {
	int mitjana(int a, long b);
}