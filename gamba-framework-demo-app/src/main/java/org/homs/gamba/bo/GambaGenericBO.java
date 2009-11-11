package org.homs.gamba.bo;

import java.sql.Connection;

public abstract class GambaGenericBO implements IGambaGenericBO {

	private Connection connection;

	protected Connection getConnection() {
		return connection;
	}

	/**
	 * @see org.homs.gamba.bo.IGambaGenericBO#setConnection(java.sql.Connection)
	 */
	public void setConnection(final Connection connection) {
		this.connection = connection;
	}

}
