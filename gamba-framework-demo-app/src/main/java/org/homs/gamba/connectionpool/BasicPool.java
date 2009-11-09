package org.homs.gamba.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class BasicPool {

	// TODO quan finalitzi l'aplicació, s'han de tancar totes les connexions!!

	private final Vector<Connection> availableConnections = new Vector<Connection>();
	private final Vector<Connection> usedConnections = new Vector<Connection>();

	protected String connectionURL;
	protected String userName;
	protected String password;
	private int maxConnections;

	private BasicPool() {
	}

	public void setup(final IConnection connection) {

		try {
			Class.forName(connection.getDriverClassName());
		} catch (final ClassNotFoundException exc) {
			throw new RuntimeException(exc);
		}

		this.connectionURL = connection.getConnectionURL();
		this.userName = connection.getUserName();
		this.password = connection.getPassword();
		this.maxConnections = connection.getMaxConnections();

		for (int i = 0; i < maxConnections; i++) {
			try {
				availableConnections.addElement(getNewConnection());
			} catch (final SQLException exc) {
				throw new RuntimeException(exc);
			}
		}

	}

	private static class SingletonHolder {
		private static final BasicPool INSTANCE = new BasicPool();
	}

	public static BasicPool getInstance() {
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
		if (c != null) {
			usedConnections.removeElement(c);
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
