package org.homs.gamba.scanner.subdir;

import org.homs.gamba.extras.EmptyFormBean;
import org.homs.gamba.frontcontroller.RequestContext;
import org.homs.gamba.scanner.Action;
import org.homs.gamba.scanner.ExampleBean;

public class ExampleController2 {

	@Action(name = "search2", formBean = ExampleBean.class)
	public String search(final RequestContext req, final ExampleBean form) {
		return null;
	}

	@Action(name = "start2")
	public String startingView(final RequestContext req, final EmptyFormBean form) {
		return null;
	}

}
