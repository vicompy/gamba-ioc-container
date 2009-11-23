package org.gro.binding;

interface ICachedBeanBinder {

	public abstract BeanInfo analize(final Class<?> beanClass) throws BindingException;

}