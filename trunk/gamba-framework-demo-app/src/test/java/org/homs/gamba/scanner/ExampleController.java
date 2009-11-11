package org.homs.gamba.scanner;

import org.homs.gamba.extras.EmptyFormBean;
import org.homs.gamba.frontcontroller.RequestContext;

/**
 * @author mhoms
 *
 */
public class ExampleController {

	@Action(name = "search", formBean = ExampleBean.class)
	public String search(final RequestContext req, final ExampleBean form) {
		return null;
	}

	@Action(name = "start")
	public String startingView(final RequestContext req, final EmptyFormBean form) {
		return null;
	}

}
