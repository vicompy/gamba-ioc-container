package org.homs.gamba.utils;

import java.lang.reflect.Constructor;

import org.homs.gamba.mock.exception.GambaMockException;

public class MockUtils {

	public static <T> T newHackedInstance(final Class<T> singletonClass) {
		Constructor<T> cons;
		try {
			cons = singletonClass.getDeclaredConstructor();
			cons.setAccessible(true);
			final T instance = cons.newInstance();
			cons.setAccessible(false);
			return instance;
		} catch (final Exception e) {
			throw new GambaMockException("error hackejant una nova instància singletona de Logger", e);
		}
	}

}
