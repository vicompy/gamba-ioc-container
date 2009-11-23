package org.gro.frontcontroller.actions;

import java.lang.reflect.Method;

public class DeclaredAction {

	public final String actionName;
	public final Class<?> actionClass;
	public final Method actionMethod;
	public final Class<?> actionForm;

	public final Class<?> validatorClass;
	public final String resourceIfInvalidForm;


	public DeclaredAction(final String actionName, final Class<?> actionClass, final Method actionMethod, final Class<?> actionForm,
			final Class<?> validatorClass, final String resourceIfInvalidForm) {
		super();
		this.actionName = actionName;
		this.actionClass = actionClass;
		this.actionMethod = actionMethod;
		this.actionForm = actionForm;
		this.validatorClass = validatorClass;
		this.resourceIfInvalidForm = resourceIfInvalidForm;
	}


	@Override
	public String toString() {
		return actionName + ": " + actionClass.getName() + "." + actionMethod.getName() + "(...), using "
				+ actionForm.getName();
	}

}
