package org.gro.chess;

//import static junit.framework.Assert.*;


import static org.junit.Assert.assertEquals;

import org.gro.chess.algols.AlphaBeta;
import org.gro.chess.algols.ISearch;
import org.gro.chess.algols.Minimax;
import org.gro.chess.algols.NegaScout;
import org.junit.Test;


//@Ignore
public class SearchAlgolsTest {


	private final static String board1 = ""+
    	"t..q.tk."+
    	"..p..ppp"+
    	"p...p..."+
    	".p.cP..."+
    	"....p..."+
    	"P.P....."+
    	"..P..PPP"+
    	"T.AQ.TK.";

	private final static String board2 = ""+
    	"tcaqkact"+
    	"pppppppp"+
    	"········"+
    	"········"+
    	"········"+
    	"········"+
    	"PPPPPPPP"+
    	"TCAQKACT";

	private final static String board3 = ""+
    	"········"+
    	"········"+
    	"·k······"+
    	"········"+
    	"···p····"+
    	"··Q·····"+
    	"·····A··"+
    	"········";


//	@Test
//	public void b() {
//		Node n1 = new Node(board2);
//		final ISearch ab1 = new Minimax();
//		n1 = ab1.search(n1, 1, Node.BLACK_DIR);
//		System.out.println(n1);
//
//		Node n2 = new Node(board2);
//		final ISearch ab2 = new Minimax();
//		n2 = ab2.search(n2, 5, Node.BLACK_DIR);
//		System.out.println(n2);
//
//		Node n5 = new Node(board2);
//		final ISearch ab5 = new AlphaBeta();
//		n5 = ab5.search(n5, 1, Node.BLACK_DIR);
//		System.out.println(n5);
//
//		Node n6 = new Node(board2);
//		final ISearch ab6 = new AlphaBeta();
//		n6 = ab6.search(n6, 5, Node.BLACK_DIR);
//		System.out.println(n6);
//
//		assertEquals(n1, n5); //TODO funka de casualitat
//		assertEquals(n2, n6); //TODO falla
//	}


	private final int nIters = 2;

	@Test
	public void test1() {
		testMinimaxVsAlphaBeta(board1, nIters, 2);
		testMinimaxVsAlphaBeta(board2, nIters, 2);
		testMinimaxVsAlphaBeta(board3, nIters, 2);
	}

	@Test
	public void test2() {
		testMinimaxVsAlphaBeta(board1, nIters, 4);
		testMinimaxVsAlphaBeta(board2, nIters, 4);
		testMinimaxVsAlphaBeta(board3, nIters, 4);
	}

	@Test
	public void test3() {
		testAlphaBetaVsNegaScout(board1, nIters, 2);
		testAlphaBetaVsNegaScout(board2, nIters, 2);
		testAlphaBetaVsNegaScout(board3, nIters, 2);
	}

	@Test
	public void test4() {
		testAlphaBetaVsNegaScout(board1, nIters, 4);
		testAlphaBetaVsNegaScout(board2, nIters, 4);
		testAlphaBetaVsNegaScout(board3, nIters, 4);
	}

	@Test
	public void test5() {
		testAlphaBetaVsNegaScout(board1, nIters, 6);
		testAlphaBetaVsNegaScout(board2, nIters, 6);
		testAlphaBetaVsNegaScout(board3, nIters, 6);
	}

	private void testMinimaxVsAlphaBeta(final String board, final int turns, final int depth) {
		long t;

		System.out.println("===================");

		/*
		 * testa MiniMax
		 */
		t = System.currentTimeMillis();
		Node n1 = new Node(board);
		final ISearch ab1 = new Minimax();
		for (int i = 0; i < turns; i++) {
			n1 = ab1.search(n1, depth, Node.WHITE_DIR);
			n1 = ab1.search(n1, depth, Node.BLACK_DIR);
		}
		System.out.println("in " + (System.currentTimeMillis() - t) + " ms.");

		/*
		 * testa MiniMax-Alfa/Beta
		 */
		t = System.currentTimeMillis();
		Node n2 = new Node(board);
		final ISearch ab2 = new AlphaBeta();
		for (int i = 0; i < turns; i++) {
			n2 = ab2.search(n2, depth, Node.WHITE_DIR);
			n2 = ab2.search(n2, depth, Node.BLACK_DIR);
		}
		System.out.println("in " + (System.currentTimeMillis() - t) + " ms.");

		assertEquals(n1, n2);
	}



	private void testAlphaBetaVsNegaScout(final String board, final int turns, final int depth) {
		long t;

		System.out.println("===================*");

		/*
		 * testa MiniMax-Alfa/Beta
		 */
		t = System.currentTimeMillis();
		long na = 0;
		Node n1 = new Node(board);
		final ISearch ab1 = new AlphaBeta();
		for (int i = 0; i < turns; i++) {
			n1 = ab1.search(n1, depth, Node.WHITE_DIR);
			na += ab1.analitzats();
			n1 = ab1.search(n1, depth, Node.BLACK_DIR);
			na += ab1.analitzats();
		}
		System.out.println("in " + (System.currentTimeMillis() - t) + " ms.");
		System.out.println("total: "+na);

		/*
		 * testa NegaScout
		 */
		t = System.currentTimeMillis();
		na = 0;
		Node n2 = new Node(board);
		final ISearch ab2 = new NegaScout();
		for (int i = 0; i < turns; i++) {
			n2 = ab2.search(n2, depth, Node.WHITE_DIR);
			na += ab2.analitzats();
			n2 = ab2.search(n2, depth, Node.BLACK_DIR);
			na += ab2.analitzats();
		}
		System.out.println("in " + (System.currentTimeMillis() - t) + " ms.");
		System.out.println("total: "+na);

		assertEquals(n1, n2);
	}

}