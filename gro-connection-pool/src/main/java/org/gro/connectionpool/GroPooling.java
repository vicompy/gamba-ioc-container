package org.gro.connectionpool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Stack;

public class GroPooling {

	private IPoolLogger logger;

	private final String connectionUrl;
	private final String userName;
	private final String passWord;

	private final int poolMaxSize;
	private final Stack<Connection> pool;
	private final Driver driverInstance;
	private int connectionsInUse;

	private final static String PROPERTIES_CONFIG_FILE = "pool-config.properties";

	protected GroPooling() {

		final PropertiesLoader propLoader = new PropertiesLoader(PROPERTIES_CONFIG_FILE);

		final String driverClassName = propLoader.getProperty("driver-class-name");
		connectionUrl = propLoader.getProperty("connection-url");
		poolMaxSize = Integer.valueOf(propLoader.getProperty("pool-size"));

		if ("none".equals(propLoader.getProperty("username"))) {
			userName = null;
		} else {
			userName = propLoader.getProperty("username");
		}
		if ("none".equals(propLoader.getProperty("password"))) {
			passWord = null;
		} else {
			passWord = propLoader.getProperty("password");
		}

		pool = new Stack<Connection>();

		try {
			driverInstance = (Driver) Class.forName(driverClassName).newInstance();
			DriverManager.registerDriver(driverInstance);

			for (int i = 0; i < poolMaxSize; i++) {
				final Connection conn = DriverManager.getConnection(connectionUrl, userName, passWord);
				pool.push(conn);
			}
		} catch (final Exception exc) {
			throw new GroPoolingException(exc);
		}

		connectionsInUse = 0;

	}

	public Connection getConnection() {

		Connection r;

		if (pool.isEmpty()) {
			// no queden conexions disponibles, crear una nova
			try {
				r = DriverManager.getConnection(connectionUrl, userName, passWord);
			} catch (final Exception exc) {
				throw new GroPoolingException(exc);
			}
		} else {
			// pop
			r = pushOrPop(null);
		}

		connectionsInUse++;
		log("connection served");
		return r;
	}

	public Connection pushOrPop(final Connection conn) {
		synchronized (this) {
			if (conn == null) {
				return pool.pop();
			} else {
				return pool.push(conn);
			}
		}
	}

	public void releaseConnection(final Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				if (pool.size() >= this.poolMaxSize) {
					// les lliures ja estàn plenes, deixar desperdiciar aquesta
					conn.close();
				} else {
					// push
					pushOrPop(conn);
				}
			}
		} catch (final Exception exc) {
			throw new GroPoolingException(exc);
		}
		connectionsInUse--;
		log("connection released");
	}

	// només invocar en finalitzar l'aplicació!!
	public void destroyAllConnections() {
		try {
			for (final Connection c : pool) {
				c.close();
			}
			pool.clear();
			DriverManager.deregisterDriver(driverInstance);
		} catch (final Exception exc) {
			throw new GroPoolingException(exc);
		}

		connectionsInUse = 0;
		log("pool connections are correctly destroyed");
	}

	/**
	 * reinicialitza l'estat del pool: tanca les anteriors, i re-omple el pool
	 * creant-ne de noves
	 */
	public void clearConnections() {
		try {
			for (final Connection c : pool) {
				c.close();
			}
			pool.clear();
			for (int i = 0; i < poolMaxSize; i++) {
				final Connection conn = DriverManager.getConnection(connectionUrl, userName, passWord);
				pool.push(conn);
			}
		} catch (final Exception exc) {
			throw new GroPoolingException(exc);
		}

		connectionsInUse = 0;
		log("pool connections are suspiciously flushed. this feature is very rare.");
	}

	public int poolCurrentSize() {
		return pool.size();
	}

	static class SingletonInstanceHolder {
		static final GroPooling POOL_INSTANCE = new GroPooling();
	}

	public static GroPooling getInstance() {
		return SingletonInstanceHolder.POOL_INSTANCE;
	}

	public void setLogger(final IPoolLogger logger) {
		this.logger = logger;
		logger.receiveMessage("connection pool startup OK");
	}

	public void log(final String message) {
		if (logger != null) {
			logger.receiveMessage(message + ". pool connections: in-use=" + connectionsInUse + " unused="
					+ pool.size());
		}
	}

	public int getConnectionsInUse() {
		return connectionsInUse;
	}

	public int getPoolSize() {
		return pool.size();
	}

	public int getPoolMaxSize() {
		return poolMaxSize;
	}

}
