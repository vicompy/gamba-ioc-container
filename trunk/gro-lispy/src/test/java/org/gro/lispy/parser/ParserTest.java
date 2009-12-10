package org.gro.lispy.parser;

import static org.junit.Assert.*;
import org.junit.Test;

public class ParserTest {

	@Test
	public void test1() {

//		final String p = "( (+ 1 2 (+ 3 ZERO 4)))";
//		final String p = "( (and 1 1 1 1 1 1 1 1)(and 1 1 1 1 ZERO 1))";
//		final String p = "( ('(1 2 3)) (' ZERO) (disp version ('(3 4 5))))";
//		final String p = "( ('(1 2 3)) (' 1))";
//		final String p = "( (lambda (' x) ('(+ x x)))  )";
//		final String p = "( (+ 3.14159 2)  )";
//		final Parser parser = new Parser(p);
//		parser.parseProgram();

		assertEquals("[12]", parse("( (+ 1 2 3 (+ 1 2 3)) )"));
		assertEquals("[1.0]", parse("( (+ version ZERO) )"));
		assertEquals("[1]", parse("( (+ 1) )"));
		assertEquals("[0]", parse("( (+) )"));

		assertEquals("[[1, 2, 3]]", parse("((quote (1 2 3)))"));
		assertEquals("[[1, 2, 3]]", parse("((' (1 2 3)))"));

//		assertEquals("", parse("()"));
	}


	private String parse(final String program) {
		final Parser parser = new Parser(program);
		return parser.parseProgram().toString();
	}

}

// TODO testar errors i línies en missatges

/*
	(def inc
		(lambda (x => (+ x 1))))
	(inc 10)

	((lambda (x => (+ x 1))) 10)
	(+ 10 1)
	11

*/