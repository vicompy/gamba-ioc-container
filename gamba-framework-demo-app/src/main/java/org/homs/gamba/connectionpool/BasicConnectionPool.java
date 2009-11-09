package org.homs.gamba.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class BasicConnectionPool {

	private final Vector<Connection> availableConnections = new Vector<Connection>();
	private final Vector<Connection> usedConnections = new Vector<Connection>();

	protected String connectionURL;
	protected String userName;
	protected String password;
	private int poolSize;

	private BasicConnectionPool() {
	}

	public void setup(final IConnectionConfig connection) {

		try {
			Class.forName(connection.getDriverClassName());
		} catch (final ClassNotFoundException exc) {
			throw new RuntimeException(exc);
		}

		this.connectionURL = connection.getConnectionURL();
		this.userName = connection.getUserName();
		this.password = connection.getPassword();
		this.poolSize = connection.getPoolSize();

		for (int i = 0; i < poolSize; i++) {
			try {
				availableConnections.addElement(getNewConnection());
			} catch (final SQLException exc) {
				throw new RuntimeException(exc);
			}
		}

	}

	private static class SingletonHolder {
		private static final BasicConnectionPool INSTANCE = new BasicConnectionPool();
	}

	public static BasicConnectionPool getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private Connection getNewConnection() throws SQLException {
		final Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		conn.setAutoCommit(false);
		return conn;
	}

	public synchronized Connection getConnection() throws SQLException {
		Connection conn = null;

		if (availableConnections.size() == 0) {
			conn = getNewConnection();
			usedConnections.addElement(conn);
		} else {
			conn = (Connection) availableConnections.lastElement();
			availableConnections.removeElement(conn);
			usedConnections.addElement(conn);
		}

		return conn;
	}

	public synchronized void releaseConnection(final Connection c) {
		usedConnections.removeElement(c);
		if (availableConnections.size() < poolSize) {
			availableConnections.addElement(c);
		}
	}

	public void closeAllConnections() {
		for (final Connection c : this.availableConnections) {
			try {
				c.close();
			} catch (final SQLException exc) {
			}
		}
		for (final Connection c : this.usedConnections) {
			try {
				c.close();
			} catch (final SQLException exc) {
			}
		}
		System.out.println("all connections closed");
	}

	public int availableCount() {
		return availableConnections.size();
	}

}
