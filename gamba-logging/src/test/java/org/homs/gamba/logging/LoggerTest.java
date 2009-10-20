package org.homs.gamba.logging;

import org.junit.Test;

public class LoggerTest {

	@Test
	public void testLogLevels() {

		final Log log = new Log(this.getClass());

		log.fatal("this is a FATAL log message");
		log.error("this is an ERROR log message");
		log.warning("this is a WARNING log message");
		log.info("this is a INFO log message");
		log.debug("this is a DEBUG log message");

	}

//	@Test
//	public void test2() {
//
//		final Logger log = Logger.getLogger()
//			.setLevel(Logger.WARNING)
//			.disableDateShowing()
//		;
//
//		final ListHandler lh = (ListHandler) log.getHandlerList().get(1);
//		lh.clear();
//		final List<String> reportedLogs = lh.getLogs();
//
//		log.fatal("fatal");
//		log.error("error");
//		log.warning("warning");
//		log.info("info");
//		log.debug("debug");
//
//
//		Assert.assertEquals(
//			"[[FATAL] fatal, [ERROR] error, [WARNING] warning]",
//			reportedLogs.toString()
//		);
//
//	}

}
