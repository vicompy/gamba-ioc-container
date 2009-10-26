package org.homs.gamba.logging;

import org.homs.gamba.logging.exception.GambaLoggingException;
import org.homs.gamba.logging.interfaces.ILog;
import org.junit.Test;

public class LogTest {


	@Test
	public void test1() {
		final ILog log = new Log();
		log.warning(new GambaLoggingException("12345\n"));
	}
}
