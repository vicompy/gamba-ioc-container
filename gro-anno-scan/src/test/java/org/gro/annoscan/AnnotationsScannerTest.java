package org.gro.annoscan;

import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.*;

import org.gro.annoscan.AnnotatedMethod;
import org.gro.annoscan.AnnoScanner;
import org.junit.Test;

public class AnnotationsScannerTest {

	@Test
	public void test1() {
		final List<AnnotatedMethod> l = new AnnoScanner().doMethodScan("org.gro.annoscan", MGro.class);

		Collections.sort(l);
		assertEquals(
			"[org.gro.annoscan.sp.AC1.ac1(...): @org.gro.annoscan.MGro(name=jou), " +
			"org.gro.annoscan.sp.sk.AC3.ac1(...): @org.gro.annoscan.MGro(name=ac3!), " +
			"org.gro.annoscan.sp.sn.AC2.ac1(...): @org.gro.annoscan.MGro(name=ac2!)]",
			l.toString());
	}

	@Test(expected=AnnoScannerException.class)
	public void test1F() {

		@SuppressWarnings("unused")
		final List<AnnotatedMethod> l = new AnnoScanner().doMethodScan("askdjlfhkgh", MGro.class);
	}

	@Test
	public void test2() {
		final List<AnnotatedClass> l = new AnnoScanner().doClassScan("org.gro.annoscan", CGro.class);

		Collections.sort(l);
		assertEquals(
			"[org.gro.annoscan.sp.AC1: @org.gro.annoscan.CGro(name=jou), " +
			"org.gro.annoscan.sp.sk.AC3: @org.gro.annoscan.CGro(name=jou)]",
			l.toString());
	}

	@Test(expected=AnnoScannerException.class)
	public void test2F() {

		@SuppressWarnings("unused")
		final List<AnnotatedClass> l = new AnnoScanner().doClassScan("askdjlfhkgh", CGro.class);
	}

}
