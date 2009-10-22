package org.homs.gamba.mock;

/**
 * entitat que permet la declaració fluent de registre de crides.
 *
 * @author mhoms
 */
public class Mocker {

	/**
	 * donada la Class d'una interfície, en retorna la instància d'un fals mock
	 * (això és, un proxy)
	 *
	 * @param interfaceToMock Class<T> d'interfície a mockejar
	 * @return la instància del fals proxy
	 */
	@SuppressWarnings("unchecked")
	public static <T> Mock<T> createMock(final Class<T> interfaceToMock) {
		return new Mock(MockProxy.newInstance(interfaceToMock));
	}
}
