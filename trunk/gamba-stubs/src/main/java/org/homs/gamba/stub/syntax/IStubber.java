package org.homs.gamba.stub.syntax;

import org.homs.gamba.stub.delegator.IDelegator;

public interface IStubber<T> {

	/**
	 * especifica un valor de retorn
	 *
	 * @param object valor de retorn
	 * @return el proxy
	 */
	IWhenSyntax<T> willReturn(final Object object);

	IWhenSyntax<T> loop(Object... objects);

	IWhenSyntax<T> singlePass(Object... objects);

	IWhenSyntax<T> pingPongLoop(Object... objects);

	IWhenSyntax<T> willThrows(final Throwable throwable);

	IWhenSyntax<T> willDelegates(final IDelegator delegator);

	/**
	 * configura el proxy en mode simulació, deixant de registrar
	 *
	 * @return el proxy
	 */
	T play();

}