package org.gro.logging;

import java.io.IOException;

import org.junit.Test;

public class GroLogTest {

	@Test
	public void run() throws SecurityException, IOException {

		final GroLog groLog = GroLog.getGroLogger(GroLogTest.class);

		groLog.finest("le finest");
		groLog.severe("le severe");

		try {
			throw new RuntimeException("jou q paixa?");
		} catch (final Exception e) {
			groLog.severe(e);
		}

		groLog.severe(GroLogTest.class.getName());
	}

	@Test
	public void run2() throws SecurityException, IOException {

		final GroLog groLog = GroLog.getGroLogger(GroLogTest.class).config();

		groLog.finest("le finest (", "Gro", Integer.valueOf(100), ") message ", "as possible");
		groLog.info("le info (", "Gro", Integer.valueOf(100), ") message ", "as possible");
	}

}
