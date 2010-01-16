/*
 *  Copyright 2009 m. homs
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.gro.orm.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import javax.sql.DataSource;

/**
 *
 *
 * @author mhoms
 */
public final class TransactionalServiceFactory implements InvocationHandler {

	private final DataSource dataSource;
	private final Object service;

	public TransactionalServiceFactory(final DataSource dataSource, final Object service) {
		super();
		this.dataSource = dataSource;
		this.service = service;
	}

	/**
	 * crea una instància del proxy
	 *
	 * @param interfaceToProx interfície a proxejar
	 * @return instància del proxy
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static Object newInstance(final Class<? extends Transactional> serviceClass,
			final DataSource dataSource) throws InstantiationException, IllegalAccessException {

		final Object service = serviceClass.newInstance();

		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), serviceClass
				.getInterfaces(), new TransactionalServiceFactory(dataSource, service));
	}

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		final Connection c = dataSource.getConnection();
		c.setAutoCommit(false);

		((Transactional) service).setConnection(c);

		try {

			final Object r = method.invoke(service, args);

			c.commit();
			return r;

		} catch (final Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
	}

}
