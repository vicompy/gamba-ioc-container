package org.gro.lispy.parser;

import java.io.IOException;

import org.junit.Test;

public class FileParserTest {


	@Test
	public void test1() throws IOException {

		FileParser.parse("./test.lisp");
	}
}
