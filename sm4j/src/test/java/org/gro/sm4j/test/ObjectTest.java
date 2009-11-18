package org.gro.sm4j.test;

import java.util.ArrayList;
import java.util.List;

import org.gro.sm4j.exception.SimpleMockingException;
import org.junit.Test;

import static org.gro.sm4j.Mocky.*;
import static junit.framework.Assert.*;

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

		thenReturnSeq(1, 2, 3).when(i).hashCode();
		replay(i);

		for (int c = 0; c < 5; c++) {
			try {
				i.hashCode();
				verify(i);
				l.add("OK:" + c + " ");
			} catch (final SimpleMockingException e) {
				l.add("KO:" + c + " ");
			}
		}
		assertEquals("[KO:0 , KO:1 , OK:2 , KO:3 , KO:4 ]", l.toString());
		// assertEquals("[hashCode() ==> 1, hashCode() ==> 2, hashCode() ==> 3]",
		// obtainCallingLog(i).toString());
	}

}

interface I {
	public int hashCode();
}
