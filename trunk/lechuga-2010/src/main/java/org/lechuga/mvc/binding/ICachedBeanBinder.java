package org.lechuga.mvc.binding;

interface ICachedBeanBinder {

	abstract BeanInfo analize(final Class<?> beanClass) throws BindingException;

}