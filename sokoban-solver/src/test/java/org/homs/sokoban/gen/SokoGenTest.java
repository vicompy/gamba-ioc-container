package org.homs.sokoban.gen;

import org.homs.sokoban.Mapa;
import org.junit.Test;

public class SokoGenTest {

	@Test
	public void test1() {
		final String l = "" +
//		" #####\n"+
//		" # ..#\n"+
//		"##$@ #\n"+
//		"#  $ #\n"+
//		"#   ##\n"+
//		"#####\n"+
//		"\n";
		"#######\n"+
		"#     #\n"+
		"#  +  #\n"+
		"#  *  #\n"+
		"#  $  #\n"+
		"#     #\n"+
		"#######\n"+
		"\n";

		System.out.println(new Mapa(l).toString2());
		new SokoGen().generate(new Mapa(l));
//		System.out.println(sr.toString());
//		Assert.assertEquals(9, sr.getLevel());
	}

}
