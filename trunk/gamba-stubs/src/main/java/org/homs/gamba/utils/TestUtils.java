package org.homs.gamba.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.homs.gamba.stub.exception.GambaStubsException;

/**
 * Utilitats estàtiques de testing
 *
 * @author mhoms
 */
public class TestUtils {

	/**
	 * invoca a un constructor per defecte privat, i en retorna una nova
	 * instància. A utilitzar especialment en testos unitaris de patrons
	 * Singleton.
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
			throw new GambaStubsException("error hackejant una nova instància singletona", e);
		}
	}

	/**
	 * invoca a un mètode privat
	 *
	 * @param targetClass
	 * @param method
	 * @param args
	 * @return
	 */
	public static Object hackedMethodInvocation(final Object targetClass, final Method method, final Object... args) {
		try {
			method.setAccessible(true);
			final Object returnValue = method.invoke(targetClass, args);
			method.setAccessible(false);
			return returnValue;
		} catch (final Exception e) {
			throw new GambaStubsException("error hackejant una invocació", e);
		}
	}

}
