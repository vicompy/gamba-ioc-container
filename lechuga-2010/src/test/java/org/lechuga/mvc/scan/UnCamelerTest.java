package org.lechuga.mvc.scan;

import org.junit.Assert;
import org.junit.Test;
import org.lechuga.mvc.scan.UnCameler;

public class UnCamelerTest {

	@Test
	public void testCamelize() {

		// de-cameling
		Assert.assertEquals("bendito-seas-hijo-mio", UnCameler.deCamelize("BenditoSeasHijoMio"));
		Assert.assertEquals("bendito-seas-hijo-mio", UnCameler.deCamelize("benditoSeasHijoMio"));

		// cameling
		Assert.assertEquals("benditoSeasHijoMio", UnCameler.camelizeLo("bendito-seas-hijo-mio"));
		Assert.assertEquals("BenditoSeasHijoMio", UnCameler.camelizeHi("bendito-seas-hijo-mio"));

		Assert.assertEquals("BenditoSeAsHijoMio", UnCameler.camelizeHi("bendito-SeAs-hijo-mio"));
		Assert.assertEquals("BenditoSeasAIHijoMio", UnCameler.camelizeHi("bendito-seas-a-i-hijo-mio"));

		Assert.assertEquals("BenditoSEASHijoMio", UnCameler.camelizeHi("bendito-s-e-a-s-hijo-mio"));
		Assert.assertEquals("bendito-s-e-a-s-hijo-mio", UnCameler.deCamelize("BenditoSEASHijoMio"));
	}

}
