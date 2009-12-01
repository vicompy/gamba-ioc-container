package org.gro.mvc;


public class ConfigConstants {

	public static final String ACTION_SERVLET_PREFIX = "/";
	public static final String ACTION_SERVLET_EXTENSION = ".do";


	/**
	 * nom del paràmetre de servlet definit en <tt>web.xml</tt>, que servirà per
	 * scannejar el package que conté les accions de l'aplicació.
	 */
	public static final String ACTIONS_BASE_PACKAGE_SERVLET_PARAMETER = "actions-base-package";
	public static final String VIEW_RESOURCE_POSTFIX_SERVLET_PARAMETER = "view-resource-postfix";
	public static final String VIEW_RESOURCE_PREFIX_SERVLET_PARAMETER = "view-resource-prefix";

	public static final String CONTEXT_NAME_ATTRIBUTE_NAME = "contextName";
	public static final String REQUEST_CONTEXT_ATTRIBUTE_NAME = "requestContext";
	public static final String VALIDATION_ERROR_MAP_ATTRIBUTE_NAME = "validationErrorMap";
	public static final String FORMBEAN_ATTRIBUTE_NAME = "form";

	/**
	 * la llargada en caràcters de l'extensió de les crides a Action, ha de
	 * concordar en longitud amb el mapping de servlet fet en <tt>web.xml</tt>.
	 */
	public static final int ACTION_EXTENSION_LENGTH = ACTION_SERVLET_EXTENSION.length();

}
