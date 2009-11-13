package org.homs.gamba.stub.any;

public class Any {

	// Any.any(Integer.class)
	public static <T> T any(final Class<T> type) {
		// retornar un proxy que implementi T i una interface més que el
		// distingeixi, així l'StubProxy ho interpretarà com a
		// "any value"-comodín.
		return (T) AnyProxy.newInstance(type);
	}
}
