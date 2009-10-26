package org.homs.gamba.stub.logtest;

import org.homs.gamba.stub.syntax.IStubber;
import org.homs.gamba.stub.syntax.Stubber;
import org.junit.Test;

public class CoffeTest {


	@Test
	public void test1() {

		final IStubber<CoffeDelayer> cd = Stubber.createStub(CoffeDelayer.class);
		cd.doReturn(null).when().delay();
		final CoffeDelayer coffeDelayer = cd.play();

//		final IStub<ILog> il = Stub.createStub(ILog.class);
//		il.doReturn(null).when().info("start making coffe...");
//		il.doReturn(null).when().info("Ok.");
//		final ILog log = il.play();


		final CoffeMaker cm = new CoffeMaker(/*log,*/ coffeDelayer);
		cm.make();

//		System.out.println(Stub.obtainReport(log));
		System.out.println(Stubber.obtainReport(coffeDelayer));
	}

}
