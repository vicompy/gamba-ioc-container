package org.gro.mvc;

import javax.servlet.ServletConfig;

import org.gro.logging.GroLog;

class ViewResolver implements IViewResolver {

	private static final GroLog LOG = GroLog.getGroLogger(ViewResolver.class);

	private final String viewResourcePrefix;
	private final String viewResourcePostfix;

	public ViewResolver(final ServletConfig httpServlet) {
		viewResourcePrefix = httpServlet.getInitParameter(ConfigConstants.VIEW_RESOURCE_PREFIX_SERVLET_PARAMETER);
		viewResourcePostfix = httpServlet.getInitParameter(ConfigConstants.VIEW_RESOURCE_POSTFIX_SERVLET_PARAMETER);
	}

	/**
	 * @see org.gro.mvc.IViewResolver#resolve(java.lang.String)
	 */
	public String resolve(final String resourceName) {

		// si determina que es tracta de petició d'action (/XXX.do) la deixa tal
		// cual, sino li empalma el prefix i postfix definit per a completar el
		// nom de vista.
		String viewRequest = null;
		if (resourceName.startsWith(ConfigConstants.ACTION_SERVLET_PREFIX) &&
			resourceName.contains(ConfigConstants.ACTION_SERVLET_EXTENSION)) {
			viewRequest = resourceName;
			LOG.finest("redirecting to servlet: ", viewRequest);
		} else {
			viewRequest = viewResourcePrefix + resourceName + viewResourcePostfix;
			LOG.finest("redirecting to view: ", viewRequest);
		}

		return viewRequest;
	}

}
