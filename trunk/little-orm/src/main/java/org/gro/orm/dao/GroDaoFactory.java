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
import java.sql.SQLException;
import java.sql.Statement;

import org.gro.orm.annotation.Query;
import org.gro.orm.annotation.Retrieve;
import org.gro.orm.exception.GroOrmException;
import org.gro.orm.mapping.SelectMapper;

/**
 * <p>
 * classe que donada una interfície de DAO amb els mètodes anotats per
 * {@link Query}, en retorna un proxy que processa i executa les sentències SQL.
 * El proxy es construeix ja amb una instància {@link java.sql.Connection}, a la
 * que utilitza per accedir per JDBC a la base de dades.
 * </p>
 * <p>
 * Veure que es pot accedir a la última sentència SQL processada i executada,
 * amb l'accessor {@link #getLastProcessedQuery()}.
 * </p>
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
	 * crea una instància proxy que compleix l'interfície de DAO, i conté una
	 * referència a la {@link java.sql.Connection}.
	 *
	 * @param daoInterface interfície de DAO a proxejar
	 * @return una nova instància de proxy
	 */
	public static Object newInstance(final Class<?> daoInterface, final Connection c) {

		if (daoInterface.isInterface()) {
			final Class<?>[] interfaceToImplement = new Class<?>[] { daoInterface };

			return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
					interfaceToImplement, new GroDaoFactory(c));
		}

		throw new GroOrmException("only interface types permitted");
	}

	/**
	 * membre interceptor del proxy; del mètode interceptat utilitza:
	 * <ul>
	 * <li>l'anotació {@link Query} la qual conté la sentència SQL a processar.</li>
	 * <li>els valors dels arguments amb els que s'ha incocat, per a aplicar
	 * substitucions sobre la query.</li>
	 * <li>el tipus de retorn, per a retornar el tipus esperat.</li>
	 * </ul>
	 *
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

		final Query annoQuery = method.getAnnotation(Query.class);
		if (annoQuery == null) {
			return null;
		}
		final String query = method.getAnnotation(Query.class).value().trim();
		lastProcessedQuery = processQuery(method, args, query);

		// System.out.println("quer: " + lastProcessedQuery);

		if (isSelectStatement(lastProcessedQuery)) {
			// una lectura Select
			return executeSelect(method, lastProcessedQuery);
		} else if (isInsertStatement(lastProcessedQuery)) {

			final Statement st = c.createStatement();
			final int rowsAffected = st.executeUpdate(lastProcessedQuery);

			final Retrieve annoRetrieve = method.getAnnotation(Retrieve.class);
			if (annoRetrieve != null) {
				final String query2 = annoRetrieve.value().trim();
				lastProcessedQuery = processQuery(method, args, query2);
				final Object r = executeSelect(method, lastProcessedQuery);

				st.close();
				return r;
			}
			st.close();
			return rowsAffected;

		} else {
			// una modificació Update ó Delete

			final Statement st = c.createStatement();
			final int rowsAffected = st.executeUpdate(lastProcessedQuery);
			st.close();
			return rowsAffected;

		}

	}

	private Object executeSelect(final Method method, final String query) throws SQLException {
		final Class<?> returnType = method.getReturnType();
		if (returnType.isArray()) {
			final SelectMapper s = new SelectMapper(returnType.getComponentType());
			return s.queryForEntities(c, query);
		} else {
			final SelectMapper s = new SelectMapper(returnType);
			return s.queryForEntity(c, query);
		}
	}

	/**
	 * <p>
	 * determina si una sentència ja processada és de tipus Select (que
	 * retornarà el resultat de la consulta), o bé una de tipus
	 * update/insert/delete, doncs retornarè un enter indicant les tuples
	 * afectades.
	 * </p>
	 * <p>
	 * Veure que el tipus es determina per si la sentència processada comença
	 * per "select", així que la query no pot començar per altres caràcters com
	 * espais, tabuladors, comentaris, etc.
	 * </p>
	 *
	 * @param query
	 * @return
	 */
	private boolean isSelectStatement(final String query) {
		return query.toUpperCase().startsWith("SELECT");
	}

	private boolean isInsertStatement(final String query) {
		return query.toUpperCase().startsWith("INSERT");
	}

	private String processQuery(final Method method, final Object[] args, final String query) {

		final StringBuffer strb = new StringBuffer();
		for (int i = 0; i < query.length(); i++) {
			if (query.charAt(i) == '$') {

				int j = i + 1; // chupa $

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

	/**
	 * donat un valor (de tipus qualsevol), el retorna en representació
	 * {@link String} i apta per a ser aplicat a una query per substitució de
	 * variable.
	 *
	 * @param value
	 * @return
	 */
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

	/**
	 * retorna la última query processada i executada.
	 *
	 * @return
	 */
	public String getLastProcessedQuery() {
		return lastProcessedQuery;
	}

}
