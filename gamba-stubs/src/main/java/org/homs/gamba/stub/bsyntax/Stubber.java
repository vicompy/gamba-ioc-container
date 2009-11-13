package org.homs.gamba.stub.bsyntax;

import java.util.List;

import org.homs.gamba.stub.CallLogEntry;
import org.homs.gamba.stub.IStubable;
import org.homs.gamba.stub.StubProxy;
import org.homs.gamba.stub.delegator.CheckedSinglePassSequence;
import org.homs.gamba.stub.delegator.ConstantValue;
import org.homs.gamba.stub.delegator.CyclicSequence;
import org.homs.gamba.stub.delegator.ExceptionTrigger;
import org.homs.gamba.stub.delegator.IDelegator;
import org.homs.gamba.stub.delegator.PingPongSequence;
import org.homs.gamba.stub.delegator.SinglePassSequence;

public final class Stubber implements IWhen {

	private final IDelegator delegator;

	private Stubber(final IDelegator delegator) {
		this.delegator = delegator;
	}

	public static Object createStub(final Class<?> interfaceToStub) {
		return StubProxy.newInstance(interfaceToStub);
	}

	public static Object createStub(final Class<?>... interfacesToStub) {
		return StubProxy.newInstance(interfacesToStub);
	}

	public static IWhen thenReturn(final Object obj) {
		return new Stubber(new ConstantValue(obj));
	}

	public static IWhen thenReturn(final Object... objs) {
		return new Stubber(new CheckedSinglePassSequence(objs));
	}

	public static IWhen thenUncheckedReturn(final Object... objs) {
		return new Stubber(new SinglePassSequence(objs));
	}

	public static IWhen thenLoop(final Object... objs) {
		return new Stubber(new CyclicSequence(objs));
	}

	public static IWhen thenPingPongLoop(final Object... objs) {
		return new Stubber(new PingPongSequence(objs));
	}

	public static IWhen thenThrow(final Throwable throwable) {
		return new Stubber(new ExceptionTrigger(throwable));
	}

	public static IWhen thenDelegate(final IDelegator delegator) {
		return new Stubber(delegator);
	}

	public <T> T when(final T proxy) {
		((IStubable) proxy).setDelegator(delegator);
		return proxy;
	}

	public static void play(final Object proxy) {
		((IStubable) proxy).stopRecording();
	}

	public static List<CallLogEntry> obtainCallReport(final Object proxy) {
		return ((IStubable) proxy).obtainCallReport();
	}

//	public static Integer anyInt() {
//		return
//	} TODO




}
