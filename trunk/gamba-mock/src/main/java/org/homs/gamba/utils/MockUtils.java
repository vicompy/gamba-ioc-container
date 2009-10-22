package org.homs.gamba.utils;

import java.lang.reflect.Constructor;

import org.homs.gamba.mock.exception.GambaMockException;

/**
 * Utilitats estàtiques de testing
 *
 * @author mhoms
 */
public class MockUtils {

	/**
	 * retorna una nova instància d'una classe amb constructor per defecte
	 * privat, a utilitzar especialment en testos unitaris de patrons Singleton.
	 *
	 * @param <T> tipus del Singleton
	 * @param singletonClass classe de Singleton
	 * @return la nova instància
	 */
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
