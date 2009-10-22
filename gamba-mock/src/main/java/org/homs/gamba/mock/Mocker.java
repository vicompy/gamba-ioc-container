package org.homs.gamba.mock;

import org.homs.gamba.mock.proxy.MockProxy;

public class Mocker {

	/**
	 * donada la Class d'una interfície, en retorna la instància d'un fals proxy
	 *
	 * @param interfaceToMock Class<T> d'interfície a mockejar
	 * @return la instància del fals proxy
	 */
	@SuppressWarnings("unchecked")
	public static <T> Mock<T> createMock(final Class<T> interfaceToMock) {
		return new Mock(MockProxy.newInstance(interfaceToMock));
	}
}
