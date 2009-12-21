package org.gro.chess;

//import static junit.framework.Assert.*;


import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

public class AlphaBetaTest {

	@Test
	public void testConstruction() {
//		final String board = ""+
//		"t......t"+
//		"........"+
//		"..c....."+
//		".a....a."+
//		"........"+
//		"..Q....."+
//		"...TPPP."+
//		"........";
//
//		final Node n = new Node(board);
//
//		final AlphaBeta ab = new AlphaBeta();
//		System.out.println(
//		ab.search(n, 6, Node.WHITE_DIR)
//		);

//		assertEquals(
//				 " | 0 1 2 3 4 5 6 7\n"+
//				 "-+----------------\n"+
//				 "0| · · · · · · · · \n"+
//				 "1| p p p p p p p p \n"+
//				 "2| · · · · · · · q \n"+
//				 "3| · · · · · · · · \n"+
//				 "4| · · · · · · · · \n"+
//				 "5| · · P · · A · · \n"+
//				 "6| · · · · · K · · \n"+
//				 "7| · · · · · · · · \n"+
//				 "\n",
//				 n.toString()
//		);
//		assertEquals(-1, n.getColorDir(42));
//		assertEquals(1, n.getColorDir(8));
	}


//	@Test
//	public void test2() {
//		final String board = ""+
//		"t..q.tk."+
//		"..p..ppp"+
//		"p...p..."+
//		".p.cP..."+
//		"....p..."+
//		"P.P....."+
//		"..P..PPP"+
//		"T.AQ.TK.";
//
//		Node n = new Node(board);
//		final AlphaBeta ab = new AlphaBeta();
//		System.out.println(n);
//		try {
//		for (int i = 0; i < 1; i++) {
//			System.out.println("torn #"+i+", blanques: ");
//			System.out.println(n = ab.search(n, 6, Node.WHITE_DIR));
//			System.out.println("torn #"+i+", negres: ");
//			System.out.println(n = ab.search(n, 6, Node.BLACK_DIR));
//			System.gc();
//		}
//		} catch(final Exception e) {}
//
//		Node n2 = new Node(board);
//		final AlphaBeta ab2 = new AlphaBeta();
//		System.out.println(n2);
//		try {
//		for (int i = 0; i < 1; i++) {
//			System.out.println("torn #"+i+", blanques: ");
//			System.out.println(n2 = ab2.search(n2, 6, Node.WHITE_DIR));
//			System.out.println("torn #"+i+", negres: ");
//			System.out.println(n2 = ab2.search(n2, 6, Node.BLACK_DIR));
//			System.gc();
//		}
//		} catch(final Exception e) {}
//
//		assertEquals(n, n2);
//
//	}

	@Test
	public void test3() throws IOException {
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

		final AlphaBeta ab = new AlphaBeta();
		System.out.println(n);

		for (int i = 0; i < 100; i++) {
			System.out.println("entra mov: ");
			final Scanner in = new Scanner(System.in);
			final int srcy = in.nextInt();
			final int srcx = in.nextInt();
			final int dsty = in.nextInt();
			final int dstx = in.nextInt();

			n.movePiece(srcx+srcy*8, dstx+dsty*8);
			System.out.println(n);

//			System.out.println(n = ab.search(n, 6, Node.WHITE_DIR));
//			System.gc();
			System.out.println(n = ab.search(n, 6, Node.BLACK_DIR));
			System.gc();
		}
	}

}
