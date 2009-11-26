package org.homs.sokoban;

import junit.framework.Assert;

import org.junit.Test;

public class SolverTest {

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

		System.out.println(new Mapa(l).toString());
		final SolutionResult sr = new Solver(l, new DummyMapHashImpl()).solve(18);
		System.out.println(sr.toString());
		Assert.assertEquals(9, sr.getLevel());
	}

	@Test
	public void test2() {
		final String l = "" +
		"####\n"+
		"#. #\n"+
		"#. ###\n"+
		"#@$  #\n"+
		"# $  #\n"+
		"#  ###\n"+
		"####\n"+
		"\n";

		final SolutionResult sr = new Solver(l, new DummyMapHashImpl()).solve(18);
		System.out.println(sr.toString());
		Assert.assertEquals(12, sr.getLevel());
	}

	@Test
	public void test1Hash() {
		final String l = "" +
		" #####\n"+
		" # ..#\n"+
		"##$@ #\n"+
		"#  $ #\n"+
		"#   ##\n"+
		"#####\n"+
		"\n";

		final SolutionResult sr = new Solver(l, new MapHash()).solve(18);
		System.out.println(sr.toString());
		Assert.assertEquals(9, sr.getLevel());
	}

	@Test
	public void test2Hash() {
		final String l = "" +
		"####\n"+
		"#. #\n"+
		"#. ###\n"+
		"#@$  #\n"+
		"# $  #\n"+
		"#  ###\n"+
		"####\n"+
		"\n";

		final SolutionResult sr = new Solver(l, new MapHash()).solve(18);
		System.out.println(sr.toString());
		Assert.assertEquals(12, sr.getLevel());
	}

	@Test
	public void test3Hash() {
		final String l = "" +
		"  #####\n"+
		"  # . #\n"+
		"###$. #\n"+
		"#   . #\n"+
		"# $$  #\n"+
		"## @###\n"+
		" ####\n"+
		"\n";

		final SolutionResult sr = new Solver(l, new MapHash()).solve(20);
		System.out.println(sr.toString());
		Assert.assertEquals(18, sr.getLevel());
	}

	@Test
	public void test4Hash() {
		final String l = "" +
		"#####\n"+
		"#   #\n"+
		"#   #\n"+
		"#  .#\n"+
		"##$ #\n"+
		" # +#\n"+
		" #$ #\n"+
		" # .#\n"+
		" #$ #\n"+
		" # .#\n"+
		" #$ #\n"+
		" #  #\n"+
		" ####\n"+
		"\n";

		final SolutionResult sr = new Solver(l, new MapHash()).solve(34);
		System.out.println(sr.toString());
		Assert.assertEquals(34, sr.getLevel());
	}

	@Test
	public void test5Hash() {
		final String l = "" +
		" ####\n"+
		" #  ####\n"+
		" #$    #\n"+
		"##@.#  #\n"+
		"#  #. ##\n"+
		"#    $#\n"+
		"####  #\n"+
		"   ####\n"+
		"\n";

		System.out.println(new Mapa(l).toString());
		final SolutionResult sr = new Solver(l, new MapHash()).solve(26);
		System.out.println(sr.toString());
		Assert.assertEquals(24, sr.getLevel());
	}

}
