package org.homs.gamba.binding;

import java.util.Map;

public class BeanBinder implements IBeanBinder {

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
			// TODO i si beanProp==null?
			if (beanProp == null) {
				throw new BindingException("l'atribut no coincideix amb cap propietat: " + atrName);
			}
			if (atrName.toUpperCase().equals(beanProp.propertyName/*.toUpperCase()*/)) {
				// ei, propietat coincident amb atribut!
				try {
					beanProp.setterMethod.invoke(bean, convert((String[]) atr.get(atrName), beanProp.argType));
				} catch (final Exception exc) {
					throw new BindingException("error injectant al bean: "
							+ cachedBean.getBeanClass().getName() + "." + beanProp.setterMethod.getName() + "("
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

	public Object doBind(final Class<?> beanClass, final Map<String, String[]> atr) throws BindingException {
		final BeanInfo cb = CachedBeanAnalizer.getInstance().analize(beanClass);
		return bind(cb, atr);
	}

}
