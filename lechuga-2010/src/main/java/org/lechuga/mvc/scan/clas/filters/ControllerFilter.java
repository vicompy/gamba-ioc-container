package org.lechuga.mvc.scan.clas.filters;

public class ControllerFilter implements IClassFilter {

	public final static String CLASSNAME_POSTFIX = "Controller";

	public boolean mustBeAccepted(final Class<?> claz) {
		return claz.getName().endsWith(CLASSNAME_POSTFIX);
	}

}
