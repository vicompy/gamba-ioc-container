package org.gro.lispy.parser;

import org.junit.Test;

public class ParserTest {

	@Test
	public void test1() {

//		final String p = "( (+ 1 2 (+ 3 ZERO 4)))";
//		final String p = "( (and 1 1 1 1 1 1 1 1)(and 1 1 1 1 ZERO 1))";

//		final String p = "( ('(1 2 3)) (' ZERO) (disp version ('(3 4 5))))";
		final String p = "( ('(1 2 3)) )";

		final Parser parser = new Parser(p);
		parser.parseProgram();

	}

}
