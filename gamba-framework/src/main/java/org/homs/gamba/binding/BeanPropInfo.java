package org.homs.gamba.binding;

import java.lang.reflect.Method;

public class BeanPropInfo {

	public String propertyName;
	public String setterName;
	public Method setterMethod;
	public String getterName;
	public Method getterMethod;
	public Class<?> argType;
	public boolean argIsArray;

	public BeanPropInfo(final String propertyName, final String setterName, final Method setterMethod,
			final String getterName, final Method getterMethod, final Class<?> argType
			) {
		super();
		this.propertyName = propertyName.toUpperCase();
		this.setterName = setterName;
		this.setterMethod = setterMethod;
		this.getterName = getterName;
		this.getterMethod = getterMethod;
		this.argType = argType;
		this.argIsArray = argType.isArray();

	}

	// public BeanPropInfo(final String propertyName, final String methodName,
	// final Method setterMethod,
	// final Class<?> argType) {
	// super();
	// this.propertyName = propertyName.toUpperCase();
	// this.setterName = methodName;
	// this.setterMethod = setterMethod;
	// this.argType = argType;
	// this.argIsArray = argType.isArray();
	// }
	//
	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer(50);

		strb.append("property: ");
		strb.append(propertyName);
		strb.append(", setter: ");
		strb.append(setterName);
		strb.append(", argument type: ");
		strb.append(argType.getSimpleName());

		return strb.toString();
	}

}
