package org.homs.gamba.stub;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.homs.gamba.logging.Log;
import org.homs.gamba.stub.exception.GambaStubsException;

/**
 * @author mhoms
 */
class StubProxy implements InvocationHandler {

	private final List<CallingElement> cel = new ArrayList<CallingElement>();
	private boolean recording = true;
	private static final Log log = new Log(StubProxy.class);

	public static Object newInstance(final Class<?> stubableInterface) {
		return Proxy.newProxyInstance(stubableInterface.getClassLoader(), new Class<?>[] { stubableInterface,
				IStubable.class }, new StubProxy());
	}

	/**
	 * contracte de crides a aquest proxy:
	 * 1. cridar a setReturnValue, amb el valor de retorn desitjat.
	 * 2. fer la crida a l'ínterfície original i així
	 * registrar el <tt>CallingElement</tt>.
	 * 3. repetir (2).
	 * 4. cridar stopRecording
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
				cel.add(new CallingElement(args[0], false));
				return null;
			}
			if (method.getName().equals("setThrowing")) {
				if (!(args[0] instanceof Throwable)) {
					final RuntimeException e = new GambaStubsException("this is not a Throwable object \n"); // TODO
					log.error(e);
					throw e;
				}
				final CallingElement ce = new CallingElement(args[0], true);
				cel.add(ce);
				return null;
			}

			if (method.getName().equals("setDelegator")) {
				if (!(args[0] instanceof IDelegator)) {
					final RuntimeException e = new GambaStubsException("this is not an IDelegator object \n"); // TODO
					log.error(e);
					throw e;
				}
				cel.add(new CallingElement((IDelegator) args[0]));
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
						if (ce.getReturningObjectisAnExceptionToThrow() != null
								&& ce.getReturningObjectisAnExceptionToThrow()) {
							log.debug("throwing...");
							throw (Throwable) ce.getReturningObject();
						}
						if (ce.getDelegator() != null) {
							log.debug("delegating...");
							return ce.getDelegator().delegates(args);
						}
						log.debug("calling: " + ce.toString());
						return ce.getReturningObject();
					}
				}
			}

			final RuntimeException e = new GambaStubsException("method call not registered: \n"); // TODO
			log.error(e);
			throw e;
		}
	}

	private void checkRegisteredCalls() {
		for (final CallingElement ce : cel) {
			if (ce.getMethod() == null) {
				final RuntimeException e = new GambaStubsException(
						"method call partially defined found, with returning value: " + ce.getReturningObject());
				log.error(e);
				throw e;
			}
		}
	}

}
