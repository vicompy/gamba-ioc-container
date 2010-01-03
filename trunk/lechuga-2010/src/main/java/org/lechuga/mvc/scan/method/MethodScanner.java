package org.lechuga.mvc.scan.method;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodScanner {

	private final List<Method> objectMethods;

	public MethodScanner() {
		objectMethods = listMethods(Object.class);
	}

	/**
	 * @return la llista de {@link Method}s que conté un
	 *         {@link java.lang.Object}.
	 */
	private final List<Method> listMethods(final Class<?> claz) {
		return new ArrayList<Method>(Arrays.asList(claz.getMethods()));
	}

	/**
	 * llista els mètodes publics d'una classe que no estàn declarats en
	 * {@link java.lang.Object}
	 *
	 * @param claz
	 * @return
	 */
	public List<Method> nonObjectableMethods(final Class<?> claz) {
		final List<Method> cms = listMethods(claz);
		cms.removeAll(objectMethods);
		return cms;
	}

	/**
	 * retorna un <tt>Map</tt> de classes amb els seus respectius mètodes
	 * publics tals que no estàn declarats en {@link java.lang.Object}
	 *
	 * @param claz
	 * @return
	 */
	public Map<Class<?>, List<Method>> nonObjectableMethods(final Class<?>[] claz) {

		final Map<Class<?>, List<Method>> r = new HashMap<Class<?>, List<Method>>();

		for (final Class<?> c : claz) {
			r.put(c, nonObjectableMethods(c));
		}
		return r;
	}
	// TODO no determinar els mètodes candidats mirant que no estiguin en
	// object; n'hia ha prou que un mètode candidat tingui 2-3 arguments, i que
	// siguin httpRequest, httpResponse, etc.

}
