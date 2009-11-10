package org.homs.gamba.scanner;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class AnnotatedActionsScannerTest {

	/**
	 * mostra que si el package és invàlid/inexistent, no es tiraexcepció,
	 * simplement compta com a classes no trobades
	 */
	@Test
	public void test1() {

		final AnnotatedActionsScanner aas = new AnnotatedActionsScanner();
		final Map<String, DeclaredAction> m = aas.doScan("zorg.homs.gamba.scanner2");

		Assert.assertEquals(0, m.size());
	}

	/**
	 * demostra que pilla les actions recursivament per els packages
	 */
	@Test
	public void test2() {

		final AnnotatedActionsScanner aas = new AnnotatedActionsScanner();
		final Map<String, DeclaredAction> m = aas.doScan("org.homs.gamba.scanner");

		Assert.assertEquals(4, m.size());
		Assert.assertEquals("[search, start, start2, search2]", m.keySet().toString());
	}

}
