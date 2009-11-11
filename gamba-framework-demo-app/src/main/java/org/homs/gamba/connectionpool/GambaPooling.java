package org.homs.gamba.connectionpool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class GambaPooling {

	private final String driverClassName;
	private final String connectionUrl;
	private final String userName;
	private final String passWord;

	private final int preferredPoolSize;
	private final List<Connection> pool;
	private final Driver driverInstance;

	private GambaPooling() {

		final PropertiesLoader pl = new PropertiesLoader();
		driverClassName = pl.getProperty("driver-class-name");
		connectionUrl = pl.getProperty("connection-url");
		preferredPoolSize = Integer.valueOf(pl.getProperty("pool-size"));

		if ("null".equals(pl.getProperty("username"))) {
			userName = null;
		} else {
			userName = pl.getProperty("username");
		}
		if ("null".equals(pl.getProperty("password"))) {
			passWord = null;
		} else {
			passWord = pl.getProperty("password");
		}

		pool = new ArrayList<Connection>();

		try {
			driverInstance = (Driver) Class.forName(driverClassName).newInstance();
			DriverManager.registerDriver(driverInstance);

			for (int i = 0; i < preferredPoolSize; i++) {
				final Connection conn = DriverManager.getConnection(connectionUrl, userName, passWord);
				pool.add(conn);
			}
		} catch (final Exception exc) {
			throw new GambaPoolingException(exc);
		}

		// Statement stmt = conn.createStatement();
		// ResultSet rset =
		// stmt.executeQuery("select BANNER from SYS.V_$VERSION");
		// while (rset.next())
		// System.out.println (rset.getString(1)); // Print col 1
		// stmt.close();

	}

	public Connection getConnection() {

		if (pool.isEmpty()) {
			// no queden conexions disponibles, crear una nova
			System.out.println("-1" + poolCurrentSize() + "-");
			try {
				return DriverManager.getConnection(connectionUrl, userName, passWord);
			} catch (final Exception exc) {
				throw new GambaPoolingException(exc);
			}
		} else {
			final Connection conn;
			synchronized (this) {
				conn = pool.get(0);
				pool.remove(0);
			}
			System.out.println("-2" + poolCurrentSize() + "-");
			return conn;
		}
	}

	public void releaseConnection(final Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				if (pool.size() >= this.preferredPoolSize) {
					// les lliures ja estàn plenes, deixar desperdiciar aquesta
					conn.close();
				} else {
					synchronized (this) {
						pool.add(conn);
					}
				}
			}
			System.out.println("-r" + poolCurrentSize() + "-");
		} catch (final Exception exc) {
			throw new GambaPoolingException(exc);
		}
	}

	@Deprecated // només invocar en finalitzar l'aplicació!!
	public void destroyAllConnections() {
		try {
			for (final Connection c : pool) {
				c.close();
			}
			pool.clear();
			DriverManager.deregisterDriver(driverInstance);
		} catch (final Exception exc) {
			throw new GambaPoolingException(exc);
		}
	}

	/**
	 * reinicialitza l'estat del pool: tanca les anteriors, i re-omple el pool creant-ne de noves
	 */
	public void clearConnections() {
		try {
			for (final Connection c : pool) {
				c.close();
			}
			pool.clear();
			for (int i = 0; i < preferredPoolSize; i++) {
				final Connection conn = DriverManager.getConnection(connectionUrl, userName, passWord);
				pool.add(conn);
			}
		} catch (final Exception exc) {
			throw new GambaPoolingException(exc);
		}
	}

	public int poolCurrentSize() {
		return pool.size();
	}

	static class SingletonInstanceHolder {
		static final GambaPooling poolInstance = new GambaPooling();
	}

	public static GambaPooling getInstance() {
		return SingletonInstanceHolder.poolInstance;
	}
}
