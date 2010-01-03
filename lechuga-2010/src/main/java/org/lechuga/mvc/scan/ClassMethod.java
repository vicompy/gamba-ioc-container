package org.lechuga.mvc.scan;

import java.lang.reflect.Method;

import org.lechuga.mvc.scan.clas.filters.ControllerFilter;

public class ClassMethod {

	public Class<?> controller;
	public Method method;

	public String controllerShortName;
	public String requestPath;

	public ClassMethod(final Class<?> controller, final Method method) {
		super();
		this.controller = controller;
		this.method = method;

		controllerShortName = controller.getSimpleName().substring(0,
				controller.getSimpleName().length() - ControllerFilter.CLASSNAME_POSTFIX.length());

		requestPath = "/"+UnCameler
				.deCamelize(controllerShortName + "/" + UnCameler.deCamelize(method.getName())) + ".do";
	}

	@Override
	public String toString() {
		return requestPath + " => " + controller.getSimpleName() + "." + method.getName();
	}

	public String getRequestPath() {
		return requestPath;
	}

}