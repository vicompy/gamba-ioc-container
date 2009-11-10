package org.homs.gamba.binding;

import java.lang.reflect.Method;

public class BeanAnalizer {

	protected BeanInfo analitza(final Class<?> beanClass) {

		final BeanInfo cb = new BeanInfo(beanClass);

		for (final Method m : beanClass.getMethods()) {
			if (isSetter(m.getName()) && m.getParameterTypes().length == 1 && m.getReturnType() == void.class) {
				final BeanPropInfo bp = new BeanPropInfo(deSet(m.getName()), m.getName(), m, m
						.getParameterTypes()[0]);
				cb.addBeanProp(bp);
			}
		}
		return cb;

	}

	private String deSet(final String s) {
		return s.substring(3, 4).toLowerCase() + s.substring(4);
	}

	private boolean isSetter(final String s) {
		if (s.substring(0, 3).equals("set") && Character.isUpperCase(s.charAt(3))) {
			return true;
		}
		return false;
	}

}
