package org.homs.gamba.binding;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CachedBeanAnalizer {

	private final Map<Class<?>, BeanInfo> cache;

	private CachedBeanAnalizer() {
		this.cache = new HashMap<Class<?>, BeanInfo>();
	}

	private static class SingletonHolder {
		private static final CachedBeanAnalizer INSTANCE = new CachedBeanAnalizer();
	}

	public static CachedBeanAnalizer getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public BeanInfo analize(final Class<?> beanClass) throws BindingException {

		BeanInfo beanInfo = this.cache.get(beanClass);
		if (beanInfo == null) {
			beanInfo = analitzaBean(beanClass);
			this.cache.put(beanClass, beanInfo);
		}
		return beanInfo;
	}

	protected BeanInfo analitzaBean(final Class<?> beanClass) {

		final BeanInfo cb = new BeanInfo(beanClass);

		for (final Method m : beanClass.getMethods()) {
			if (isMethodSetter(m)) {
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
