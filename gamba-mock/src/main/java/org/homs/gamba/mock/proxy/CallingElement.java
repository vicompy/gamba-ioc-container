package org.homs.gamba.mock.proxy;

import java.lang.reflect.Method;

public class CallingElement {

	private Method method;
//	private Class<?>[] callingArgsTypes;
	private Object[] callingArgsValues;
	private final Object returningObject;

	public CallingElement(final Object returningObject) {
		super();
		this.returningObject = returningObject;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(final Method method) {
		this.method = method;
	}

//	public Class<?>[] getCallingArgsTypes() {
//		return callingArgsTypes;
//	}
//
//	public void setCallingArgsTypes(final Class<?>[] callingArgsTypes) {
//		this.callingArgsTypes = callingArgsTypes;
//	}

	public Object[] getCallingArgsValues() {
		return callingArgsValues;
	}

	public void setCallingArgsValues(final Object[] callingArgsValues) {
		this.callingArgsValues = callingArgsValues;
	}

	public Object getReturningObject() {
		return returningObject;
	}

	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer();
		strb.append(returningObject.toString());
		strb.append(" <= ");
		strb.append(method.getName());
		strb.append('(');
		for (int i = 0; i < callingArgsValues.length; i++) {
			strb.append(callingArgsValues[i].toString());
			strb.append(", ");
		}
		strb.append(')');
		return strb.toString();
	}

}
