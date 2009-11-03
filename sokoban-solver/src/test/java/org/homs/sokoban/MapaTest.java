package org.homs.sokoban;

import junit.framework.Assert;

import org.junit.Test;

public class MapaTest {

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
		"# #### #\n" +
		"# #  # #\n" +
		"#   ## #\n" +
		"# ## # #\n" +
		"# #    #\n" +
		"########\n" +
		"\n";

		System.out.println(new Mapa(l).toString());

		Assert.assertEquals(
			"13,9<" +
			"   |<" +
			"#########<" +
			"#      ##<" +
			"#  @$*  #-<" +
			"#  $..  #<" +
			"#      ##<" +
			"#### ####<" +
			"#      ##<" +
			"# #### ##<" +
			"# #  # ##<" +
			"#   ## ##<" +
			"# ## # ##<" +
			"# #    ##<" +
			"#########<" +
			"<" +
			"#########<" +
			"#      ##<" +
			"#   ##  #<" +
			"#  #    #<" +
			"#      ##<" +
			"#### ####<" +
			"#      ##<" +
			"# #### ##<" +
			"# #  # ##<" +
			"#   ## ##<" +
			"# ## # ##<" +
			"# #    ##<" +
			"#########<" +
			"<",
			new Mapa(l).toString().replace('\n', '<'));
	}

}


