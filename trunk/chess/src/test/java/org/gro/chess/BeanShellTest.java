package org.gro.chess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import bsh.EvalError;
import bsh.Interpreter;

public class BeanShellTest {

	@Test
	public void test1() throws EvalError, FileNotFoundException, IOException {
		final Interpreter i = new Interpreter(); // Construct an interpreter
		i.set("foo", 5); // Set variables
		i.set("date", new Date());

		@SuppressWarnings("unused")
		final Date date = (Date) i.get("date"); // retrieve a variable

		// Eval a statement and get the result
		i.eval("bar = foo*10; System.out.println(\"jou\")");
		System.out.println(i.get("bar"));

		// Source an external script file
//		i.source("somefile.bsh");

	}
}