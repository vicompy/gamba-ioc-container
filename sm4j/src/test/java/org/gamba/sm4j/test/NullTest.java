package org.gamba.sm4j.test;

import static junit.framework.Assert.assertEquals;
import static org.gamba.sm4j.Mocky.createMock;
import static org.gamba.sm4j.Mocky.replay;
import static org.gamba.sm4j.Mocky.thenReturn;
import static org.gamba.sm4j.Mocky.*;

import org.junit.Test;

// TODO es poden mockar els mètodes static??
public class NullTest {

	@Test
	public void test1() {

		final X x = createMock(X.class);

		thenReturn(null).when(x).sum(null, null);
		thenReturn(3).when(x).sum((Integer) anyObject(), (Integer) anyObject());
		replay(x);

		assertEquals(null, x.sum(null,null));
		assertEquals(Integer.valueOf(3), x.sum(1,2));

		verify(x);
	}

}

interface X {
	Integer sum(Integer a, Integer b);
}