package org.homs.gamba.stub.test;

import static org.homs.gamba.stub.bsyntax.Stubber.*;

import org.homs.gamba.stub.ents.IB;
import org.homs.gamba.stub.ents.IC;
import org.junit.Assert;
import org.junit.Test;

public class InheritanceTest {

	@Test
	public void test1() {

		final IB b = createStub(IB.class);
		thenReturn(27).when(b).getAge();
		thenReturn(54).when(b).getDoubledAge(27);
		thenReturn("mhc").when(b).getName();
		play(b);

		Assert.assertEquals(27, b.getAge());
		Assert.assertEquals(Integer.valueOf(54), b.getDoubledAge(27));
		Assert.assertEquals("mhc", b.getName());
	}

	@Test
	public void test2() {

		final IB c = (IB) createStub(IC.class, IB.class);
		thenReturn(27).when(c).getAge();
		thenReturn(54).when(c).getDoubledAge(27);
		thenReturn("mhc").when(c).getName();

		thenReturn(28).when((IC) c).getCAge();
		thenReturn("mhc2").when((IC) c).getCName();
		play(c);

		Assert.assertEquals(27, c.getAge());
		Assert.assertEquals(Integer.valueOf(54), c.getDoubledAge(27));
		Assert.assertEquals("mhc", c.getName());

		Assert.assertEquals(28, ((IC) c).getCAge());
		Assert.assertEquals("mhc2", ((IC) c).getCName());
	}

}
