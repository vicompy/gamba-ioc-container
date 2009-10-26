package org.homs.gamba.stub.syntax;

import org.homs.gamba.stub.IDelegator;

public interface IStubber<T> {

	/**
	 * especifica un valor de retorn
	 *
	 * @param r valor de retorn
	 * @return el proxy
	 */
	public abstract IWhenSyntax<T> doReturn(final Object r);

	public abstract IWhenSyntax<T> doThrow(final Throwable t);

	public abstract IWhenSyntax<T> doDelegate(final IDelegator delegator);

	/**
	 * configura el proxy en mode simulació, deixant de registrar
	 *
	 * @return el proxy
	 */
	public abstract T play();

}