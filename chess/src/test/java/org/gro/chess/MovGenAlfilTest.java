package org.gro.chess;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class MovGenAlfilTest {

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
	public void testWhiteMoves() {

		final String board = ""+
		"........"+
		"········"+
		"········"+
		"········"+
		"·······p"+
		"····p···"+
		"····pA··"+
		"····P···";

		final Node n = new Node(board);
		final MovGen whiteGen = new MovGen(n, Node.WHITE_DIR);

		assertEquals(
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| · · · · · · · · \n"+
				 "2| · · · · · · · · \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · · · · p \n"+
				 "5| · · · · p · · · \n"+
				 "6| · · · · p A · · \n"+
				 "7| · · · · P · · · \n"+
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
				 "4| · · · · · · · A \n"+
				 "5| · · · · p · · · \n"+
				 "6| · · · · p · · · \n"+
				 "7| · · · · P · · · \n"+
				 "\n"+
				 ", "+
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| · · · · · · · · \n"+
				 "2| · · · · · · · · \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · · · · p \n"+
				 "5| · · · · A · · · \n"+
				 "6| · · · · p · · · \n"+
				 "7| · · · · P · · · \n"+
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
				 "4| · · · · · · · p \n"+
				 "5| · · · · p · · · \n"+
				 "6| · · · · p · · · \n"+
				 "7| · · · · P · A · \n"+
				 "\n"+
				 ", "+
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| · · · · · · · · \n"+
				 "2| · · · · · · · · \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · · · · p \n"+
				 "5| · · · · p · A · \n"+
				 "6| · · · · p · · · \n"+
				 "7| · · · · P · · · \n"+
				 "\n"+
				 "]",

				 noKillingWhite.toString()
		);


	}


}
