package org.homs.gamba.scanner;

import org.homs.gamba.extras.EmptyFormBean;
import org.homs.gamba.frontcontroller.RequestContext;

public class Controller {

	@Action(name = "search", formBean = SearchBean.class)
	public String search(final RequestContext req, final SearchBean form) {
		return null;
	}

	@Action(name = "start")
	public String startingView(final RequestContext req, final EmptyFormBean form) {
		return null;
	}

}
