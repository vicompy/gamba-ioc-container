package org.homs.gamba.stub.syntax;

import java.util.List;

import org.homs.gamba.stub.CalledRegister;
import org.homs.gamba.stub.IStubable;
import org.homs.gamba.stub.StubProxy;
import org.homs.gamba.stub.delegator.ConstantValue;
import org.homs.gamba.stub.delegator.CyclicSequence;
import org.homs.gamba.stub.delegator.ExceptionTrigger;
import org.homs.gamba.stub.delegator.IDelegator;
import org.homs.gamba.stub.delegator.OnePassSequence;
import org.homs.gamba.stub.delegator.PingPongSequence;

public final class Stubber<T> implements IStubber<T>, IWhenSyntax<T> {

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

	/**
	 * @see org.homs.gamba.stub.IStub#willReturn(java.lang.Object)
	 */
	public IWhenSyntax<T> willReturn(final Object object) {
		((IStubable) proxiedStub).setDelegator(new ConstantValue(object));
		return this;
	}

	public IWhenSyntax<T> loop(final Object... objects) {
		((IStubable) proxiedStub).setDelegator(new CyclicSequence(objects));
		return this;
	}

	public IWhenSyntax<T> singlePass(final Object... objects) {
		((IStubable) proxiedStub).setDelegator(new OnePassSequence(objects));
		return this;
	}

	public IWhenSyntax<T> pingPongLoop(final Object... objects) {
		((IStubable) proxiedStub).setDelegator(new PingPongSequence(objects));
		return this;
	}

	/**
	 * @see org.homs.gamba.stub.IStub#willThrows(java.lang.Throwable)
	 */
	public IWhenSyntax<T> willThrows(final Throwable throwable) {
		((IStubable) proxiedStub).setDelegator(new ExceptionTrigger(throwable));
		return this;
	}

	/**
	 * @see org.homs.gamba.stub.IStub#willDelegates(org.homs.gamba.stub.delegator.IDelegator)
	 */
	public IWhenSyntax<T> willDelegates(final IDelegator delegator) {
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

	public static <T> List<CalledRegister> obtainReport(final T proxiedStub) {
		return ((IStubable) proxiedStub).obtainReport();
	}

}
