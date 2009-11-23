package org.gro.binding;

import java.util.Map;

public class BeanBinder implements IBeanBinder {

	private final CachedBeanAnalizer cachedBeanAnalizer = new CachedBeanAnalizer();

	protected Object bind(final BeanInfo cachedBean, final Map<String, String[]> atr) throws BindingException {

		Object bean = null;
		try {
			bean = cachedBean.getBeanClass().newInstance();
		} catch (final Exception exc) {
			throw new BindingException("error instanciant el bean: " + cachedBean.getBeanClass().getName(),
					exc);
		}

		for (final String atrName : atr.keySet()) {
			final BeanPropInfo beanProp = cachedBean.getBeanProps().get(atrName);
			if (beanProp == null) {
				throw new BindingException("l'atribut no coincideix amb cap propietat: " + atrName);
			}

			if (atrName.equals(beanProp.propertyName)) {
				// ei, propietat coincident amb atribut!
				try {
					beanProp.setterMethod
							.invoke(bean, convert((String[]) atr.get(atrName), beanProp.argType));
				} catch (final Exception exc) {
					throw new BindingException("error injectant al bean: "
							+ cachedBean.getBeanClass().getName() + "." + beanProp.setterMethod.getName()
							+ "(" + beanProp.argType.getName() + ")", exc);
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
		try {
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
		} catch (final NumberFormatException e) {
			throw new BindingException("", e); // TODO
		}

		throw new BindingException("");// TODO
	}

	private Object convertToPrimitiveWrapper(final Class<?> type, final String v) {
		try {
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
		} catch (final NumberFormatException e) {
			throw new BindingException("", e); // TODO
		}

		throw new BindingException("");// TODO
	}

	private Object[] newArray(final Class<?> type, final int size) {
		if (type.isPrimitive()) {
			throw new BindingException(""); //TODO
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
			throw new BindingException(""); //TODO
		}
	}

	public Object doBind(final Class<?> beanClass, final Map<String, String[]> atr) throws BindingException {
		final BeanInfo cb = cachedBeanAnalizer.analize(beanClass);
		return bind(cb, atr);
	}

}
