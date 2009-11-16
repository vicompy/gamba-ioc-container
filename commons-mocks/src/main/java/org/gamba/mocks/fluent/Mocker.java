package org.gamba.mocks.fluent;

import org.gamba.mocks.CallingLog;
import org.gamba.mocks.IMockable;
import org.gamba.mocks.MockProxy;
import org.gamba.mocks.sequences.ExceptionSequence;
import org.gamba.mocks.sequences.ISequence;
import org.gamba.mocks.sequences.ObjectSequence;
import org.gamba.mocks.utils.Seq;

public final class Mocker implements IWhen {

	private final ISequence sequence;

	private Mocker(final ISequence sequence) {
		this.sequence = sequence;
	}

	@SuppressWarnings("unchecked")
	public static <T> T createMock(final Class<T> interfaceToMock) {
		return (T) MockProxy.newInstance(interfaceToMock);
	}

	public static Object createMock(final Class<?>... interfacesToMock) {
		return MockProxy.newInstance(interfacesToMock);
	}

	// TODO testar retorns void!!
	public static IWhen thenReturnVoid() {
		return thenReturn(null, 1);
	}
	// TODO es fa igual retornar null que void??
	public static IWhen thenReturnNull() {
		return thenReturn(null, 1);
	}

	public static IWhen thenReturn(final Object obj) {
		return thenReturn(obj, 1);
	}
	public static IWhen thenReturn(final Object obj, final int times) {
		return new Mocker(new ObjectSequence(Seq.rep(times, obj)));
	}
	public static IWhen thenReturnList(final Object... objs) {
		return new Mocker(new ObjectSequence(objs));
	}

	public static IWhen thenThrow(final Throwable throwable) {
		return new Mocker(new ExceptionSequence(throwable));
	}
	public static IWhen thenThrow(final Throwable throwable, final int times) {
		return new Mocker(new ExceptionSequence(Seq.rep(times, throwable)));
	}
	public static IWhen thenThrowList(final Throwable... throwables) {
		return new Mocker(new ExceptionSequence((Object[]) throwables));
	}

	// TODO mantenir com a extension point?
	public static IWhen thenDelegate(final ISequence sequence) {
		return new Mocker(sequence);
	}

	public <T> T when(final T proxy, final Mask mask) {
		((IMockable) proxy).setSequence(sequence, mask);
		return proxy;
	}

	public <T> T when(final T proxy) {
		((IMockable) proxy).setSequence(sequence, Mask.mask());
		return proxy;
	}

	public static void replay(final Object proxy) {
		((IMockable) proxy).stopRecording();
	}

	public static void verify(final Object proxy) {
		((IMockable) proxy).verify();
	}

	public static CallingLog obtainCallingLog(final Object proxy) {
		return ((IMockable) proxy).obtainCallingLog();
	}

	public static String obtainCallConfig(final Object proxy) {
		return ((IMockable) proxy).obtainCallConfig();
	}

	public static Mask maskBy(final boolean... booleanMask) {
		return Mask.mask(booleanMask);
	}

	public static Mask maskBy(final String mask) {
		final boolean[] bmask = new boolean[mask.length()];
		for (int i = 0; i < mask.length(); i++) {
			if (mask.charAt(i) == '*') {
				bmask[i] = true;
			} else {
				bmask[i] = false;
			}
		}
		return Mask.mask(bmask);
	}

}
