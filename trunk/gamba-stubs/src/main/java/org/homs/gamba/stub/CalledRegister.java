package org.homs.gamba.stub;

import java.lang.reflect.Method;

public class CalledRegister {

	public final Method method;
	public final Object[] args;

	public CalledRegister(final Method method, final Object[] args) {
		super();
		this.method = method;
		if (args == null) {
			this.args = null;
		} else {
			this.args = args.clone();
		}
	}

	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer();
		strb.append(method.getName());
		strb.append('(');
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				strb.append(args[i]);
				if (i < args.length - 1) {
					strb.append(", ");
				}
			}
		}
		strb.append(')');
		return strb.toString();
	}
}
