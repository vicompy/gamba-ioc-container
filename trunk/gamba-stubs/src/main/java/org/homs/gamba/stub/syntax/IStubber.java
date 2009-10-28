package org.homs.gamba.stub.syntax;

import org.homs.gamba.stub.delegator.IDelegator;

public interface IStubber<T> {

	/**
	 * especifica un valor de retorn
	 *
	 * @param object valor de retorn
	 * @return el proxy
	 */
	IWhenSyntax<T> thenReturn(final Object object);

	IWhenSyntax<T> thenLoop(Object... objects);

	IWhenSyntax<T> thenSinglePass(Object... objects);

	IWhenSyntax<T> thenPingPongLoop(Object... objects);

	IWhenSyntax<T> thenThrows(final Throwable throwable);

	IWhenSyntax<T> thenDelegates(final IDelegator delegator);

	/**
	 * configura el proxy en mode simulació, deixant de registrar
	 *
	 * @return el proxy
	 */
	T play();

}