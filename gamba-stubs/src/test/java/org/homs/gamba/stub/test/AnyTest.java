package org.homs.gamba.stub.test;

import static org.homs.gamba.stub.bsyntax.Stubber.*;

import org.homs.gamba.stub.ents.IAdder;
import org.junit.Assert;
import org.junit.Test;

public class AnyTest {

	@Test
	public void test1() {

		final IAdder adderStub = createStub(IAdder.class);

		thenReturn(1,2,3).when(adderStub, maskBy(ANY, _)).add(0, 2);
		thenReturn(4,5,6).when(adderStub, maskBy(_, ANY)).add(2, 0);

		play(adderStub);

		Assert.assertEquals(Integer.valueOf(1), adderStub.add(9, 2));
		Assert.assertEquals(Integer.valueOf(2), adderStub.add(9, 2));
		Assert.assertEquals(Integer.valueOf(3), adderStub.add(9, 2));

		Assert.assertEquals(Integer.valueOf(4), adderStub.add(2, 9));
		Assert.assertEquals(Integer.valueOf(5), adderStub.add(2, 9));
		Assert.assertEquals(Integer.valueOf(6), adderStub.add(2, 9));

		System.out.println(obtainCallConfig(adderStub));
		System.out.println(obtainCallReport(adderStub));
	}

}
