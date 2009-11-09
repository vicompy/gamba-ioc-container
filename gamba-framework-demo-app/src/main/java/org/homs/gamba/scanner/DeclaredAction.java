package org.homs.gamba.scanner;

import java.lang.reflect.Method;

public class DeclaredAction {

	public final String actionName;
	public final Class<?> actionClass;
	public final Method actionMethod;
	public final Class<?> actionForm;

	public DeclaredAction(final String actionName, final Class<?> actionClass, final Method actionMethod,
			final Class<?> actionForm) {
		super();
		this.actionName = actionName;
		this.actionClass = actionClass;
		this.actionMethod = actionMethod;
		this.actionForm = actionForm;
	}

	@Override
	public String toString() {
		return actionName + ": " + actionClass.getName() + "." + actionMethod.getName() + "(...), using "
				+ actionForm.getName();
	}

}
