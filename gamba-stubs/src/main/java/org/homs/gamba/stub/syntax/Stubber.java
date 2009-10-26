package org.homs.gamba.stub.syntax;

import java.util.List;

import org.homs.gamba.stub.CallingReport;
import org.homs.gamba.stub.IDelegator;
import org.homs.gamba.stub.IStubable;
import org.homs.gamba.stub.StubProxy;

public class Stubber<T> implements IStubber<T>, IWhenSyntax<T> {

	private final T proxiedStub;

	private Stubber(final T proxiedStub) {
		this.proxiedStub = proxiedStub;
	}

	/**
	 * donada la Class d'una interfície, en retorna la instància d'un fals stub
	 * (això és, un proxy)
	 *
	 * @param interfaceToStub Class<T> d'interfície a stubejar
	 * @return la instància del fals proxy
	 */
	@SuppressWarnings("unchecked")
	public static <T> IStubber<T> createStub(final Class<T> interfaceToStub) {
		return new Stubber(StubProxy.newInstance(interfaceToStub));
	}

	public static <T> List<CallingReport> obtainReport(final T proxiedStub) {
		return ((IStubable) proxiedStub).obtainReport();
	}

	/**
	 * @see org.homs.gamba.stub.IStub#doReturn(java.lang.Object)
	 */
	public IWhenSyntax<T> doReturn(final Object r) {
		((IStubable) proxiedStub).setReturnValue(r);
		return this;
	}

	/**
	 * @see org.homs.gamba.stub.IStub#doThrow(java.lang.Throwable)
	 */
	public IWhenSyntax<T> doThrow(final Throwable t) {
		((IStubable) proxiedStub).setThrowing(t);
		return this;
	}

	/**
	 * @see org.homs.gamba.stub.IStub#doDelegate(org.homs.gamba.stub.IDelegator)
	 */
	public IWhenSyntax<T> doDelegate(final IDelegator delegator) {
		((IStubable) proxiedStub).setDelegator(delegator);
		return this;
	}

	/**
	 * @see org.homs.gamba.stub.IStub#play()
	 */
	public T play() {
		((IStubable) proxiedStub).stopRecording();
		return proxiedStub;
	}

	/**
	 * @see org.homs.gamba.stub.syntax.IWhenSyntax#when()
	 */
	public T when() {
		return proxiedStub;
	}

}
