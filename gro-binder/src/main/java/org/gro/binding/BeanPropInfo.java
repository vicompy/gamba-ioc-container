package org.gro.binding;

import java.lang.reflect.Method;

class BeanPropInfo {

	public String propertyName;
	public Method setterMethod;
	public Class<?> argType;
	public boolean argIsArray;

	public BeanPropInfo(final String propertyName, final Method setterMethod, final Class<?> argType) {
		super();
		this.propertyName = propertyName;
		this.setterMethod = setterMethod;
		this.argType = argType;
		this.argIsArray = argType.isArray();
	}

//	@Override
//	public String toString() {
//		final StringBuffer strb = new StringBuffer(50);
//
//		strb.append("property: ");
//		strb.append(propertyName);
//		strb.append(", setter: ");
//		strb.append(setterMethod.getName());
//		strb.append(", argument type: ");
//		strb.append(argType.getSimpleName());
//
//		return strb.toString();
//	}

}
