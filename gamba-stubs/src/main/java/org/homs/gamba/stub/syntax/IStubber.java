package org.homs.gamba.stub.syntax;

import org.homs.gamba.stub.delegator.IDelegator;

public interface IStubber<T> {

	/**
	 * especifica un valor de retorn
	 *
	 * @param object valor de retorn
	 * @return el proxy
	 */
	IWhenSyntax<T> doReturn(final Object object);

	IWhenSyntax<T> doLoop(Object... objects);

	IWhenSyntax<T> doSinglePass(Object... objects);

	IWhenSyntax<T> doPingPongLoop(Object... objects);

	IWhenSyntax<T> doThrow(final Throwable throwable);

	IWhenSyntax<T> doDelegate(final IDelegator delegator);

	/**
	 * configura el proxy en mode simulació, deixant de registrar
	 *
	 * @return el proxy
	 */
	T play();

}