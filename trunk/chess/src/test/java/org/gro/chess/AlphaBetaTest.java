package org.gro.chess;

//import static junit.framework.Assert.*;


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


	@Test
	public void test2() {
		final String board = ""+
		"t..q.tk."+
		"..p..ppp"+
		"p...p..."+
		".p.cP..."+
		"....p..."+
		"P.P....."+
		"..P..PPP"+
		"T.AQ.TK.";

		Node n = new Node(board);

		final AlphaBeta ab = new AlphaBeta();
		System.out.println(n);
		for (int i = 0; i < 50; i++) {
			System.out.println(n = ab.search(n, 6, Node.WHITE_DIR));
			System.gc();
			System.out.println(n = ab.search(n, 6, Node.BLACK_DIR));
			System.gc();
		}
	}
}
