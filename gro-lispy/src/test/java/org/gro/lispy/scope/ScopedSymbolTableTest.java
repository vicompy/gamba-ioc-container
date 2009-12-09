package org.gro.lispy.scope;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ScopedSymbolTableTest {

	private ScopedSymbolTable<String> s;

	@Before
	public void setUp() {
		s = new ScopedSymbolTable<String>();
	}

	@Test
	public void test1() throws ScopedSymbolTableException {

		s.createLevel();

			s.define("one", "x");
			assertEquals("x", s.obtain("one"));

			s.createLevel();
				s.define("one", "1");
				s.define("two", "2");
				assertEquals("1", s.obtain("one"));
				assertEquals("2", s.obtain("two"));
				s.let("two", "22");
				assertEquals("22", s.obtain("two"));
			s.removeLevel();

			s.createLevel();
				assertEquals("x", s.obtain("one"));
				s.let("one", "1");
				assertEquals("1", s.obtain("one"));
			s.removeLevel();

		s.removeLevel();

	}

	/**
	 * espera {@link ScopedSymbolTableException}, degut a que s'intenta redefinir una
	 * variable ja definida en el mateix nivell.
	 */
	@Test(expected=ScopedSymbolTableException.class)
	public void test3() throws ScopedSymbolTableException {

		s.createLevel();
			s.define("jou", "jou1");
			s.define("jou", "jou2");
		s.removeLevel();
	}

	/**
	 * espera {@link ScopedSymbolTableException}, degut a que s'intenta obtenir una
	 * variable no definida.
	 */
	@Test(expected=ScopedSymbolTableException.class)
	public void test4() throws ScopedSymbolTableException {

		s.createLevel();
			s.obtain("jou");
		s.removeLevel();
	}

	/**
	 * espera {@link ScopedSymbolTableException}, degut a que s'intenta lettejar una
	 * variable no definida.
	 */
	@Test(expected=ScopedSymbolTableException.class)
	public void test5() throws ScopedSymbolTableException {

		s.createLevel();
			s.let("jou", "jou");
		s.removeLevel();
	}

}
