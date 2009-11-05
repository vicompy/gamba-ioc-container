package org.homs.server;

import java.lang.reflect.Method;

class ResolvedAction {

	public final Object actionInstance;
	public final Method actionMethod;

	public ResolvedAction(final Object actionInstance, final Method actionMethod) {
		super();
		this.actionInstance = actionInstance;
		this.actionMethod = actionMethod;
	}

	public Object getActionInstance() {
		return actionInstance;
	}

	public Method getActionMethod() {
		return actionMethod;
	}

}
