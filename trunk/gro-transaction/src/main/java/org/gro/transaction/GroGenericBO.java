package org.gro.transaction;

import java.sql.Connection;

public abstract class GroGenericBO implements IGroGenericBO {

	private Connection connection;

	protected Connection getConnection() {
		return connection;
	}

	/**
	 * @see org.gro.transaction.IGroGenericBO#setConnection(java.sql.Connection)
	 */
	public void setConnection(final Connection connection) {
		this.connection = connection;
	}

}
