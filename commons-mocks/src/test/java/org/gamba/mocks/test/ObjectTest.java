package org.gamba.mocks.test;

import static org.gamba.mocks.fluent.Mocker.*;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;

import org.gamba.mocks.exception.GambaMockException;
import org.gamba.mocks.fluent.Mocker;
import org.junit.Test;

public class ObjectTest {

	@Test
	public void test1() {
		final I i = createMock(I.class);

		thenReturn(2).when(i).hashCode();
		replay(i);

		assertEquals(2, i.hashCode());

		verify(i);
	}

	@Test
	public void test2() {
		final List<String> l = new ArrayList<String>();

		final I i = createMock(I.class);

		thenReturnList(1, 2, 3).when(i).hashCode();
		replay(i);

		for (int c = 0; c < 5; c++) {
			try {
				i.hashCode();
				verify(i);
				l.add("OK:" + c + " ");
			} catch (final GambaMockException e) {
				l.add("KO:" + c + " ");
			}
		}
		assertEquals("[KO:0 , KO:1 , OK:2 , KO:3 , KO:4 ]", l.toString());
		assertEquals("[hashCode() ==> 1, hashCode() ==> 2, hashCode() ==> 3]", Mocker.obtainCallingLog(i).toString());
	}

	// @Override
	// public int hashCode() {
	// return 1;
	// }
}

interface I {
	public int hashCode();
}

