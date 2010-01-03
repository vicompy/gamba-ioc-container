package org.lechuga.mvc.binding;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class CachedBeanAnalizer implements ICachedBeanBinder {

	private final Map<Class<?>, BeanInfo> setterCache;

	public CachedBeanAnalizer() {
		this.setterCache = new HashMap<Class<?>, BeanInfo>();
	}

	/**
	 * @see org.lechuga.mvc.binding.ICachedBeanBinder#analize(java.lang.Class)
	 */
	public BeanInfo analize(final Class<?> beanClass) {

		BeanInfo beanInfo = this.setterCache.get(beanClass);
		if (beanInfo == null) {
			beanInfo = analitzaBean(beanClass);
			this.setterCache.put(beanClass, beanInfo);
		}
		return beanInfo;
	}

	protected BeanInfo analitzaBean(final Class<?> beanClass) {

		final BeanInfo cb = new BeanInfo(beanClass);

		for (final Field field : beanClass.getDeclaredFields()) {
			final Method sm = findForSetterOf(beanClass, field.getName());

			final BeanPropInfo bp = new BeanPropInfo(field.getName(), sm, sm.getParameterTypes()[0]);
			cb.addBeanProp(bp);
		}

		return cb;

	}

	private Method findForSetterOf(final Class<?> beanClass, final String propertyName) {
		for (final Method m : beanClass.getMethods()) {
			if (isMethodSetter(m) && m.getName().equals(setterOf(propertyName))) {
				return m;
			}
		}
		throw new BindingException("setter method not found for property: "+beanClass.getName()+"."+propertyName);
	}

	private String setterOf(final String s) {
		return "set" + s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	private boolean isMethodSetter(final Method m) {
		return m.getParameterTypes().length == 1 && m.getReturnType() == void.class
				&& isSetterName(m.getName());
	}

	private boolean isSetterName(final String s) {
		if (s.substring(0, 3).equals("set") && Character.isUpperCase(s.charAt(3))) {
			return true;
		}
		return false;
	}

}
