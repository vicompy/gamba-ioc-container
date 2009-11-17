package org.gamba.sm4j.mockproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import org.gamba.sm4j.exception.GambaMockingException;
import org.gamba.sm4j.sequences.AbstractSequence;
import org.gamba.sm4j.utils.Seq;

/**
 * Java Dynamic proxy que simula l'objecte mock.
 *
 * @author mhoms
 */
public final class MockProxy implements InvocationHandler {

	/**
	 * guarda l'estat de gravació/reproducció. del primer es passa al segon amb una crida <tt>replay()</tt>.
	 */
	private boolean inRecordingMode = true;

	/**
	 * objecte al que delegar les operacions de configuració i lògica de l'objecte Mock.
	 */
	private final IMockProxyLogic proxyLogic = new MockProxyLogic();

	public static Object newInstance(final Class<?> mockableInterface) {
		if (mockableInterface.isInterface()) {
			return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[] {
					mockableInterface, IMockProxyLogic.class }, new MockProxy());
		}

		throw new GambaMockingException("cannot mock a class, just have to be interface(s)");
	}

	public static Object newInstance(final Class<?>... mockableInterfaces) {

		final Class<?>[] interfaces = new Class<?>[mockableInterfaces.length + 1];
		for (int i = 0; i < mockableInterfaces.length; i++) {
			if (!mockableInterfaces[i].isInterface()) {
				throw new GambaMockingException("cannot mock a class, just have to be interface(s)");
			}
			interfaces[i] = mockableInterfaces[i];
		}
		interfaces[mockableInterfaces.length] = IMockProxyLogic.class;

		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces,
				new MockProxy());
	}

	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

		if (method.getName().equals("getCallConfig")) {
			return proxyLogic.getCallConfig();
		}

		if (method.getName().equals("replay")) {
			replay();
			return null;
		}

		if (inRecordingMode) {
			return recording(method, args);
		} else {

			if (method.getName().equals("verify")) {
				proxyLogic.verify();
				return null;
			}

			final List<MethodConfig> mcl = this.proxyLogic.getCallConfig();
			for (final MethodConfig mc : mcl) {
				if (method.equals(mc.getMethod())) {
					boolean methodFound = true;
					for (int i = 0; i < mc.getArguments().length; i++) {
//						if (!args[i].equals(mc.getArguments()[i]) && !mc.getArgMask()[i]) {
//							methodFound = false;
//							break;
//						}
					if (mc.getArgMask()[i] || // TODO algo sobra??
						args[i] == null && mc.getArguments()[i] == null ||
						args[i] != null && args[i].equals(mc.getArguments()[i]) ||
						mc.getArguments()[i] != null && mc.getArguments()[i].equals(args[i]) ||
						args[i].equals(mc.getArguments()[i])) {

					} else {
						methodFound = false;
						break;
					}
					}
					if (methodFound) {
						return mc.getSequence().getNext(args);
					}
				}
			}
			throw new GambaMockingException("undefined expectation, caused by an undefined method call: "
					+ method.getName() + "(" + (args != null ? Seq.enList(args).toString() : "") + ")");
		}
	}

	public void replay() {
		inRecordingMode = false;
		proxyLogic.replay();
	}

	private Object recording(final Method method, final Object[] args) {
		if (method.getName().equals("setSeq")) {
			proxyLogic.setSeq((AbstractSequence) args[0]);
			return null;
		}
		if (method.getName().equals("addMaskValue")) {
			proxyLogic.addMaskValue((Boolean) args[0]);
			return null;
		}

		// commit de definició!!! (es fa la crida)
		proxyLogic.commit(method, args);
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

}
