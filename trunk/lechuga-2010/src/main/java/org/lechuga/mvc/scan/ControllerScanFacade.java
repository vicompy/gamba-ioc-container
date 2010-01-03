package org.lechuga.mvc.scan;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gro.logging.GroLog;
import org.lechuga.mvc.scan.clas.ClassScanner;
import org.lechuga.mvc.scan.clas.filters.ControllerFilter;
import org.lechuga.mvc.scan.clas.filters.IClassFilter;
import org.lechuga.mvc.scan.method.MethodScanner;

public class ControllerScanFacade {

	final static GroLog LOG = GroLog.getGroLogger(ControllerScanFacade.class);

	/**
	 * llista a totes les classes contingudes en el package especificat; d'aquí
	 * n'extreu tots els mètodes que poden seguir la convenció de
	 * <tt>Controllers</tt>, encapsulant-ne la informació en un <tt>Map</tt> per
	 * a un posterior ús.
	 * <ul>
	 * <li>el nom simple de la classe (<tt>.getClass().getSimpleName()</tt>) ha
	 * d'acabar en "<tt>Controller</tt>", com per exemple "
	 * <tt>BookController</tt>".</li>
	 * <li>cadascun dels mètodes que conté la classe identificada com a
	 * <tt>Controller</tt> i que no estan presents en <tt>java.lang.Object</tt>
	 * serà considerat una acció invocable per petició HTTP.</li>
	 * </ul>
	 *
	 * <p>
	 * Veure que un mètode d'acció ha de tenir 2 ó 3 arguments, segons si
	 * s'especifica un FormBean. TODO això s'hauria de verificar ó es deixa
	 * lliure?
	 * </p>
	 *
	 * @param packageName
	 * @return
	 */
	public static Map<String, ClassMethod> getControllerMappingsMap(final String packageName) {

		final Map<String, ClassMethod> mappingsMap = new HashMap<String, ClassMethod>();
		final Class<?>[] cl = new ClassScanner().getClasses(packageName);
		final Map<Class<?>, List<Method>> methodsMap = new MethodScanner().nonObjectableMethods(cl);

		LOG.info("scanning for controllers in: ", packageName);
		final IClassFilter filter = new ControllerFilter();
		for (final Class<?> c : methodsMap.keySet()) {
			if (filter.mustBeAccepted(c)) {
				for (final Method m : methodsMap.get(c)) {

					final ClassMethod cm = new ClassMethod(c, m);
					mappingsMap.put(cm.getRequestPath(), cm);

					LOG.info("*  ", cm);
				}
			}
		}

		return mappingsMap;
	}

}
