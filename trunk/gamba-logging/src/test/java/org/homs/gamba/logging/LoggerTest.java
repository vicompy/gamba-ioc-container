package org.homs.gamba.logging;

import java.util.List;

import org.homs.gamba.logging.handlers.ConsoleHandler;
import org.homs.gamba.logging.handlers.ListHandler;
import org.junit.Assert;
import org.junit.Test;

public class LoggerTest {

	@Test
	public void test1() {

		final Logger log = Logger.getLogger()
			.setLevel(Logger.DEBUG)
			.addHandler(new ConsoleHandler())
			.addHandler(new ListHandler())
			.disableDateShowing()
		;

		final ListHandler lh = (ListHandler) log.getHandlerList().get(1);
		lh.clear();
		final List<String> reportedLogs = lh.getLogs();

		log.fatal("fatal");
		log.error("error");
		log.warning("warning");
		log.info("info");
		log.debug("debug");


		Assert.assertTrue(reportedLogs.size() == 5);
		Assert.assertEquals(
			"[[FATAL] fatal, [ERROR] error, [WARNING] warning, [INFO] info, [DEBUG] debug]",
			reportedLogs.toString()
		);

	}

	@Test
	public void test2() {

		final Logger log = Logger.getLogger()
			.setLevel(Logger.WARNING)
			.disableDateShowing()
		;

		final ListHandler lh = (ListHandler) log.getHandlerList().get(1);
		lh.clear();
		final List<String> reportedLogs = lh.getLogs();

		log.fatal("fatal");
		log.error("error");
		log.warning("warning");
		log.info("info");
		log.debug("debug");


		Assert.assertEquals(
			"[[FATAL] fatal, [ERROR] error, [WARNING] warning]",
			reportedLogs.toString()
		);

	}

}
