package org.gro.orm.datasource;

import javax.sql.DataSource;

import org.hsqldb.jdbc.jdbcDataSource;
import org.junit.Ignore;

@Ignore
public class TestingDataSourceFactory {

	public static DataSource getDataSource() {

		final jdbcDataSource ds = new jdbcDataSource();

		ds.setDatabase("jdbc:hsqldb:file:/home/mhoms/usuarios");
		ds.setUser(null);
		ds.setPassword(null);

		return ds;
	}

}
