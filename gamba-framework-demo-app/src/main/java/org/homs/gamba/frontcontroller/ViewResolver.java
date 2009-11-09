package org.homs.gamba.frontcontroller;

import javax.servlet.http.HttpServlet;

class ViewResolver {
	private final String viewResourcePrefix;
	private final String viewResourcePostfix;

	public ViewResolver(final HttpServlet httpServlet) {
		viewResourcePrefix = httpServlet.getInitParameter("view-resource-prefix");
		viewResourcePostfix = httpServlet.getInitParameter("view-resource-postfix");
	}

	public String resolve(final String resourceName) {
		return viewResourcePrefix + resourceName + viewResourcePostfix;

	}

}
