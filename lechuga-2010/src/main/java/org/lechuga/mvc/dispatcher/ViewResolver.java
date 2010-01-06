package org.lechuga.mvc.dispatcher;

import org.gro.logging.GroLog;

class ViewResolver {

	private static final GroLog LOG = GroLog.getGroLogger(ViewResolver.class);

	private final String viewResourcePrefix;
	private final String viewResourcePostfix;

	public ViewResolver(final String viewResourcePrefix, final String viewResourcePostfix) {
		super();
		this.viewResourcePrefix = viewResourcePrefix;
		this.viewResourcePostfix = viewResourcePostfix;

		LOG.info(ConfigConstants.VIEW_RESOURCE_PREFIX_SERVLET_PARAMETER, " = ", viewResourcePrefix);
		LOG.info(ConfigConstants.VIEW_RESOURCE_POSTFIX_SERVLET_PARAMETER, " = ", viewResourcePostfix);
	}

	/**
	 * @see org.gro.mvc.IViewResolver#resolve(java.lang.String)
	 */
	public String resolve(final String resourceName) {

		// si determina que es tracta de petició d'action (*.do) la deixa tal
		// cual, sino li empalma el prefix i postfix definit per a completar el
		// nom de vista.
		String viewRequest = null;
//		if (resourceName.startsWith(ConfigConstants.ACTION_SERVLET_PREFIX)
//				&& resourceName.contains(ConfigConstants.ACTION_SERVLET_EXTENSION)) {
		if (resourceName.endsWith(ConfigConstants.ACTION_SERVLET_EXTENSION)) {
			viewRequest = resourceName;
			LOG.finest("redirecting to servlet: ", viewRequest);
		} else {
			viewRequest = viewResourcePrefix + resourceName + viewResourcePostfix;
			LOG.finest("redirecting to view: ", viewRequest);
		}

		return viewRequest;
	}

}