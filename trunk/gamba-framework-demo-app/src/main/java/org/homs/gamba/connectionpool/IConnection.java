package org.homs.gamba.connectionpool;

public interface IConnection {

	public abstract String getConnectionURL();

	public abstract String getUserName();

	public abstract String getPassword();

	public abstract String getDriverClassName();

}