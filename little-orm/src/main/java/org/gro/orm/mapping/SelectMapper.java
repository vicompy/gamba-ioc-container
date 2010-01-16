package org.gro.orm.mapping;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.gro.orm.camel.UnCameler;
import org.gro.orm.exception.GroOrmException;
import org.gro.orm.introspection.IntrospectionEntity;
import org.gro.orm.introspection.IntrospectionBuilder;

public class SelectMapper {

	private final Class<?> entityClass;
	private final IntrospectionEntity entityFields;

	public <T> SelectMapper(final Class<T> entityClass) {
		super();
		this.entityClass = entityClass;
		entityFields = IntrospectionBuilder.inspect(entityClass);
	}

	public Object queryForEntity(final Connection c, final String query) throws SQLException {

		final Statement st = c.createStatement();
		final ResultSet rs = st.executeQuery(query);
		try {
			if (!rs.next()) {
				throw new GroOrmException("query produces no results");
			}
			return mapRow(rs);
		} finally {
			rs.close();
			st.close();
		}
	}

	public Object[] queryForEntities(final Connection c, final String query) throws SQLException {

		final Statement st = c.createStatement();
		final ResultSet rs = st.executeQuery(query);

		try {
			final List<Object> list = new ArrayList<Object>();
			while (rs.next()) {
				list.add(mapRow(rs));
			}

			final Object[] r = (Object[]) Array.newInstance(entityClass, list.size());
			for (int i = 0; i < list.size(); i++) {
				r[i] = list.get(i);
			}

			return r;
		} finally {
			rs.close();
			st.close();
		}
	}

	private Object mapRow(final ResultSet rs) throws SQLException {

		Object entity;
		try {
			entity = entityClass.newInstance();
		} catch (final Exception exc) {
			throw new GroOrmException("error instantiating " + entityClass.getName()
					+ ", has a public default constructor?", exc);
		}

		final ResultSetMetaData meta = rs.getMetaData();
		final int numColumns = meta.getColumnCount();

		for (int i = 1; i <= numColumns; i++) {
			final String colName = meta.getColumnName(i);
			final Object colValue = rs.getObject(i);

			entityFields.set(entity, UnCameler.camelizeLo(colName), colValue);
		}

		return entity;
	}

}
