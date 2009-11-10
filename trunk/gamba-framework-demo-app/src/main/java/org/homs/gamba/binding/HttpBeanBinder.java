package org.homs.gamba.binding;

import java.lang.reflect.Method;
import java.util.Map;

public class HttpBeanBinder implements IHttpBinder {

	protected Object bind(final BeanInfo cachedBean, final Map<String, String[]> atr) throws BindingException {

		Object bean = null;
		try {
			bean = cachedBean.getBeanClass().newInstance();
		} catch (final Exception exc) {
			throw new BindingException("error instanciant el bean: " + cachedBean.getBeanClass().getName(),
					exc);
		}

		for (final String atrName : atr.keySet()) {
			System.out.println("asking for prop: "+atrName.toUpperCase());
			final BeanPropInfo beanProp = cachedBean.getBeanProps().get(atrName.toUpperCase());
			if (atrName.toUpperCase().equals(beanProp.propertyName/*.toUpperCase()*/)) {
				// ei, propietat coincident amb atribut!
				try {
					beanProp.method.invoke(bean, convert((String[]) atr.get(atrName), beanProp.argType));
				} catch (final Exception exc) {
					throw new BindingException("error injectant al bean: "
							+ cachedBean.getBeanClass().getName() + "." + beanProp.method.getName() + "("
							+ beanProp.argType.getName() + ")", exc);
				}
			}
		}
		return bean;
	}

	private Object convert(final String[] value, final Class<?> type) {
		if (type.isArray()) {
			final int len = ((Object[]) value).length;
			final Object[] r = newArray(type.getComponentType(), len);

			for (int i = 0; i < len; i++) {
				((Object[]) r)[i] = convertToPrimitiveWrapper(type.getComponentType(), ((String[]) value)[i]);
			}
			return r;

		} else {
			if (type.isPrimitive()) {
				return convertToPrimitive(type, value[0]);
			} else {
				return convertToPrimitiveWrapper(type, value[0]);
			}
		}
	}

	private Object convertToPrimitive(final Class<?> type, final String v) {
		if (int.class.equals(type))
			return Integer.valueOf(v);
		if (long.class.equals(type))
			return Long.valueOf(v);
		if (float.class.equals(type))
			return Float.valueOf(v);
		if (double.class.equals(type))
			return Double.valueOf(v);
		if (boolean.class.equals(type))
			return Boolean.valueOf(v); // TODO si?
		if (short.class.equals(type))
			return Short.valueOf(v);
		throw new BindingException();
	}

	private Object convertToPrimitiveWrapper(final Class<?> type, final String v) {
		if (Integer.class.equals(type))
			return Integer.valueOf(v);
		if (Long.class.equals(type))
			return Long.valueOf(v);
		if (Float.class.equals(type))
			return Float.valueOf(v);
		if (Double.class.equals(type))
			return Double.valueOf(v);
		if (Boolean.class.equals(type))
			return Boolean.valueOf(v); // TODO si?
		if (Short.class.equals(type))
			return Short.valueOf(v);
		if (String.class.equals(type))
			return v;
		throw new BindingException();
	}

	private Object[] newArray(final Class<?> type, final int size) {
		if (type.isPrimitive()) {
			throw new BindingException();
		} else {
			if (Integer.class.equals(type))
				return new Integer[size];
			if (Long.class.equals(type))
				return new Long[size];
			if (Float.class.equals(type))
				return new Float[size];
			if (Double.class.equals(type))
				return new Double[size];
			if (Boolean.class.equals(type))
				return new Boolean[size]; // TODO si?
			if (Short.class.equals(type))
				return new Short[size];
			if (String.class.equals(type))
				return new String[size];
		}
		throw new BindingException();
	}

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

	public Object doBind(final Class<?> beanClass, final Map<String, String[]> atr) throws BindingException {
		final BeanInfo cb = analitza(beanClass);
		return bind(cb, atr);
	}

}
