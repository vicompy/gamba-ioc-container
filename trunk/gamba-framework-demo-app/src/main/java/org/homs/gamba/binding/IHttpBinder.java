package org.homs.gamba.binding;

import java.util.Map;

public interface IHttpBinder {

	public abstract Object doBind(final Class<?> beanClass, final Map<String, Object> atr)
			throws BindingException;

}