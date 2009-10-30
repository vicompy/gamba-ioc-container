package org.homs.gamba.stub.syntax2;

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

public final class Stubber implements IWhen {

	private final IDelegator delegator;

	private Stubber(final IDelegator delegator) {
		this.delegator = delegator;
	}

	public static Object createStub(final Class<?> interfaceToStub) {
		return StubProxy.newInstance(interfaceToStub);
	}

	public static IWhen willReturn(final Object obj) {
		return new Stubber(new ConstantValue(obj));
	}

	public static IWhen willLoop(final Object... objs) {
		return new Stubber(new CyclicSequence(objs));
	}

	public static IWhen willSinglePass(final Object... objs) {
		return new Stubber(new OnePassSequence(objs));
	}

	public static IWhen willPingPongLoop(final Object... objs) {
		return new Stubber(new PingPongSequence(objs));
	}

	public static IWhen willThrow(final Throwable throwable) {
		return new Stubber(new ExceptionTrigger(throwable));
	}

	public static IWhen willDelegate(final IDelegator delegator) {
		return new Stubber(delegator);
	}

	public <T> T when(final T proxy) {
		((IStubable) proxy).setDelegator(delegator);
		return proxy;
	}

	// --------
	public static void play(final Object proxy) {
		((IStubable) proxy).stopRecording();
	}

	public static List<CalledRegister> obtainReport(final Object proxy) {
		return ((IStubable) proxy).obtainReport();
	}

}
