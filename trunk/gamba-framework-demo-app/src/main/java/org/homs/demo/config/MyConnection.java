package org.homs.demo.config;

import org.homs.gamba.connectionpool.IConnection;

public class MyConnection implements IConnection {

	private final String driverClassName;
	private final String connectionURL;
	private final String userName;
	private final String password;

	public MyConnection() {
		super();
		this.driverClassName = "org.hsqldb.jdbcDriver";
		this.connectionURL = "jdbc:hsqldb:file:/home/mhoms/usuarios";
		this.userName = null;
		this.password = null;
	}

	/**
	 * @see org.homs.gamba.connectionpool.IConnection#getConnectionURL()
	 */
	public String getConnectionURL() {
		return connectionURL;
	}

	/**
	 * @see org.homs.gamba.connectionpool.IConnection#getUserName()
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @see org.homs.gamba.connectionpool.IConnection#getPassword()
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @see org.homs.gamba.connectionpool.IConnection#getDriverClassName()
	 */
	public String getDriverClassName() {
		return driverClassName;
	}

}
