package org.gamba.mocks;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.gamba.mocks.exception.GambaMockException;
import org.gamba.mocks.fluent.Mask;
import org.gamba.mocks.sequences.ISequence;

/**
 * @author mhoms
 */
public final class MockProxy implements InvocationHandler {

	private static final String OBTAIN_CALL_CONFIG_PROXY_CALL = "obtainCallConfig";
	private static final String STOP_RECORDING_PROXY_CALL = "stopRecording";
	private static final String SET_SEQUENCE_PROXY_CALL = "setSequence";
	private static final String OBTAIN_CALL_REPORT_PROXY_CALL = "obtainCallingLog";

	private final MockProxyLogic logic = new MockProxyLogic();

	public static Object newInstance(final Class<?> mockableInterface) {
		if (mockableInterface.isInterface()) {
			return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[] {
					mockableInterface, IMockable.class }, new MockProxy());
		}

		throw new GambaMockException("cannot stub a class, just have to be interface(s)");
	}

	public static Object newInstance(final Class<?>... mockableInterfaces) {

		final Class<?>[] interfaces = new Class<?>[mockableInterfaces.length + 1];
		for (int i = 0; i < mockableInterfaces.length; i++) {
			if (!mockableInterfaces[i].isInterface()) {
				throw new GambaMockException("cannot stub a class, just have to be interface(s)");
			}
			interfaces[i] = mockableInterfaces[i];
		}
		interfaces[mockableInterfaces.length] = IMockable.class;

		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, new MockProxy());
	}

	/**
	 * contracte de crides a aquest proxy:
	 * <ul>
	 * <li>1. cridar a <tt>setReturnValue</tt>, amb el valor de retorn desitjat.
	 * </li>
	 * <li>2. fer la crida a l'ínterfície original i així registrar el
	 * <tt>CallingElement</tt>.</li>
	 * <li>3. repetir (2).</li>
	 * <li>4. cridar stopRecording.</li>
	 * </ul>
	 *
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

		if (method.getName().equals(OBTAIN_CALL_CONFIG_PROXY_CALL)) {
			return logic.obtainCallConfig();
		}
		if (method.getName().equals(OBTAIN_CALL_REPORT_PROXY_CALL)) {
			return logic.obtainCallingLog();
		}
		if (method.getName().equals("verify")) {
			logic.verify();
			return null;
		}
		// atura el mode de gravació i passa al de reproducció
		if (method.getName().equals(STOP_RECORDING_PROXY_CALL)) {
			logic.stopRecording();
			return null;
		}


		if (logic.isProxyIsRecording()) {
			// es prepara per a una nova crida a registrar (s'està en estat de
			// gravació)
			// i hi desa el delegator
			if (method.getName().equals(SET_SEQUENCE_PROXY_CALL)) {
				logic.setSequence(((ISequence) args[0]), ((Mask) args[1]));
				return null;
			}
		}

		return this.logic.invoke(method, args);
	}

}
