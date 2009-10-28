package org.homs.gamba.stub;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.homs.gamba.stub.delegator.IDelegator;
import org.homs.gamba.stub.exception.GambaStubsException;

/**
 * @author mhoms
 */
public class StubProxy implements InvocationHandler {

	private final List<CallActionConfig> callsConfig = new ArrayList<CallActionConfig>();
	private final List<CalledRegister> callsReport = new ArrayList<CalledRegister>();
	private boolean proxyIsRecording = true;

	public static Object newInstance(final Class<?> stubableInterface) {
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[] {
				stubableInterface, IStubable.class }, new StubProxy());
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

		if (proxyIsRecording) {
			/*
			 * in recording state
			 */
			return recording(method, args);
		} else {
			/*
			 * in playing state
			 */
			return playing(method, args);
		}
	}

	private Object playing(final Method method, final Object[] args) throws Throwable {

		if (method.getName().equals("obtainReport")) {
			return callsReport;
		}
		callsReport.add(new CalledRegister(method, args));

		for (final CallActionConfig ce : callsConfig) {
			if (ce.getMethod().equals(method)) {

				boolean argsOK = true;
				for (int i = 0; i < ce.getCallingArgsValues().length; i++) {

					if (!args[i].equals(ce.getCallingArgsValues()[i])) {
						argsOK = false;
						break;
					}
				}

				if (argsOK) {
					return ce.getDelegator().delegates(args);
				}
			}
		}

		throw new GambaStubsException("method call not registered: \n" + method.getName()); // TODO
	}

	private Object recording(final Method method, final Object[] args) {

		if (method.getName().equals("setDelegator")) {
			if (!(args[0] instanceof IDelegator)) {
				throw new GambaStubsException("this is not an IDelegator object \n"); // TODO
			}
			callsConfig.add(new CallActionConfig((IDelegator) args[0]));
			return null;
		}

		if (method.getName().equals("stopRecording")) {
			proxyIsRecording = false;
			checkRegisteredCalls();
			return null;
		}

		final CallActionConfig callConfig = callsConfig.get(callsConfig.size() - 1);
		callConfig.setCall(method, args);

		return computeRecordingReturn(method);
	}

	private Object computeRecordingReturn(final Method method) {
		final Class<?> type = method.getReturnType();

		if (type.equals(int.class)) {
			return Integer.valueOf(0);
		}
		if (type.equals(long.class)) {
			return Long.valueOf(0);
		}
		if (type.equals(float.class)) {
			return Float.valueOf(0);
		}
		if (type.equals(double.class)) {
			return Double.valueOf(0);
		}
		if (type.equals(boolean.class)) {
			return Boolean.FALSE;
		}
		if (type.equals(char.class)) {
			return Character.valueOf(' ');
		}
		return null;
	}

	private void checkRegisteredCalls() {
		for (final CallActionConfig ce : callsConfig) {
			if (ce.getMethod() == null) {
				throw new GambaStubsException("method call partially defined found, with returning value: "
						+ ce.getDelegator());
			}
		}
	}

}
