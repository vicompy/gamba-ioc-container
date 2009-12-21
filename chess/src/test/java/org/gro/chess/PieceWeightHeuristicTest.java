package org.gro.chess;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PieceWeightHeuristicTest {

	private Node n;

	@Before
	public void setUp() {

		final String board = ""+
		"qq......"+
		"pppppppp"+
		".......q"+
		"........"+
		"........"+
		"..P··A.."+
		".....K.."+
		"........";

		n = new Node(board);
	}

	@Test
	public void testConstruction() {

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
		assertTrue(PieceWeightHeuristic.calc(n, Node.BLACK_DIR) < 0);
		assertTrue(PieceWeightHeuristic.calc(n, Node.WHITE_DIR) > 0);

		assertTrue(PieceWeightHeuristic.calc(n, Node.WHITE_DIR) == -PieceWeightHeuristic.calc(n, Node.BLACK_DIR));
	}


}
