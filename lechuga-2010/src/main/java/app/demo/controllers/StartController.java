package app.demo.controllers;

import org.lechuga.mvc.dispatcher.RequestContext;

public class StartController {

	// private final static GroLog LOG =
	// GroLog.getGroLogger(ControllerScanFacade.class);

	public String demo(final RequestContext ctx) {

		return "/demo/start.do";
	}

}
