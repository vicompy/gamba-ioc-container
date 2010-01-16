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
package org.gro.orm.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Statement;

import org.gro.orm.annotation.Query;
import org.gro.orm.exception.GroOrmException;
import org.gro.orm.mapping.SelectMapper;

/**
 * Java Dynamic proxy que simula l'objecte mock.
 *
 * @author mhoms
 */
public final class GroDaoFactory implements InvocationHandler {

	private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[] {};
	private final Connection c;
	private String lastProcessedQuery;

	public GroDaoFactory(final Connection c) {
		super();
		this.c = c;
	}

	/**
	 * crea una instància del proxy
	 *
	 * @param interfaceToProx interfície a proxejar
	 * @return instància del proxy
	 */
	public static Object newInstance(final Class<?> interfaceToProx, final Connection c) {

		if (interfaceToProx.isInterface()) {
			final Class<?>[] interfaceToImplement = new Class<?>[] { interfaceToProx };

			return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
					interfaceToImplement, new GroDaoFactory(c));
		}

		throw new GroOrmException("only interface types permitted");
	}

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

		lastProcessedQuery = processQuery(method, args);

		System.out.println("quer: " + lastProcessedQuery);

		if (isSelectStatement(lastProcessedQuery)) {
			// una lectura Select
			final Class<?> returnType = method.getReturnType();
			if (returnType.isArray()) {
				final SelectMapper s = new SelectMapper(returnType.getComponentType());
				return s.queryForEntities(c, lastProcessedQuery);
			} else {
				final SelectMapper s = new SelectMapper(returnType);
				return s.queryForEntity(c, lastProcessedQuery);
			}
		} else {
			// una modificació: Insert, Update ó Delete

			final Statement st = c.createStatement();
			final int rowsAffected = st.executeUpdate(lastProcessedQuery);
			st.close();
			return rowsAffected;

		}

	}

	private boolean isSelectStatement(final String query) {
		return query.toUpperCase().startsWith("SELECT");
	}

	private String processQuery(final Method method, final Object[] args) {

		final String query = method.getAnnotation(Query.class).value().trim();

		final StringBuffer strb = new StringBuffer();
		for (int i = 0; i < query.length(); i++) {
			if (query.charAt(i) == '$') {

				int j = i + 1; // chupa =$

				// recupera el # de paràmetre
				while (isDigit(query.charAt(j))) {
					j++;
					if (j == query.length()) {
						break;
					}
				}
				final int numArg = Integer.valueOf(query.substring(i + 1, j));

				// recupera el valor del paràmetre especificat
				Object value = args[numArg];

				// accedeix iterativament als accessors del valor
				while (j < query.length() && query.charAt(j) == '.') {
					j++; // chupa .
					int k = j;
					while (k < query.length() && isAlfa(query.charAt(k))) {
						k++;
					}
					final String propertyName = query.substring(j, k);
					j = k;

					Method getter;
					try {
						getter = value.getClass().getMethod(
								"get" + Character.toUpperCase(propertyName.charAt(0))
										+ propertyName.substring(1), EMPTY_CLASS_ARRAY);
					} catch (final Exception exc) {
						throw new GroOrmException("exception parsing expression: " + query.substring(i, k),
								exc);
					}
					try {
						value = getter.invoke(value, ((Object[]) null));
					} catch (final Exception exc) {
						throw new GroOrmException("exception parsing expression: " + query.substring(i, k),
								exc);
					}
				}
				i = j - 1;

				// genera el resultat de l'expressió del valor, sql-friendly
				strb.append(valueOf(value));

			} else {

				// el caràcter obtingut no és expressió
				strb.append(query.charAt(i));
			}
		}
		// strb.append(';');
		return strb.toString();
	}

	// /**
	// * converteix un valor java.lang a representació String, preparat per a
	// ser
	// * insertat a una query SQL.
	// *
	// * @param value
	// * @return
	// */
	// private String conditionalValueOf(final Object value) {
	// if (value == null) {
	// return " IS NULL";
	// } else if (value instanceof Number) {
	// return "=" + value.toString();
	// } else {
	// return "='" + value.toString().replaceAll("'", "''") + "'";
	// }
	// }

	private String valueOf(final Object value) {
		if (value == null) {
			return "NULL";
		} else if (value instanceof Number) {
			return value.toString();
		} else {
			return "'" + value.toString().replaceAll("'", "''") + "'";
		}
	}

	private boolean isDigit(final char c) {
		return c >= '0' && c <= '9';
	}

	private boolean isAlfa(final char c) {
		return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || isDigit(c);
	}

	public String getLastProcessedQuery() {
		return lastProcessedQuery;
	}

}
