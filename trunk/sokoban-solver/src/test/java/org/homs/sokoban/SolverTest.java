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

		final SolutionResult sr = new Solver(l).solve(18);
		System.out.println(sr.toString());
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

		final SolutionResult sr = new Solver(l).solve(18);
		System.out.println(sr.toString());
		Assert.assertEquals(12, sr.getLevel());
	}


}


// 18p.
//"  #####\n"+
//"  # . #\n"+
//"###$. #\n"+
//"#   . #\n"+
//"# $$  #\n"+
//"## @###\n"+
//" ####\n"+

//9p.
//" #####\n"+
//" # ..#\n"+
//"##$@ #\n"+
//"#  $ #\n"+
//"#   ##\n"+
//"#####\n"+

// 12p.
//"####\n"+
//"#. #\n"+
//"#. ###\n"+
//"#@$  #\n"+
//"# $  #\n"+
//"#  ###\n"+
//"####\n"+
