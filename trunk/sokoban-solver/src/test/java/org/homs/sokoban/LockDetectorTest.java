package org.homs.sokoban;

import junit.framework.Assert;

import org.junit.Test;

public class LockDetectorTest {

	@Test
	public void test1() {
		final String l = "" +
		"########\n" +
		"#      #\n" +
		"#  @$*  #\n" +
		"#  $..  #\n" +
		"#      #\n" +
		"#### ###\n" +
		"#      #\n" +
		"#      #\n" +
		"####   #\n" +
		"########\n" +
		"\n";

		final Mapa m = new Mapa(l);
		final String out = new LockDetector(m).toString();
		System.out.println(out);

		Assert.assertEquals(
		"#########\n" +
		"#########\n" +
		"##     ##\n" +
		"##     ##\n" +
		"##    ###\n" +
		"#### ####\n" +
		"##    ###\n" +
		"##    ###\n" +
		"#########\n" +
		"#########\n" +
		"\n",
		out);
	}
}
