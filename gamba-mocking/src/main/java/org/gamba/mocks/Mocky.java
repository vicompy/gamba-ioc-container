package org.gamba.mocks;

import java.util.List;

import org.gamba.mocks.recordingproxy.IRecordingControl;
import org.gamba.mocks.recordingproxy.MethodConfig;
import org.gamba.mocks.recordingproxy.MockProxy;
import org.gamba.mocks.sequences.ExceptionSequence;
import org.gamba.mocks.sequences.ISequence;
import org.gamba.mocks.sequences.ObjectSequence;
import org.gamba.mocks.utils.Seq;

public final class Mocky implements IWhen {

	private static ISequence seq;
	private static IRecordingControl proxy;

	@SuppressWarnings("unchecked")
	public static <T> T createMock(final Class<T> interfaceToMock) {
		return (T) MockProxy.newInstance(interfaceToMock);
	}

	@SuppressWarnings("unchecked")
	public static <T> T createMock(final Class<T>... interfacesToMock) {
		return (T) MockProxy.newInstance(interfacesToMock);
	}

	/*******************************************************
	 * fluent begin
	 *******************************************************/
	private Mocky(final ISequence seq) {
		Mocky.seq = seq;
	}

	public static IWhen thenReturnNull() {
		return thenReturnSeq(null, 1);
	}

	public static IWhen thenReturnVoid() {
		return thenReturnSeq(null, 1);
	}

	public static IWhen thenReturn(final Object o) {
		return thenReturnSeq(o, 1);
	}

	public static IWhen thenReturnSeq(final Object o, final int times) {
		return new Mocky(new ObjectSequence(Seq.rep(times, o)));
	}

	public static IWhen thenReturnSeq(final Object... os) {
		return new Mocky(new ObjectSequence(os));
	}

	public static IWhen thenThrow(final Throwable throwable) {
		return new Mocky(new ExceptionSequence(throwable));
	}

	public static IWhen thenThrow(final Throwable throwable, final int times) {
		return new Mocky(new ExceptionSequence(Seq.rep(times, throwable)));
	}

	public static IWhen thenThrowList(final Throwable... throwables) {
		return new Mocky(new ExceptionSequence((Object[]) throwables));
	}

	// mantingut com a extension point
	public static IWhen thenDelegate(final ISequence sequence) {
		return new Mocky(sequence);
	}

	public <T> T when(final T proxy) {
		Mocky.proxy = (IRecordingControl) proxy;
		Mocky.proxy.setSeq(seq); // obrint transacció
		return proxy;
	}

	/*******************************************************
	 * màscares
	 *******************************************************/

	private static void addMask(final Boolean value) {
		proxy.addMaskValue(value);
	}

	public static int anyInt() {
		addMask(true);
		return 0;
	}

	public static long anyLong() {
		addMask(true);
		return 0L;
	}

	public static float anyFloat() {
		addMask(true);
		return 0f;
	}

	public static double anyDouble() {
		addMask(true);
		return 0.0;
	}

	public static short anyShort() {
		addMask(true);
		return 0;
	}

	public static boolean anyBoolean() {
		addMask(true);
		return true;
	}

	public static <T> T anyObject(final T t) {
		addMask(true);
		return t;
	}

	public static <T> T eq(final T t) {
		addMask(false);
		return t;
	}

	/*******************************************************
	 * ???
	 *******************************************************/

	public static void replay(final Object proxy) {
		((IRecordingControl) proxy).replay();
	}

	public static void replay(final Object... proxies) {
		for (final Object proxy : proxies) {
			((IRecordingControl) proxy).replay();
		}
	}

//	public static void verify(final Object proxy) {
//		((IRecordingControl) proxy).verify();
//	}
//
//	public static CallingLog obtainCallingLog(final Object proxy) {
//		return ((IRecordingControl) proxy).obtainCallingLog();
//	}

	public static List<MethodConfig> obtainCallConfig(final Object proxy) {
		return ((IRecordingControl) proxy).getCallConfig();
	}

}
