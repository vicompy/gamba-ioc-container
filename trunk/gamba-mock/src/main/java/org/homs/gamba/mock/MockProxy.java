package org.homs.gamba.mock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.homs.gamba.logging.Log;
import org.homs.gamba.mock.exception.GambaMockException;

/**
 *
 * @author mhoms
 */
class MockProxy implements InvocationHandler {

	private final List<CallingElement> cel = new ArrayList<CallingElement>();
	private boolean recording = true;
	private static final Log log = new Log(MockProxy.class);

	public static Object newInstance(final Class<?> mockableInterface) {
		return Proxy.newProxyInstance(mockableInterface.getClassLoader(), new Class<?>[] { mockableInterface,
				IMockeable.class }, new MockProxy());
	}

	/**
	 * contracte de crides a aquest proxy:
	 *
	 * 1. cridar a setReturnValue, amb el valor de retorn desitjat. 2. fer la
	 * crida a l'ínterfície originar i així registrar el
	 * <tt>CallingElement</tt>. 3. repetir (2). 4. cridar stopRecording
	 *
	 *
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

		if (recording) {
			/*
			 * in recording state
			 */
			if (method.getName().equals("setReturnValue")) {
				cel.add(new CallingElement(args[0]));
				return null;
			}
			if (method.getName().equals("stopRecording")) {
				recording = false;
				checkRegisteredCalls();
				return null;
			}

			final CallingElement ce = cel.get(cel.size() - 1);
			ce.setMethod(method);
			ce.setCallingArgsValues(args);

			log.debug("registered call: " + cel.get(cel.size() - 1));
			return null;
		} else {
			/*
			 * in playing state
			 */
			for (final CallingElement ce : cel) {
				if (ce.getMethod().equals(method)) {

					boolean argsOK = true;
					for (int i = 0; i < ce.getCallingArgsValues().length; i++) {

						if (!args[i].equals(ce.getCallingArgsValues()[i])) {
							argsOK = false;
							break;
						}
					}

					if (argsOK) {
						log.debug("calling: " + ce.toString());
						return ce.getReturningObject();
					}
				}
			}

			final RuntimeException e = new GambaMockException("method call not registered: \n"); // TODO
			log.error(renderException(e));
			throw e;
		}
	}

	private String renderException(final Exception e) {
		final StringBuffer strb = new StringBuffer();
		strb.append(e.toString());
		for (final StackTraceElement ste : e.getStackTrace()) {
			strb.append(ste.toString());
			strb.append('\n');
		}
		return strb.toString();
	}

	private void checkRegisteredCalls() {
		for (final CallingElement ce : cel) {
			if (ce.getMethod() == null) {
				final RuntimeException e = new GambaMockException(
					"method call partially defined found, with returning value: " + ce.getReturningObject());
				log.error(renderException(e));
				throw e;
			}
		}
	}

}
