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




	@Test
	public void test1() {

		Node n = new Node(board);
		final ISearch ab = new Minimax(false);
		for (int i = 0; i < 4; i++) {
			n = ab.search(n, 3, Node.WHITE_DIR);
			n = ab.search(n, 3, Node.BLACK_DIR);
		}

		Node n2 = new Node(board);
		final ISearch ab2 = new AlphaBeta2(false);
		for (int i = 0; i < 4; i++) {
			n2 = ab2.search(n2, 3, Node.WHITE_DIR);
			n2 = ab2.search(n2, 3, Node.BLACK_DIR);
		}

		Node n3 = new Node(board);
		final ISearch ab3 = new AlphaBeta2Hash(false);
		for (int i = 0; i < 4; i++) {
			n3 = ab3.search(n3, 3, Node.WHITE_DIR);
			n3 = ab3.search(n3, 3, Node.BLACK_DIR);
		}





		assertEquals(n, n2);
		assertEquals(n, n3);

	}

}