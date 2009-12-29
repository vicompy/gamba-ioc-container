package org.gro.chess;

//import static junit.framework.Assert.*;


import static org.junit.Assert.assertEquals;

import org.gro.chess.algols.ISearch;
import org.gro.chess.algols.quiescents.QuiescentAlphaBeta;
import org.gro.chess.algols.quiescents.QuiescentMinimax;
import org.junit.Ignore;
import org.junit.Test;


@Ignore
public class QuiescentAlgolsTest {


	private final static String board1 = ""+
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
		final int nIters = 1;
		testMinimaxVsAlphaBeta(board1, nIters, 1, 2);
		testMinimaxVsAlphaBeta(board1, nIters, 2, 3);
		testMinimaxVsAlphaBeta(board1, nIters, 3, 3);
	}

	public void testMinimaxVsAlphaBeta(final String board, final int turns, final int depth, final int quiescentDepth) {
		long t;

		/*
		 * testa MiniMax
		 */
		t = System.currentTimeMillis();
		Node n1 = new Node(board);
		final ISearch ab1 = new QuiescentMinimax(quiescentDepth);
		for (int i = 0; i < turns; i++) {
			n1 = ab1.search(n1, depth, Node.WHITE_DIR);
//			n1 = ab1.search(n1, depth, Node.BLACK_DIR);
		}
		System.out.println("in " + (System.currentTimeMillis() - t) + " ms.");

		/*
		 * testa MiniMax-Alfa/Beta
		 */
		t = System.currentTimeMillis();
		Node n2 = new Node(board);
		final ISearch ab2 = new QuiescentAlphaBeta(quiescentDepth);
		for (int i = 0; i < turns; i++) {
			n2 = ab2.search(n2, depth, Node.WHITE_DIR);
//			n2 = ab2.search(n2, depth, Node.BLACK_DIR);
		}
		System.out.println("in " + (System.currentTimeMillis() - t) + " ms.");


		assertEquals(n1, n2);
	}

}