package org.homs.gamba.container.context;

import java.util.HashMap;
import java.util.Map;

import org.homs.gamba.container.exception.GambaException;

/**
 * Singleton que cacheja instàncies de contexts, per a una fàcil obtenció. Al
 * ser una Factory Singleton de Contextos, els beans definits en aquests com a
 * singleton, realment ho seràn. En demanar un context per primera vegada,
 * aquest és desat en hash, conservant-lo per a la futura vegada que es demani.
 *
 * @author mhoms
 * @since Sept-2009
 */
public final class GambaContainer {

	/**
	 * map de contextos
	 */
	private final Map<String, GambaContext> contexts;

	private GambaContainer() {
		contexts = new HashMap<String, GambaContext>();
	}

	private static class SingletonHolder {
		private static final GambaContainer INSTANCE = new GambaContainer();
	}

	public static GambaContainer getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * obté (creant, ó bé de hash) un context. Si és nou, enhasha.
	 *
	 * @param propertiesFilename
	 * @return
	 * @throws GambaException si no es pot obrir el fitxer de propietats
	 */
	private GambaContext obtainContext(final String propertiesFilename) throws GambaException {

		GambaContext context = contexts.get(propertiesFilename);
		if (context == null) {
			context = new GambaContext(propertiesFilename);
			contexts.put(propertiesFilename, context);
		}
		return context;
	}

	/**
	 * obtain the requested context, described in a properties file location
	 * <tt>propertiesFilename</tt>. The resulting context is registered in a
	 * hash, to improve performance.
	 *
	 * @param propertiesFilename properties file location path
	 * @return the requested context structure
	 * @throws GambaException if error ocurred loading the properties file
	 */
	public static GambaContext getContext(final String propertiesFilename) throws GambaException {
		return GambaContainer.getInstance().obtainContext(propertiesFilename);
	}

	public static String toStringStatic() {
		return getInstance().toString();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer();
		for (final String contextId : contexts.keySet()) {
			strb.append("context id: ");
			strb.append(contextId);
			strb.append('\n');
			strb.append(contexts.get(contextId));
			strb.append('\n');
		}
		return strb.toString();
	}

}
