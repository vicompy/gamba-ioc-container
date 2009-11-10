package org.homs.gamba.binding;

import java.util.HashMap;
import java.util.Map;

public class BeanInfo {

	private final Class<?> beanClass;
	private final Map<String, BeanPropInfo> beanProps;

	public BeanInfo(final Class<?> beanClass) {
		super();
		this.beanClass = beanClass;
		this.beanProps = new HashMap<String, BeanPropInfo>();
	}

	public void addBeanProp(final BeanPropInfo beanProp) {
		this.beanProps.put(beanProp.propertyName.toUpperCase(), beanProp);
		System.out.println("registered prop: "+beanProp.propertyName.toUpperCase());
	}

	public Class<?> getBeanClass() {
		return beanClass;
	}

	public Map<String, BeanPropInfo> getBeanProps() {
		return beanProps;
	}

	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer();

		strb.append("bean: ");
		strb.append(beanClass.getSimpleName());
		strb.append('\n');
		for (final String propName : beanProps.keySet()) {
			strb.append("   ");
			strb.append(beanProps.get(propName).toString());
			strb.append('\n');
		}

		return strb.toString();
	}

}
