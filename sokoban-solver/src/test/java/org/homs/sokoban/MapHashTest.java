package org.homs.sokoban;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Test;

public class MapHashTest {

	@Test
	public void test1() {
		final String l = "" +
		" #####\n"+
		" # ..#\n"+
		"##$@ #\n"+
		"#  $ #\n"+
		"#   ##\n"+
		"#####\n"+
		"\n";


		final MapHash h = new MapHash();

		final MapaGen m1 = new MapaGen(l);
		final MapaGen m2 = new MapaGen(l);


		h.put(m1, 5);

		final MapaW mv = h.exists(m2, 5);
		if(mv == null) {
			fail("hgf");
		}

		Assert.assertEquals(true, m1.hashCode() == m2.hashCode());
		Assert.assertEquals(m1, mv.getMapa());
	}

	@Test
	public void test2() {
		final String l = "" +
		" #####\n"+
		" # ..#\n"+
		"##$@ #\n"+
		"#  $ #\n"+
		"#   ##\n"+
		"#####\n"+
		"\n";


		final MapHash h = new MapHash();

		final MapaGen m1 = new MapaGen(l);
		final MapaGen m2 = new MapaGen(l);


		final boolean c1 = h.cuttable(m1, 5);
		final boolean c2 = h.cuttable(m2, 5);

		Assert.assertEquals(true, m1.hashCode() == m2.hashCode());
		Assert.assertEquals(false, c1);
		Assert.assertEquals(true, c2);
	}
}
