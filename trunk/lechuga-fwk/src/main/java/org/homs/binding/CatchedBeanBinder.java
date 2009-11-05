package org.homs.binding;

import java.util.HashMap;
import java.util.Map;

public class CatchedBeanBinder extends BeanBinder implements IBinder {

	private final Map<Class<?>, BeanInfo> cache;

	public CatchedBeanBinder() {
		this.cache = new HashMap<Class<?>, BeanInfo>();
	}

	/**
	 * @see org.homs.binding.IBinder#doBind(java.lang.Class, java.util.Map)
	 */
	public Object doBind(final Class<?> beanClass, final Map<String, Object> atr) {

		BeanInfo cb = this.cache.get(beanClass);
		if (cb == null) {
			cb = analitza(beanClass);
			this.cache.put(beanClass, cb);
			System.out.println("analitzat " + beanClass.getSimpleName());
		}
		return bind(cb, atr);
	}

}
