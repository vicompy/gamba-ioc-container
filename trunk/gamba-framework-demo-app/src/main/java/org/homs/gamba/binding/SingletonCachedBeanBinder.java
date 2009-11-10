package org.homs.gamba.binding;

import java.util.Map;

public class SingletonCachedBeanBinder implements IBeanBinder {

	private static class SingletonHolder {
		private static final IBeanBinder INSTANCE = new CachedBeanBinder();
	}

	public static IBeanBinder getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public Object doBind(final Class<?> beanClass, final Map<String, String[]> atr) throws BindingException {
		return getInstance().doBind(beanClass, atr);
	}

}
