package org.lechuga.mvc.scan;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.lechuga.mvc.scan.ControllerScanFacade;

public class ControllerScanFacadeTest {

	@Test
	public void test1() {

		assertEquals(
//			"{" +
//			"user/delete=UserController.delete, " +
//			"nightly-job/list=NightlyJobController.list, " +
//			"nightly-job/show=NightlyJobController.show" +
//			"}",
			"{" +
			"/nightly-job/list.do=/nightly-job/list.do => NightlyJobController.list, " +
			"/nightly-job/show.do=/nightly-job/show.do => NightlyJobController.show, " +
			"/user/delete.do=/user/delete.do => UserController.delete" +
			"}",
			ControllerScanFacade.getControllerMappingsMap("org.lechuga.mvc.annoscan.sp").toString());
	}
}
