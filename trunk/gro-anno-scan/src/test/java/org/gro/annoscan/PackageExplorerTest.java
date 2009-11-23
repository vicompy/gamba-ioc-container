package org.gro.annoscan;

import static org.junit.Assert.*;

import java.io.IOException;

import org.gro.annoscan.PackageExplorer;
import org.junit.Test;

@SuppressWarnings("unused") // TODO
public class PackageExplorerTest {

	@Test
	public void test1() throws ClassNotFoundException, IOException {
		final Class<?>[] cl = new PackageExplorer().getClasses("org.gro.annoscan.sp");

//		for (Class<?> c : cl) {
//			System.out.println(c);
//		}
		
		assertEquals(3, cl.length);		
	}

	@Test(expected=AnnoScannerException.class)
	public void test2() throws ClassNotFoundException, IOException {

		final Class<?>[] cl = new PackageExplorer().getClasses("aslh");
	}

}
