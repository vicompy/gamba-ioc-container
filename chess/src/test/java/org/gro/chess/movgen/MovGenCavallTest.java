package org.gro.chess.movgen;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.gro.chess.Node;
import org.gro.chess.movgen.MovGen;
import org.junit.Test;

public class MovGenCavallTest {

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
		"········"+
		"·······p"+
		"·····C··"+
		"···p····";

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
				 "5| · · · · · · · p \n"+
				 "6| · · · · · C · · \n"+
				 "7| · · · p · · · · \n"+
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
				 "5| · · · · · · · p \n"+
				 "6| · · · · · · · · \n"+
				 "7| · · · C · · · · \n"+
				 "\n"+
				 ", "+
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| · · · · · · · · \n"+
				 "2| · · · · · · · · \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · · · · · \n"+
				 "5| · · · · · · · C \n"+
				 "6| · · · · · · · · \n"+
				 "7| · · · p · · · · \n"+
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
				 "5| · · · · · · · p \n"+
				 "6| · · · · · · · · \n"+
				 "7| · · · p · · · C \n"+
				 "\n"+
				 ", "+
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| · · · · · · · · \n"+
				 "2| · · · · · · · · \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · · · · · \n"+
				 "5| · · · C · · · p \n"+
				 "6| · · · · · · · · \n"+
				 "7| · · · p · · · · \n"+
				 "\n"+
				 ", "+
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| · · · · · · · · \n"+
				 "2| · · · · · · · · \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · · · C · \n"+
				 "5| · · · · · · · p \n"+
				 "6| · · · · · · · · \n"+
				 "7| · · · p · · · · \n"+
				 "\n"+
				 ", "+
				 " | 0 1 2 3 4 5 6 7\n"+
				 "-+----------------\n"+
				 "0| · · · · · · · · \n"+
				 "1| · · · · · · · · \n"+
				 "2| · · · · · · · · \n"+
				 "3| · · · · · · · · \n"+
				 "4| · · · · C · · · \n"+
				 "5| · · · · · · · p \n"+
				 "6| · · · · · · · · \n"+
				 "7| · · · p · · · · \n"+
				 "\n"+
				 "]",

				 noKillingWhite.toString()
		);


	}


}
