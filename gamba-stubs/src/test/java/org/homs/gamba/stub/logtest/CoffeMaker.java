package org.homs.gamba.stub.logtest;


public class CoffeMaker {

//	private final ILog LOG;
	private final CoffeDelayer coffeDelayer;

	public CoffeMaker(/*final ILog LOG, */final CoffeDelayer coffeDelayer) {
		this.coffeDelayer = coffeDelayer;
//		this.LOG = LOG;
	}

	public void make() {
//		LOG.info("start making coffe...");
		coffeDelayer.delay();
//		LOG.info("Ok.");
	}

}
