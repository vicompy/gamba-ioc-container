package org.gro.chess;


import java.io.IOException;

import org.gro.chess.algols.ISearch;
import org.gro.chess.algols.NegaScout;
import org.junit.Test;

import java.util.Scanner;


//@Ignore
public class PlayerTest {

	@Test
	public void jugaPartidaPerConsola() throws IOException {
		final String board = ""+
		"tcaqkact"+
		"pppppppp"+
		"········"+
		"········"+
		"········"+
		"········"+
		"PPPPPPPP"+
		"TCAQKACT";

		Node n = new Node(board);

		final ISearch ab = new NegaScout();
		System.out.println(n);

		for (int i = 0; i < 100; i++) {
			System.out.println("torn #"+i+" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("score blanques: " + BoardHeuristic.calcDiff(n, Node.WHITE_DIR));
			System.out.println("score negres: " + BoardHeuristic.calcDiff(n, Node.BLACK_DIR));
			System.out.println("entra mov: ");
			final Scanner in = new Scanner(System.in);
			final int srcy = in.nextInt();
			final int srcx = in.nextInt();
			final int dsty = in.nextInt();
			final int dstx = in.nextInt();
			n.movePiece(srcx+srcy*8, dstx+dsty*8);
			System.out.println(n);

//			System.out.println(n = ab.search(n, 6, Node.WHITE_DIR));
			System.out.println(n = ab.search(n, 6, Node.BLACK_DIR));

		} // 6444 7152 7542
	}

}

