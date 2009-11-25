package org.gro.mvc;

import javax.servlet.ServletConfig;

import org.gro.logging.GroLog;

class ViewResolver implements IViewResolver {

	private static final GroLog log = GroLog.getGroLogger(ViewResolver.class);

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

		String viewRequest = null;
		if (resourceName.startsWith("/") && resourceName.contains(".do")) {
			viewRequest = resourceName;
		} else {
			viewRequest = viewResourcePrefix + resourceName + viewResourcePostfix;
		}
		// final String viewRequest = viewResourcePrefix + resourceName +
		// viewResourcePostfix;
		log.fine("redirecting to view: ", viewRequest);
		return viewRequest;
	}

}
