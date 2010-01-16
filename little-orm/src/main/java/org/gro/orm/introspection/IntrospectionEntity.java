package org.gro.orm.introspection;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.gro.orm.exception.GroOrmException;

/**
 * les propietats són públicament accessibles, però inmutables. (veure
 * {@link Collections#unmodifiableMap}).
 *
 * @author mhoms
 */
public class IntrospectionEntity {

	public final Class<?> entityType;
	public final Set<String> properties;
	public final Map<String, Class<?>> types;
	public final Map<String, Method> getters;
	public final Map<String, Method> setters;

	public IntrospectionEntity(final Class<?> entityType, final Set<String> properties,
			final Map<String, Class<?>> types, final Map<String, Method> getters,
			final Map<String, Method> setters) {
		super();
		this.entityType = entityType;
		this.properties = Collections.unmodifiableSet(properties);
		this.types = Collections.unmodifiableMap(types);
		this.getters = Collections.unmodifiableMap(getters);
		this.setters = Collections.unmodifiableMap(setters);
	}

	public void set(final Object entity, final String propertyName, final Object value) {

		final Method setterMethod = setters.get(propertyName);
		if (setterMethod == null) {
			throw new GroOrmException("propietat no trobada: " + entity.getClass().getName() + "."
					+ propertyName);
		}

		try {
			setterMethod.invoke(entity, value);
		} catch (final Exception exc) {
			throw new GroOrmException("error invocant setter: " + entity.getClass().getName() + "."
					+ propertyName, exc);
		}
	}

	// public Object get(final Object entity, final String propertyName) {
	//
	// final Method getterMethod = getters.get(propertyName);
	// if (getterMethod == null) {
	// throw new GroOrmException("property not found: " +
	// entity.getClass().getName() + "."
	// + propertyName);
	// }
	//
	// try {
	// return getterMethod.invoke(entity);
	// } catch (final Exception exc) {
	// throw new GroOrmException("error invoking setter method: " +
	// entity.getClass().getName() + "."
	// + propertyName, exc);
	// }
	// }
	//
	// @Override
	// public String toString() {
	// return "[" +
	// "\nentityType=" + entityType +
	// "\nproperties=" + properties +
	// "\ntypes=" + types +
	// "\ngetters=" + getters +
	// "\nsetters=" + setters +
	// "\n]";
	// }

}
