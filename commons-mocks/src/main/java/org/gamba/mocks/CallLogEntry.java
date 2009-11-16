package org.gamba.mocks;

import java.lang.reflect.Method;

public class CallLogEntry {

	public final Method method;
	public final Object[] arguments;
	public final Object returnValue;

	public CallLogEntry(final Method method, final Object[] args, final Object returnValue) {
		super();
		this.method = method;
		this.returnValue = returnValue;
		if (args == null) {
			this.arguments = null;
		} else {
			this.arguments = args.clone();
		}
	}

	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer();
		strb.append(method.getName());
		strb.append('(');
		if (arguments != null) {
			for (int i = 0; i < arguments.length; i++) {
				strb.append(arguments[i]);
				if (i < arguments.length - 1) {
					strb.append(", ");
				}
			}
		}
		strb.append(") ==> ");
		strb.append(returnValue);
		return strb.toString();
	}

}
