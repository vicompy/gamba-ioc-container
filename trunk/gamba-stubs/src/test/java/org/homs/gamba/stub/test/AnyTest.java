package org.homs.gamba.stub.test;

import static org.homs.gamba.stub.bsyntax.Stubber.createStub;
import static org.homs.gamba.stub.bsyntax.Stubber.obtainCallReport;
import static org.homs.gamba.stub.bsyntax.Stubber.play;
import static org.homs.gamba.stub.bsyntax.Stubber.thenReturn;

import org.homs.gamba.stub.any.Any;
import org.homs.gamba.stub.ents.IAdder;
import org.junit.Assert;
import org.junit.Test;

public class AnyTest {

	@Test
	public void test1() {

		final IAdder adderStub = (IAdder) createStub(IAdder.class);
		thenReturn(3).when(adderStub).add((Integer) Any.any(Number.class), 2);

		play(adderStub);
		System.out.println(obtainCallReport(adderStub));

		Assert.assertEquals(Integer.valueOf(3), adderStub.add(1, 2));
	}


}
