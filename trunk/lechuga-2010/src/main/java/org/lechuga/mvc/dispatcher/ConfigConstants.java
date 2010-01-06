package org.lechuga.mvc.dispatcher;

public class ConfigConstants {

	public final static String CONTROLLERS_BASE_PACKAGE_SERVLET_PARAMETER = "controllers-base-package-name";
	public final static String CONTROLLERS_BASE_PACKAGE_DEFAULT_VALUE = "org";

	public static final String VIEW_RESOURCE_POSTFIX_SERVLET_PARAMETER = "view-resource-postfix";
	public static final String VIEW_RESOURCE_PREFIX_SERVLET_PARAMETER = "view-resource-prefix";
	public static final String VIEW_RESOURCE_PREFIX_DEFAULT = "/jsp/";
	public static final String VIEW_RESOURCE_POSTFIX_DEFAULT = ".jsp";

//	public static final String ACTION_SERVLET_PREFIX = "/";
	public static final String ACTION_SERVLET_EXTENSION = ".do";

	public static final String CONTEXT_NAME_ATTRIBUTE_NAME = "contextName";
	public static final String FORMBEAN_ATTRIBUTE_NAME = "form"; // TODO renombrar a "formBean"
	public static final String VALIDATION_ERROR_MAP_ATTRIBUTE_NAME = "validationErrorMap";

}
