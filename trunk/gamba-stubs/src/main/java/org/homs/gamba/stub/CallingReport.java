package org.homs.gamba.stub;

import java.lang.reflect.Method;

public class CallingReport {

	public final Method method;
	public final Object[] args;

	public CallingReport(final Method method, final Object[] args) {
		super();
		this.method = method;
		this.args = args;
	}

	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer();
		strb.append(method.getName() + "(");
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				strb.append(args[i]);
				if (i < args.length - 1) {
					strb.append(", ");
				}
			}
		}
		strb.append(")");
		return strb.toString();
	}
}
