package org.homs.gamba.connectionpool;

public interface IConnectionConfig {

	public abstract String getConnectionURL();

	public abstract String getUserName();

	public abstract String getPassword();

	public abstract String getDriverClassName();

	public abstract int getPoolSize();

}