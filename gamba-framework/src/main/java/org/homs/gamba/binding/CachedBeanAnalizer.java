package org.homs.gamba.binding;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CachedBeanAnalizer {

	private final Map<Class<?>, BeanInfo> setterCache;

	private CachedBeanAnalizer() {
		this.setterCache = new HashMap<Class<?>, BeanInfo>();
	}

	private static class SingletonHolder {
		private static final CachedBeanAnalizer INSTANCE = new CachedBeanAnalizer();
	}

	public static CachedBeanAnalizer getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public BeanInfo analize(final Class<?> beanClass) throws BindingException {

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
			final Method gm = findForGetterOf(beanClass, field.getName());


			final BeanPropInfo bp = new BeanPropInfo(
					deSet(sm.getName()),
					sm.getName(),
					sm,
					gm.getName(),
					gm,
					sm.getParameterTypes()[0]);
//			System.out.println("** " + bp);
			cb.addBeanProp(bp);
		}

//		String propertyName,
//		final String setterName,
//		final Method setterMethod,
//		final String getterName,
//		final Method getterMethod,
//		final Class<?> argType,

		//		for (final Method m : beanClass.getMethods()) {
//			if (isMethodSetter(m)) {
//				final BeanPropInfo bp = new BeanPropInfo(deSet(m.getName()), m.getName(), m, m
//						.getParameterTypes()[0]);
//				cb.addBeanProp(bp);
//			}
//		}
		return cb;

	}

	public Method findForSetterOf(final Class<?> beanClass, final String propertyName) {
		for (final Method m : beanClass.getMethods()) {
			if (isMethodSetter(m) && m.getName().equals(setterOf(propertyName))) {
//				System.out.println("trobat setter de " + propertyName + ": " + m.getName());
				return m;
			}
		}
//		return null;
		throw new NullPointerException();
	}
	public Method findForGetterOf(final Class<?> beanClass, final String propertyName) {
		for (final Method m : beanClass.getMethods()) {
			if (isMethodGetter(m) && m.getName().equals(getterOf(propertyName))) {
//				System.out.println("trobat getter de " + propertyName + ": " + m.getName());
				return m;
			}
		}
//		return null;
		throw new NullPointerException();
	}

	private String setterOf(final String s) {
		return "set" + s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	private String getterOf(final String s) {
		return "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	private String deSet(final String s) {
		return s.substring(3, 4).toLowerCase() + s.substring(4);
	}

	private boolean isMethodSetter(final Method m) {
		return m.getParameterTypes().length == 1 && m.getReturnType() == void.class
				&& isSetterName(m.getName());
	}

	private boolean isMethodGetter(final Method m) {
		return m.getParameterTypes().length == 0 && m.getReturnType() != void.class
				&& isGetterName(m.getName());
	}

	private boolean isSetterName(final String s) {
		if (s.substring(0, 3).equals("set") && Character.isUpperCase(s.charAt(3))) {
			return true;
		}
		return false;
	}

	private boolean isGetterName(final String s) {
		if (s.substring(0, 3).equals("get") && Character.isUpperCase(s.charAt(3))) {
			return true;
		}
		return false;
	}

}
