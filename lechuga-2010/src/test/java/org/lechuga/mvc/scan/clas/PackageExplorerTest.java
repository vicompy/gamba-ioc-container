package org.lechuga.mvc.scan.clas;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.lechuga.mvc.scan.clas.ClassScanner;
import org.lechuga.mvc.scan.clas.ClassScannerException;

public class PackageExplorerTest {

	@Test
	public void test1() throws ClassNotFoundException, IOException {
		final Class<?>[] cl = new ClassScanner().getClasses("org.lechuga.mvc.annoscan.sp");

		assertEquals(3, cl.length);
	}

	@Test(expected=ClassScannerException.class)
	public void test3() throws ClassNotFoundException, IOException {

		new ClassScanner().getClasses("aslh");
	}

}


