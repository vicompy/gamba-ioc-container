package org.gro.orm.introspection;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.gro.orm.exception.GroOrmException;

public final class IntrospectionBuilder {

	private IntrospectionBuilder() {

	}

	public static IntrospectionEntity inspect(final Class<?> entity) {

		final Set<String> properties;
		final Map<String, Class<?>> types = new HashMap<String, Class<?>>();
		final Map<String, Method> getters = new HashMap<String, Method>();
		final Map<String, Method> setters = new HashMap<String, Method>();

		for (final Method m : entity.getMethods()) {

			if (isSetterMethod(m)) {
				setters.put(propertyOf(m), m);
			} else {
				if (isGetterMethod(m)) {
					getters.put(propertyOf(m), m);
				}
			}
		}

		if (getters.size() != setters.size()) {
			throw new GroOrmException("getters and setters not bounds in entity: " + entity.getName());
		}

		properties = getters.keySet();

		for (final String property : properties) {
			types.put(property, getters.get(property).getReturnType());
		}

		return new IntrospectionEntity(entity, properties, types, getters, setters);
	}

	private static boolean isSetterMethod(final Method m) {
		return m.getParameterTypes().length == 1 && m.getReturnType().equals(void.class)
				&& m.getName().length() > 3 && m.getName().startsWith("set");
	}

	private static boolean isGetterMethod(final Method m) {
		return m.getParameterTypes().length == 0 && !m.getReturnType().equals(Void.class)
				&& m.getName().length() > 3 && m.getName().startsWith("get")
				&& !m.getName().equals("getClass");
	}

	private static String propertyOf(final Method m) {
		return Character.toLowerCase(m.getName().charAt(3)) + m.getName().substring(4);
	}

}
