package org.lechuga.mvc.binding;

interface ICachedBeanBinder {

	public abstract BeanInfo analize(final Class<?> beanClass) throws BindingException;

}