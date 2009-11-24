package org.gro.mvc;

import javax.servlet.ServletConfig;

class ViewResolver implements IViewResolver {
	private final String viewResourcePrefix;
	private final String viewResourcePostfix;

	public ViewResolver(final ServletConfig httpServlet) {
		viewResourcePrefix = httpServlet.getInitParameter("view-resource-prefix");
		viewResourcePostfix = httpServlet.getInitParameter("view-resource-postfix");
	}

	/**
	 * @see org.gro.mvc.IViewResolver#resolve(java.lang.String)
	 */
	public String resolve(final String resourceName) {
		return viewResourcePrefix + resourceName + viewResourcePostfix;
	}

}
