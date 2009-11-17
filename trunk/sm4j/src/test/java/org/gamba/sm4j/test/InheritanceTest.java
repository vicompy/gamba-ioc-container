package org.gamba.sm4j.test;

import org.gamba.sm4j.ents.IB;
import org.gamba.sm4j.ents.IC;
import org.junit.Test;

import static org.gamba.sm4j.Mocky.*;
import static junit.framework.Assert.*;

public class InheritanceTest {

	@Test
	public void test1() {

		final IB b = createMock(IB.class);
		thenReturn(27).when(b).getAge();
		thenReturn(54).when(b).getDoubledAge(27);
		thenReturn("mhc").when(b).getName();
		replay(b);

		assertEquals(27, b.getAge());
		assertEquals(Integer.valueOf(54), b.getDoubledAge(27));
		assertEquals("mhc", b.getName());
	}

	@Test
	public void test2() {

		final IB c = (IB) createMock(IC.class, IB.class);
		thenReturn(27).when(c).getAge();
		thenReturn(54).when(c).getDoubledAge(27);
		thenReturn("mhc").when(c).getName();

		thenReturn(28).when((IC) c).getCAge();
		thenReturn("mhc2").when((IC) c).getCName();
		replay(c);

		assertEquals(27, c.getAge());
		assertEquals(Integer.valueOf(54), c.getDoubledAge(27));
		assertEquals("mhc", c.getName());

		assertEquals(28, ((IC) c).getCAge());
		assertEquals("mhc2", ((IC) c).getCName());
	}

}
