package org.homs.demo.config;

import org.homs.gamba.connectionpool.IConnectionConfig;

public class MyConnection implements IConnectionConfig {

	private final String driverClassName;
	private final String connectionURL;
	private final String userName;
	private final String password;
	private final int poolSize;

	public MyConnection() {
		super();
		this.driverClassName = "org.hsqldb.jdbcDriver";
		this.connectionURL = "jdbc:hsqldb:file:/home/mhoms/usuarios";
		this.userName = null;
		this.password = null;
		this.poolSize = 10;
	}

	/**
	 * @see org.homs.gamba.connectionpool.IConnectionConfig#getConnectionURL()
	 */
	public String getConnectionURL() {
		return connectionURL;
	}

	/**
	 * @see org.homs.gamba.connectionpool.IConnectionConfig#getUserName()
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @see org.homs.gamba.connectionpool.IConnectionConfig#getPassword()
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @see org.homs.gamba.connectionpool.IConnectionConfig#getDriverClassName()
	 */
	public String getDriverClassName() {
		return driverClassName;
	}

	public int getPoolSize() {
		return this.poolSize;
	}

}
