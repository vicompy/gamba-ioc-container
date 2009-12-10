package org.homs.binding;

import java.lang.reflect.Method;

class BeanPropInfo {

	public String propertyName;
	public String setterName;
	public Method method;
	public Class<?> argType;
	public boolean argIsArray;

	public BeanPropInfo(final String propertyName, final String methodName, final Method method, final Class<?> argType) {
		super();
		this.propertyName = propertyName;
		this.setterName = methodName;
		this.method = method;
		this.argType = argType;
		this.argIsArray = argType.isArray();
	}

	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer();

		strb.append("property: ");
		strb.append(propertyName);
		strb.append(", setter: ");
		strb.append(setterName);
		strb.append(", argument type: ");
		strb.append(argType.getSimpleName());

		return strb.toString();
	}

}