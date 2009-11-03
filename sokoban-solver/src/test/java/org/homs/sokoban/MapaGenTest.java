package org.homs.sokoban;

import org.junit.Test;

public class MapaGenTest {

	@Test
	public void test1() {
		final String l = "" +
		"########\n" +
		"#      #\n" +
		"#   $  #\n" +
		"# @ .  #\n" +
		"#   *  #\n" +
		"#      #\n" +
		"########\n" +
		"\n";

		System.out.println(new MapaGen(l).moveGen().toString());

	}


}


