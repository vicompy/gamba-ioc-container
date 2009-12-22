package org.gro.chess;

//import static junit.framework.Assert.*;


import static org.junit.Assert.*;


import org.junit.Test;

public class SearchUsageTest {


	final static String board = ""+
	"t..q.tk."+
	"..p..ppp"+
	"p...p..."+
	".p.cP..."+
	"....p..."+
	"P.P....."+
	"..P..PPP"+
	"T.AQ.TK.";


	Node n = new Node(board);

	@Test
	public void test1() {

		final ISearch ab = new Minimax(false);
//		System.out.println(n);
		try {
			for (int i = 0; i < 4; i++) {
				n = ab.search(n, 3, Node.WHITE_DIR);
				n = ab.search(n, 3, Node.BLACK_DIR);
//				System.out.println(n = ab.search(n, 4, Node.WHITE_DIR));
//				System.out.println(n = ab.search(n, 4, Node.BLACK_DIR));
			}
		} catch (final Exception e) {
		}

//	}
//
//	@Test
//	public void test2() {

		Node n2 = new Node(board);
		final ISearch ab2 = new AlphaBeta(false);
//		System.out.println(n2);
		try {
			for (int i = 0; i < 4; i++) {
				n2 = ab2.search(n2, 3, Node.WHITE_DIR);
				n2 = ab2.search(n2, 3, Node.BLACK_DIR);
//				System.out.println(n = ab.search(n, 4, Node.WHITE_DIR));
//				System.out.println(n = ab.search(n, 4, Node.BLACK_DIR));
			}
		} catch (final Exception e) {
		}



//		Node n3 = new Node(board);
//		final AlphaBeta ab3 = new AlphaBeta(true);
////		System.out.println(n2);
//		try {
//			for (int i = 0; i < 2; i++) {
//				n3 = ab3.search(n3, 3, Node.WHITE_DIR);
//				n3 = ab3.search(n3, 3, Node.BLACK_DIR);
////				System.out.println(n = ab.search(n, 4, Node.WHITE_DIR));
////				System.out.println(n = ab.search(n, 4, Node.BLACK_DIR));
//			}
//		} catch (final Exception e) {
//		}

		assertEquals(n, n2);
//		assertEquals(n, n3);

//		assertEquals(n, n2);
	}

}