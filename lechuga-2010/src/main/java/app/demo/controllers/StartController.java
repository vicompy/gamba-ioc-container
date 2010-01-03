package app.demo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StartController {

//	private final static GroLog LOG = GroLog.getGroLogger(ControllerScanFacade.class);

	public String demo(final HttpServletRequest request, final HttpServletResponse response) {

		return "/demo/start.do";
	}


}
