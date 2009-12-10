package org.gro.lispy.parser;

import static org.junit.Assert.*;
import org.junit.Test;

public class ParserTest {

	@Test
	public void test1() {

		// aggregates basics: nested, vars, stupid expressions
		assertEquals("[12]", parse("( (+ 1 2 3 (+ 1 2 3)) )"));
		assertEquals("[12]", parse("( (+ 1 (+ 1 2 3)2 3 ) )"));
		assertEquals("[12]", parse("( (+ (+ 1 2 3)1 2 3 ) )"));
		assertEquals("[1.0]", parse("( (+ version ZERO) )"));
		assertEquals("[1]", parse("( (+ 1) )"));
		assertEquals("[0]", parse("( (+) )"));

		// longints vs. doubles
		assertEquals("[3]", parse("( (+ 1 2) )"));
		assertEquals("[3.0]", parse("( (+ 1.0 2) )"));
		assertEquals("[3.0]", parse("( (+ 1 2.0) )"));

		// quotes
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