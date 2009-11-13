package org.homs.gamba.stub.any;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author mhoms
 */
public final class AnyProxy implements InvocationHandler {

//	private final Object returningValue;

	private AnyProxy() {
	}

	public static Object newInstance(final Class<?> objectToProxy) {
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
			getInterfacesOf(objectToProxy)
			, new AnyProxy());
	}

	private static Class<?>[] getInterfacesOf(final Class<?> objectToProxy) {

		final Class<?>[] stubableInterfaces = objectToProxy.getInterfaces();

		final Class<?>[] interfaces = new Class<?>[stubableInterfaces.length + 1];
		for (int i = 0; i < stubableInterfaces.length; i++) {
			interfaces[i] = stubableInterfaces[i];
		}
		interfaces[stubableInterfaces.length] = ImAny.class;

		return interfaces;
	}

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		return method.invoke(proxy, args);
	}


}
