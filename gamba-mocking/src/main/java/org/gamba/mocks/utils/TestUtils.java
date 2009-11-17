package org.gamba.mocks.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.gamba.mocks.exception.GambaMockException;

/**
 * Utilitats estàtiques de testing
 * 
 * @author mhoms
 */
public final class TestUtils {

	private TestUtils() {
		// all members are static
	}

	/**
	 * invoca a un constructor per defecte privat, i en retorna una nova
	 * instància. A utilitzar especialment en testos unitaris de patrons
	 * Singleton.
	 * 
	 * @param <T>
	 *            tipus del Singleton
	 * @param singletonClass
	 *            classe de Singleton
	 * @return la nova instància
	 */
	public static Object newHackedInstance(final Class<?> singletonClass, final Object... args) {
		final Constructor<?> cons;
		final Class<?>[] argClasses = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++) {
			argClasses[i] = args[i].getClass();
		}
		try {
			cons = findConstructor(singletonClass, argClasses);
			cons.setAccessible(true);
			final Object instance = cons.newInstance(args);
			cons.setAccessible(false);
			return instance;
		} catch (final Exception e) {
			throw new GambaMockException("error hackejant una nova instància singletona", e);
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
	public static Object hackedMethodInvocation(final Object targetClass, final Method method,
			final Object... args) {
		try {
			method.setAccessible(true);
			final Object returnValue = method.invoke(targetClass, args);
			method.setAccessible(false);
			return returnValue;
		} catch (final Exception e) {
			throw new GambaMockException("error hackejant una invocació", e);
		}
	}

	/**
	 * Cerca el constructor adient, donada la llista dels tipus dels arguments
	 * 
	 * @param targetClass
	 *            classe a on buscar el constructor
	 * @param classArgsList
	 *            llista de tipus d'arguments de constructor
	 * @return el constructor;
	 * @throws GambaException
	 *             si constructor no trobat
	 */
	private static Constructor<?> findConstructor(final Class<?> targetClass, final Class<?>[] classArgsList)
			throws GambaMockException {
		final Constructor<?>[] constructors = targetClass.getDeclaredConstructors();

		Constructor<?> constructor = null;

		boolean founded = true;
		for (final Constructor<?> c : constructors) {
			if (c.getParameterTypes().length == classArgsList.length) {

				founded = true;
				for (int i = 0; i < classArgsList.length; i++) {
					if (!areAssignable(c.getParameterTypes()[i], classArgsList[i])) {
						founded = false;
						break;
					}
				}

				if (founded) {
					constructor = c;
					break;
				}
			}
		}

		if (!founded) {
			final StringBuffer strb = new StringBuffer("constructor not found: ");
			strb.append(targetClass);
			strb.append('(');
			for (final Class<?> param : classArgsList) {
				strb.append(param);
				strb.append(", ");
			}
			strb.append(')');
			throw new GambaMockException(strb.toString());
		}
		return constructor;
	}

	/**
	 * performa <tt>isAssignableFrom</tt>, convertint el tipus de classe destí;
	 * si interessa injectar a un primitiu <tt>int</tt> un <tt>Integer</tt> és
	 * correcte, però <tt>isAssignableFrom</tt> retornarà <tt>false</tt>. Així
	 * doncs aquest mètode aplica la corresponent conversió de tipus primitius a
	 * classe Wrapper primitiva, per tal re reconsiderar
	 * <tt>isAssignableFrom</tt>.
	 * 
	 * @param dst
	 *            tipus destí de l'assignació
	 * @param org
	 *            tipus origen de l'assignació
	 * @return si tal assignació és possible
	 */
	private static boolean areAssignable(final Class<?> dst, final Class<?> org) {
		Class<?> dst2 = dst;
		if (dst.isPrimitive()) {
			if (dst.equals(int.class)) {
				dst2 = Integer.class;
			} else if (dst.equals(long.class)) {
				dst2 = Long.class;
			} else if (dst.equals(float.class)) {
				dst2 = Float.class;
			} else if (dst.equals(double.class)) {
				dst2 = Double.class;
			} else if (dst.equals(boolean.class)) {
				dst2 = Boolean.class;
			} else if (dst.equals(char.class)) {
				dst2 = Character.class;
			} else if (dst.equals(byte.class)) {
				dst2 = Byte.class;
				// } else if (dst.equals(short.class)) {
				// dst2 = Short.class;
			}
		}

		return dst2.isAssignableFrom(org);
	}

}
