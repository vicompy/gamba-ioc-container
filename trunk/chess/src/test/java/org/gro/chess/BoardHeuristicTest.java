package org.gro.chess;

import static junit.framework.Assert.*;

import org.junit.Test;

public class BoardHeuristicTest {


	@Test
	public void test1() {
		final String board = ""+
		"qq......"+
		"pppppppp"+
		".......q"+
		"........"+
		"........"+
		"..P··A.."+
		".....K.."+
		"........";

		final Node n = new Node(board);

		assertEquals(
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| q q · · · · · · \n"+
				 "1| p p p p p p p p \n"+
				 "2| · · · · · · · q \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · · · · · \n"+
				 "5| · · P · · A · · \n"+
				 "6| · · · · · K · · \n"+
				 "7| · · · · · · · · \n"+
				 "\n",
				 n.toString()
		);
		assertTrue(BoardHeuristic.calc(n, Node.BLACK_DIR) < 0);
		assertTrue(BoardHeuristic.calc(n, Node.WHITE_DIR) > 0);

//		assertTrue(BoardHeuristic.calc(n, Node.WHITE_DIR) == -BoardHeuristic.calc(n, Node.BLACK_DIR));
	}


	@Test
	public void test2() {
		final String board = ""+
		"········"+
		"········"+
		"·p······"+
		"········"+
		"········"+
		"········"+
		"········"+
		"········";

		final Node n = new Node(board);

		assertEquals(50L, BoardHeuristic.calc(n, Node.BLACK_DIR));
		assertEquals(-50L, BoardHeuristic.calc(n, Node.WHITE_DIR));
	}

	@Test
	public void test3() {
		final String board = ""+
		"········"+
		"········"+
		"·P······"+
		"········"+
		"········"+
		"········"+
		"········"+
		"········";

		final Node n = new Node(board);

		assertEquals(-50L, BoardHeuristic.calc(n, Node.BLACK_DIR));
		assertEquals(50L, BoardHeuristic.calc(n, Node.WHITE_DIR));
	}

	@Test
	public void test4() {
		final String board = ""+
		"········"+
		"········"+
		"·p······"+
		"··P·····"+
		"········"+
		"········"+
		"········"+
		"········";

		final Node n = new Node(board);

		assertEquals(10L, BoardHeuristic.calc(n, Node.BLACK_DIR));
		assertEquals(10L, BoardHeuristic.calc(n, Node.WHITE_DIR));
	}


}
