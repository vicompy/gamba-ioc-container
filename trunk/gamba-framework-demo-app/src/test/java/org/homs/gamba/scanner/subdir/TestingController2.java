package org.homs.gamba.scanner.subdir;

import org.homs.demo.formbeans.SearchBean;
import org.homs.gamba.extras.EmptyFormBean;
import org.homs.gamba.frontcontroller.RequestContext;
import org.homs.gamba.scanner.Action;

public class TestingController2 {

	@Action(name = "search2", formBean = SearchBean.class)
	public String search(final RequestContext req, final SearchBean form) {
		return null;
	}

	@Action(name = "start2")
	public String startingView(final RequestContext req, final EmptyFormBean form) {
		return null;
	}

}
