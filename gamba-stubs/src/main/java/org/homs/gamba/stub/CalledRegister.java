package org.homs.gamba.stub;

import java.lang.reflect.Method;

public class CalledRegister {

	public final Method method;
	public final Object[] arguments;

	public CalledRegister(final Method method, final Object[] args) {
		super();
		this.method = method;
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
		strb.append(')');
		return strb.toString();
	}
}