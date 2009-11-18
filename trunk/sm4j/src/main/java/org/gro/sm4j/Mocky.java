package org.gro.sm4j;

import java.util.List;

import org.gro.sm4j.mockproxy.IMockProxyLogic;
import org.gro.sm4j.mockproxy.MethodConfig;
import org.gro.sm4j.mockproxy.MockProxy;
import org.gro.sm4j.sequences.ExceptionSequence;
import org.gro.sm4j.sequences.ISequence;
import org.gro.sm4j.sequences.ObjectSequence;
import org.gro.sm4j.utils.Seq;

public final class Mocky implements IWhen {

	private static ISequence sequence;
	private static IMockProxyLogic proxy;

	/*******************************************************
	 * construcció del proxy
	 *******************************************************/

	@SuppressWarnings("unchecked")
	public static <T> T createMock(final Class<T> interfaceToMock) {
		return (T) MockProxy.newInstance(interfaceToMock);
	}

	public static Object createMock(final Class<?>... interfacesToMock) {
		return MockProxy.newInstance(interfacesToMock);
	}

	/*******************************************************
	 * fluent definitions
	 *******************************************************/
	private Mocky(final ISequence seq) {
		Mocky.sequence = seq;
	}

	public static IWhen thenReturnNull() {
		return thenReturn(null, 1);
	}

	public static IWhen thenReturnVoid() {
		return thenReturn(null, 1);
	}

	public static IWhen thenReturn(final Object object) {
		return thenReturn(object, 1);
	}

	public static IWhen thenReturn(final Object object, final int times) {
		return new Mocky(new ObjectSequence(Seq.rep(times, object)));
	}

	public static IWhen thenReturnSeq(final Object... objects) {
		return new Mocky(new ObjectSequence(objects));
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
		Mocky.proxy = (IMockProxyLogic) proxy;
		Mocky.proxy.setSeq(sequence); // obrint transacció
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

	// TODO retorns null arriscats: testat per NullTest.java?
	public static <T> T anyObject(final T value) {
		addMask(true);
		return null; // TODO  t?
	}

	public static Object anyObject() {
		addMask(true);
		return null; //TODO new Object();?
	}

	public static String anyString() {
		addMask(true);
		return null; // TODO ""?
	}

	public static <T> T eq(final T value) {
		addMask(false);
		return value;
	}

	/*******************************************************
	 * ???
	 *******************************************************/

	public static void replay(final Object proxy) {
		((IMockProxyLogic) proxy).replay();
	}

	public static void replay(final Object... proxies) {
		for (final Object proxy : proxies) {
			((IMockProxyLogic) proxy).replay();
		}
	}

	public static void verify(final Object proxy) {
		((IMockProxyLogic) proxy).verify();
	}

	public static void verify(final Object... proxies) {
		for (final Object proxy : proxies) {
			((IMockProxyLogic) proxy).verify();
		}
	}

	// public static CallingLog obtainCallingLog(final Object proxy) {
	// return ((IRecordingControl) proxy).obtainCallingLog();
	// }

	public static List<MethodConfig> obtainCallConfig(final Object proxy) {
		return ((IMockProxyLogic) proxy).getCallConfig();
	}

}
