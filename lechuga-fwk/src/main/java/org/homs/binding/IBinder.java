package org.homs.binding;

import java.util.Map;

public interface IBinder {

	public abstract Object doBind(final Class<?> beanClass, final Map<String, Object> atr);

}