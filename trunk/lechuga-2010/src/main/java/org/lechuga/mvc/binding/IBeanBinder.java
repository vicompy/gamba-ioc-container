package org.lechuga.mvc.binding;

import java.util.Map;

public interface IBeanBinder {

	abstract Object doBind(final Class<?> beanClass, final Map<String, String[]> atr)
			throws BindingException;

}