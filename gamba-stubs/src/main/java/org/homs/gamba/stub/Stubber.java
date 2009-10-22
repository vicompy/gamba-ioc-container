package org.homs.gamba.stub;

/**
 * entitat que permet la declaració fluent de registre de crides.
 *
 * @author mhoms
 */
public class Stubber {

	/**
	 * donada la Class d'una interfície, en retorna la instància d'un fals stub
	 * (això és, un proxy)
	 *
	 * @param interfaceToStub Class<T> d'interfície a stubejar
	 * @return la instància del fals proxy
	 */
	@SuppressWarnings("unchecked")
	public static <T> Stub<T> createStub(final Class<T> interfaceToStub) {
		return new Stub(StubProxy.newInstance(interfaceToStub));
	}
}
