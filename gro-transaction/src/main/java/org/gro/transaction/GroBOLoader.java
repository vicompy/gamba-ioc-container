package org.gro.transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import org.gro.connectionpool.GroPooling;

/**
 * @author mhoms
 */
public class GroBOLoader implements InvocationHandler {

	private final IGroGenericBO gambaBOInstance;

	private GroBOLoader(final Class<? extends IGroGenericBO> boClass) {
		try {
			gambaBOInstance = boClass.newInstance();
		} catch (final Exception exc) {
			throw new RuntimeException(exc);
		}
	}

	public static Object newInstance(final Class<? extends IGroGenericBO> boClass) {

		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				boClass.getInterfaces(), new GroBOLoader(boClass));
	}

	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

		final Connection conn = GroPooling.getInstance().getConnection();

		Object r = null;
		try {
			gambaBOInstance.setConnection(conn);
			r = method.invoke(gambaBOInstance, args);

			conn.commit();
		} catch (final Exception exc) {
			conn.rollback();
			throw new RuntimeException("jhgjhg", exc);
		} finally {
			GroPooling.getInstance().releaseConnection(conn);
		}

		return r;
	}

}
