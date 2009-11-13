package org.homs.gamba.stub.test;

import static org.homs.gamba.stub.bsyntax.Stubber.*;

import org.homs.gamba.stub.bsyntax.ForAny;
import org.homs.gamba.stub.ents.IAdder;
import org.junit.Assert;
import org.junit.Test;

public class AnyTest {

	@Test
	public void test1() {

		final IAdder adderStub = (IAdder) createStub(IAdder.class);

		thenReturn(3).when(adderStub, ForAny.forAny(0)).add(1, 2);

		play(adderStub);

		Assert.assertEquals(Integer.valueOf(3), adderStub.add(3, 2));

		System.out.println(obtainCallConfig(adderStub));
		System.out.println(obtainCallConfig(adderStub));
		System.out.println(obtainCallReport(adderStub));
		System.out.println(obtainCallReport(adderStub));
	}


}
