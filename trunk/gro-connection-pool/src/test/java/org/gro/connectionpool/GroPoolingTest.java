package org.gro.connectionpool;

import java.sql.Connection;
import java.util.Stack;

import org.gro.logging.GroLog;
import org.junit.Test;

public class GroPoolingTest {

	@Test
	public void test1() {

		final IPoolLogger logger = new IPoolLogger() {
			private final GroLog log = GroLog.getGroLogger(GroPooling.class);

			public void receiveMessage(final String message) {
				log.info(message);
			}
		};

		GroLog.getGroLogger(this.getClass()).config();
		final GroPooling pool = GroPooling.getInstance();
		pool.setLogger(logger);

		final Stack<Connection> used = new Stack<Connection>();

		for (int k = 0; k < 100; k++) {
			for (int i = 0; i < 10; i++) {
				used.push(pool.getConnection());
			}
			for (int i = 0; i < 10; i++) {
				pool.releaseConnection(used.pop());
			}
		}

		pool.clearConnections();
		pool.destroyAllConnections();
	}

}
