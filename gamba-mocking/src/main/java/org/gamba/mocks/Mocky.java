package org.gamba.mocks;

import org.gamba.mocks.recordingproxy.IRecordingControl;
import org.gamba.mocks.recordingproxy.MockProxy;
import org.gamba.mocks.sequences.AbstractSequence;
import org.gamba.mocks.sequences.ObjectSequence;
import org.gamba.mocks.utils.Seq;

public final class Mocky implements IWhen {

	private static AbstractSequence seq;
	private static IRecordingControl proxy;

	@SuppressWarnings("unchecked")
	public static <T> T createMock(final Class<T> interfaceToMock) {
		return (T) MockProxy.newInstance(interfaceToMock);
	}

	@SuppressWarnings("unchecked")
	public static <T> T createMock(final Class<T>...interfacesToMock) {
		return (T) MockProxy.newInstance(interfacesToMock);
	}

	/*******************************************************
	 * fluent begin
	 *******************************************************/
	private Mocky(final AbstractSequence seq) {
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

}
