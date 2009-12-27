package org.gro.chess.movgen;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.gro.chess.Node;
import org.gro.chess.movgen.MovGen;
import org.junit.Test;

public class MovGenTest {

	//	final String board = ""+
    //	"........"+
    //	"........"+
    //	"........"+
    //	"........"+
    //	"........"+
    //	"........"+
    //	"........"+
    //	"........";

	@Test
	public void test() {

		final String board = ""+
		"........"+
		"p·······"+
		"·A······"+
		"········"+
		"········"+
		"········"+
		"········"+
		"········";

		final Node n = new Node(board);
		final MovGen blackGen = new MovGen(n, Node.BLACK_DIR);


		assertEquals(
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| p · · · · · · · \n"+
				 "2| · A · · · · · · \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · · · · · \n"+
				 "5| · · · · · · · · \n"+
				 "6| · · · · · · · · \n"+
				 "7| · · · · · · · · \n"+
				 "\n",
				 n.toString()
		);

		final List<Node> killingBlack = blackGen.generaMovesMatadors();
		assertEquals(
				 "["+
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| · · · · · · · · \n"+
				 "2| · p · · · · · · \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · · · · · \n"+
				 "5| · · · · · · · · \n"+
				 "6| · · · · · · · · \n"+
				 "7| · · · · · · · · \n"+
				 "\n]",
		killingBlack.toString());
	}


	@Test
	public void testBlackMoves() {

		final String board = ""+
		"........"+
		"··p·····"+
		"·A·A····"+
		"········"+
		"········"+
		"········"+
		"········"+
		"········";

		final Node n = new Node(board);
		final MovGen blackGen = new MovGen(n, Node.BLACK_DIR);


		assertEquals(
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| · · p · · · · · \n"+
				 "2| · A · A · · · · \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · · · · · \n"+
				 "5| · · · · · · · · \n"+
				 "6| · · · · · · · · \n"+
				 "7| · · · · · · · · \n"+
				 "\n",
				 n.toString()
		);

		final List<Node> killingBlack = blackGen.generaMovesMatadors();
		assertEquals(
				 "["+
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| · · · · · · · · \n"+
				 "2| · A · p · · · · \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · · · · · \n"+
				 "5| · · · · · · · · \n"+
				 "6| · · · · · · · · \n"+
				 "7| · · · · · · · · \n"+
				 "\n"+
				 ", "+
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| · · · · · · · · \n"+
				 "2| · p · A · · · · \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · · · · · \n"+
				 "5| · · · · · · · · \n"+
				 "6| · · · · · · · · \n"+
				 "7| · · · · · · · · \n"+
				 "\n"+
				 "]",

				 killingBlack.toString()
		);


		final List<Node> noKillingBlack = blackGen.generaMovesNoMatadors();
		assertEquals(
				 "["+
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| · · · · · · · · \n"+
				 "2| · A p A · · · · \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · · · · · \n"+
				 "5| · · · · · · · · \n"+
				 "6| · · · · · · · · \n"+
				 "7| · · · · · · · · \n"+
				 "\n"+
				 ", "+
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| · · · · · · · · \n"+
				 "2| · A · A · · · · \n"+
				 "3| · · p · · · · · \n"+
				 "4| · · · · · · · · \n"+
				 "5| · · · · · · · · \n"+
				 "6| · · · · · · · · \n"+
				 "7| · · · · · · · · \n"+
				 "\n"+
				 "]",

				 noKillingBlack.toString()
		);


	}



	@Test
	public void testWhiteMoves() {

		final String board = ""+
		"........"+
		"········"+
		"········"+
		"········"+
		"········"+
		"····a·a·"+
		"·····P··"+
		"········";

		final Node n = new Node(board);
		final MovGen whiteGen = new MovGen(n, Node.WHITE_DIR);

		assertEquals(
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| · · · · · · · · \n"+
				 "2| · · · · · · · · \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · · · · · \n"+
				 "5| · · · · a · a · \n"+
				 "6| · · · · · P · · \n"+
				 "7| · · · · · · · · \n"+
				 "\n",
				 n.toString()
		);

		final List<Node> killingWhite = whiteGen.generaMovesMatadors();
		assertEquals(
				 "["+
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| · · · · · · · · \n"+
				 "2| · · · · · · · · \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · · · · · \n"+
				 "5| · · · · a · P · \n"+
				 "6| · · · · · · · · \n"+
				 "7| · · · · · · · · \n"+
				 "\n"+
				 ", "+
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| · · · · · · · · \n"+
				 "2| · · · · · · · · \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · · · · · \n"+
				 "5| · · · · P · a · \n"+
				 "6| · · · · · · · · \n"+
				 "7| · · · · · · · · \n"+
				 "\n"+
				 "]",

				 killingWhite.toString()
		);


		final List<Node> noKillingWhite = whiteGen.generaMovesNoMatadors();
		assertEquals(
				 "["+
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| · · · · · · · · \n"+
				 "2| · · · · · · · · \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · · · · · \n"+
				 "5| · · · · a P a · \n"+
				 "6| · · · · · · · · \n"+
				 "7| · · · · · · · · \n"+
				 "\n"+
				 ", "+
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| · · · · · · · · \n"+
				 "2| · · · · · · · · \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · · P · · \n"+
				 "5| · · · · a · a · \n"+
				 "6| · · · · · · · · \n"+
				 "7| · · · · · · · · \n"+
				 "\n"+
				 "]",

				 noKillingWhite.toString()
		);


	}


}
