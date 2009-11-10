package org.homs.gamba.binding;

import java.util.HashMap;
import java.util.Map;

public class CachedBeanBinder extends BeanBinder implements IBeanBinder {

	private final Map<Class<?>, BeanInfo> cache;

	public CachedBeanBinder() {
		super();
		this.cache = new HashMap<Class<?>, BeanInfo>();
	}

	/**
	 * @throws BindingException
	 * @see org.homs.gamba.binding.IBeanBinder#doBind(java.lang.Class, java.util.Map)
	 */
	@Override
	public Object doBind(final Class<?> beanClass, final Map<String, String[]> atr) throws BindingException {

		BeanInfo beanInfo = this.cache.get(beanClass);
		if (beanInfo == null) {
			beanInfo = analitza(beanClass);
			this.cache.put(beanClass, beanInfo);
		}
		return bind(beanInfo, atr);
	}

}