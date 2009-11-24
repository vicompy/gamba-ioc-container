package org.gro.logging;

import java.io.IOException;
import java.util.logging.Level;

import org.gro.logging.GroLog;
import org.junit.Test;

public class GroLogTest {

	private final static GroLog GROLOG = GroLog.getGroLogger(GroLogTest.class, Level.FINEST);

//	public static void main(final String[] args) throws SecurityException, IOException {
//		new GroLogTest().run();
//	}

	@Test
	public void run() throws SecurityException, IOException {

		GROLOG.finest("le finest");
		GROLOG.severe("le severe");

		try {
			throw new RuntimeException("jou q paixa?");
		} catch (final Exception e) {
			GROLOG.severe(e);
		}

		GROLOG.severe(GroLogTest.class.getName());
	}

	@Test
	public void run2() throws SecurityException, IOException {

		GROLOG.finest("le finest (", Integer.valueOf(100), ") message ", "as possible");

	}

}
