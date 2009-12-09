package org.gro.lispy.tokenizer;

import static org.junit.Assert.*;

import org.junit.Test;

public class TokenizerTest {

	@Test
	public void test1() {

		final String p = "(1 2 3 4 5)";
		final Tokenizer t = new Tokenizer(p);

		assertEquals("[1, 2, 3, 4, 5]", t.tokenize().toString());
	}

	@Test
	public void test2() {

		final String p = "(1 (2 3) 4 5)";
		final Tokenizer t = new Tokenizer(p);

		assertEquals("[1, [2, 3], 4, 5]", t.tokenize().toString());
	}

	@Test
	public void test3() {

		final String p = "(1 (2 () 3) 4.3 5)";
		final Tokenizer t = new Tokenizer(p);

		assertEquals("[1, [2, [], 3], 4.3, 5]", t.tokenize().toString());
	}

	@Test
	public void test4() {

		final String p = "(def double (lambda (x) (+ x x)))";
		final Tokenizer t = new Tokenizer(p);

		assertEquals("[def, double, [lambda, [x], [+, x, x]]]", t.tokenize().toString());
	}

	@Test
	public void test5() {

		final String p = "(((()))()(()()))";
		final Tokenizer t = new Tokenizer(p);

		assertEquals("[[[[]]], [], [[], []]]", t.tokenize().toString());
	}

	@Test
	public void test6() {

		final String p = "(\" hello \" \"world!\")";
		final Tokenizer t = new Tokenizer(p);

		assertEquals("[ hello , world!]", t.tokenize().toString());
	}

	@Test
	public void test7() {

		final String p = "(1(2)()3)";
		final Tokenizer t = new Tokenizer(p);

		assertEquals("[1, [2], [], 3]", t.tokenize().toString());
	}

	@Test
	public void test8() {

		final String p = "  (  1   (  2  )  (  )  3  )";
		final Tokenizer t = new Tokenizer(p);

		assertEquals("[1, [2], [], 3]", t.tokenize().toString());
	}

	@Test
	public void test9() {

		final String p = "(1 2 3)";
		final Tokenizer t = new Tokenizer(p);

		assertEquals("[1, 2, 3]", t.tokenize().toString());
	}


}
