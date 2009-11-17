package org.gamba.sm4j.test;

import java.util.List;

import org.gamba.sm4j.exception.GambaMockingException;
import org.junit.Test;

import static org.gamba.sm4j.Mocky.*;
import static junit.framework.Assert.*;

public class ExpectationsTest {

	public void testReturns(final int expected, final int real) {

		final List<?> l = createMock(List.class);
		thenReturn(5, expected).when(l).get(0);
		replay(l);

		for (int i = 0; i < real; i++) {
			assertEquals(5, l.get(0));
		}

		verify(l);
	}

	@Test
	public void testReturns1() {
		testThrows(5, 5);
	}

	@Test(expected = GambaMockingException.class)
	public void testReturns2() {
		testThrows(4, 5);
	}

	@Test(expected = GambaMockingException.class)
	public void testReturns3() {
		testThrows(6, 5);
	}

	public void testThrows(final int expected, final int real) {

		final List<?> l = createMock(List.class);
		thenThrow(new NullPointerException(), expected).when(l).get(0);
		replay(l);

		for (int i = 0; i < real; i++) {
			try {
				l.get(0);
			} catch (final NullPointerException e) {
			}
		}

		verify(l);
	}

	@Test
	public void testThrows1() {
		testThrows(5, 5);
	}

	@Test(expected = GambaMockingException.class)
	public void testThrows2() {
		testThrows(4, 5);
	}

	@Test(expected = GambaMockingException.class)
	public void testThrows3() {
		testThrows(6, 5);
	}

}
