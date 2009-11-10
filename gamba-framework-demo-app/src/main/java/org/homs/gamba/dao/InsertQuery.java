package org.homs.gamba.dao;

import org.homs.gamba.binding.BeanInfo;
import org.homs.gamba.binding.BeanPropInfo;
import org.homs.gamba.binding.CachedBeanAnalizer;

public class InsertQuery {

	// insert into t_actor (first_name, surname) values (?, ?)
	public void insert(final Object object) {
		final BeanInfo bi = CachedBeanAnalizer.getInstance().analize(object.getClass());

		final StringBuffer strb = new StringBuffer();

		strb.append("INSERT INTO ");
		strb.append(object.getClass().getSimpleName());

		strb.append(" (");

		for (final String key : bi.getBeanProps().keySet()) {
			final BeanPropInfo bpi = bi.getBeanProps().get(key);
			strb.append(bpi.propertyName.toUpperCase());
			strb.append(',');
		}

		strb.append(") VALUES (	");

		for (final String key : bi.getBeanProps().keySet()) {
			final BeanPropInfo bpi = bi.getBeanProps().get(key);

			// TODO falta que BeanPropInfo també guardi info dels getters, a més dels setters!!!!
//			strb.append(bpi.propertyName.toUpperCase());
//			strb.append(',');
		}

		strb.append(")");



		System.out.println(strb.toString().replaceAll(",\\)", ")"));
	}

}
