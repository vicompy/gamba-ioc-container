package org.homs.gamba.stub;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.homs.gamba.stub.delegator.IDelegator;
import org.homs.gamba.stub.exception.GambaStubsException;
import org.homs.gamba.utils.Seq;

/**
 * @author mhoms
 */
public final class StubProxy implements InvocationHandler {

	private final List<CallActionConfig> callsConfig;
	private final List<CalledRegister> callsReport;
	private boolean proxyIsRecording;

	private StubProxy() {
		callsConfig = new ArrayList<CallActionConfig>();
		callsReport = new ArrayList<CalledRegister>();
		proxyIsRecording = true;
	}

	public static Object newInstance(final Class<?> stubableInterface) {
		if (stubableInterface.isInterface()) {
			return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[] {
					stubableInterface, IStubable.class }, new StubProxy());
		}

		// TODO si lo a stubbar no és una interfície, doncs és una classe, es
		// proxeja extraient-ne les seves interfícies implementades; cal testar
		// encara.
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), (Class<?>[]) Seq.seq(
				stubableInterface.getInterfaces(), IStubable.class), new StubProxy());

		// TODO falta testar encara què passa amb herència de classses, interfícies, etc.
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
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

		if (proxyIsRecording) {
			/*
			 * in recording state
			 */
			return recording(method, args);
		}
		/*
		 * in playing state
		 */
		return playing(method, args);

	}

	private Object playing(final Method method, final Object[] args) throws Throwable {

		if (method.getName().equals("obtainReport")) {
			return callsReport;
		}
		callsReport.add(new CalledRegister(method, args));

		// TODO cerca de la crida lenta?
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

		throw new GambaStubsException("method call not registered: " + method.getName() + "("
				+ Seq.enList(args).toString() + ")");
	}

	private Object recording(final Method method, final Object[] args) {

		// es prepara per a una nova crida a registrar (s'està en estat de
		// gravació)
		// i hi desa el delegator
		if (method.getName().equals("setDelegator")) {
			if (!(args[0] instanceof IDelegator)) {
				throw new GambaStubsException("this is not an IDelegator object \n"); // TODO
			}
			callsConfig.add(new CallActionConfig((IDelegator) args[0]));
			return null;
		}

		// atura el mode de gravació i passa al de reproducció
		if (method.getName().equals("stopRecording")) {
			proxyIsRecording = false;
			checkRegisteredCalls();
			return null;
		}

		// 2n pas: no s'ha cridat a cap mètode de IStubable, per tant és una
		// crida a la interfície proxejada
		// i s'ha de guardar la crida
		callsConfig.get(callsConfig.size() - 1).setCall(method, args);

		// System.out.println("registering: " + method.getName() +
		// "("+Seq.enList(args).toString() + ")");

		return computeRecordingReturn(method);
	}

	private Object computeRecordingReturn(final Method method) {
		final Class<?> type = method.getReturnType();

		if (type.isPrimitive()) {
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
		}
		return null;
	}

	/**
	 * verifica que no s'hagi registrat un delegator de retorn sense haver
	 * registrat la crida
	 */
	private void checkRegisteredCalls() {
		for (final CallActionConfig ce : callsConfig) {
			if (ce.getMethod() == null) {
				throw new GambaStubsException("method call partially defined found, with returning value: "
						+ ce.getDelegator());
			}
		}

		// TODO comprovar també que no hi hagin definicions de crides repetides
	}

}
