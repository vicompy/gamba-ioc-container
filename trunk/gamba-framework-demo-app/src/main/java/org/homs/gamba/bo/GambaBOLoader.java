package org.homs.gamba.bo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import org.homs.gamba.connectionpool.GambaPooling;

/**
 * @author mhoms
 */
public class GambaBOLoader implements InvocationHandler {

	private final IGambaGenericBO gambaBOInstance;

	private GambaBOLoader(final Class<? extends IGambaGenericBO> boClass) {
		try {
			gambaBOInstance = boClass.newInstance();
		} catch (final Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	public static Object newInstance(final Class<? extends IGambaGenericBO> boClass) {

//		if (!(boClass instanceof IGambaGenericBO)) {
//			throw new RuntimeException();
//		}
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				boClass.getInterfaces(), new GambaBOLoader(boClass));
	}

	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

		final Connection conn = GambaPooling.getInstance().getConnection();

		Object r = null;
		try {
			gambaBOInstance.setConnection(conn);
			r = method.invoke(gambaBOInstance, args);

			conn.commit();
		} catch (final Exception exc) {
			conn.rollback();
			throw new RuntimeException("jhgjhg", exc);
		} finally {
			GambaPooling.getInstance().releaseConnection(conn);
		}

		return r;
	}

}
