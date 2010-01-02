package org.gro.chess;

//import static junit.framework.Assert.*;


import static org.junit.Assert.assertEquals;

import org.gro.chess.algols.ISearch;
import org.gro.chess.algols.NegaScout;
import org.gro.chess.algols.QuiescentMinimax;
import org.gro.chess.algols.quiescents.QuiescentAlphaBeta;
import org.junit.Test;


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

	private final static String board2 = ""+
	"········"+
	"········"+
	"·k······"+
	"········"+
	"···p····"+
	"··Q·····"+
	"·····A··"+
	"········";

//	@Test
//	public void a() {
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
//		// TODO deixa matar al rei!? no pot ser!
//		Node n3 = new Node(board2);
//		final ISearch ab3 = new QuiescentMinimax(5);
//		n3 = ab3.search(n3, 1, Node.BLACK_DIR);
//		System.out.println(n3);
//
//		// aquest funka! el que falla és el q-minimax
//		Node n4 = new Node(board2);
//		final ISearch ab4 = new QuiescentAlphaBeta(5);
//		n4 = ab4.search(n4, 1, Node.BLACK_DIR);
//		System.out.println(n4);
//
//		assertEquals(n2, n4);
//	}


//	@Test
//	public void a2() {
//		Node n1 = new Node(board2);
//		final ISearch ab1 = new AlphaBeta();
//		n1 = ab1.search(n1, 1, Node.BLACK_DIR);
//		System.out.println(n1);
//
//		// aquest funka! el que falla és el q-minimax
//		Node n4 = new Node(board2);
//		final ISearch ab4 = new QuiescentAlphaBeta(5);
//		n4 = ab4.search(n4, 1, Node.BLACK_DIR);
//		System.out.println(n4);
//
////		assertEquals(n1, n4);
//	}


	private final int nIters = 1;

	@Test
	public void test1() {
		testQuiescentableNegaScoutVsNegaScout(board1, nIters, 2, 0);
		testQuiescentableNegaScoutVsNegaScout(board1, nIters, 4, 0);
//		testQuiescentableNegaScoutVsNegaScout(board1, nIters, 6, 0);

		testQuiescentableNegaScoutVsNegaScout(board2, nIters, 2, 0);
		testQuiescentableNegaScoutVsNegaScout(board2, nIters, 4, 0);
	}

	@Test
	public void test2() {
		testMinimaxVsAlphaBeta(board1, nIters, 2, 0);
		testMinimaxVsAlphaBeta(board1, nIters, 4, 0);
		testMinimaxVsAlphaBeta(board1, nIters, 2, 2); // TODO falla
		testMinimaxVsAlphaBeta(board1, nIters, 2, 4); // TODO falla
		testMinimaxVsAlphaBeta(board1, nIters, 4, 2); // TODO falla
	}

	@Test
	public void test3() {
		testMinimaxVsAlphaBeta(board2, nIters, 2, 0);
		testMinimaxVsAlphaBeta(board2, nIters, 4, 0);
		testMinimaxVsAlphaBeta(board2, nIters, 2, 2); // TODO falla
		testMinimaxVsAlphaBeta(board2, nIters, 2, 4); // TODO falla
		testMinimaxVsAlphaBeta(board2, nIters, 4, 2); // TODO falla
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
			n1 = ab1.search(n1, depth, Node.BLACK_DIR);
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
			n2 = ab2.search(n2, depth, Node.BLACK_DIR);
		}
		System.out.println("in " + (System.currentTimeMillis() - t) + " ms.");


		assertEquals(n1, n2);
	}


	public void testQuiescentableNegaScoutVsNegaScout(final String board, final int turns, final int depth, final int quiescentDepth) {
		long t;

		/*
		 * testa MiniMax
		 */
		t = System.currentTimeMillis();
		Node n1 = new Node(board);
		final ISearch ab1 = new QuiescentAlphaBeta(quiescentDepth);
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
		final ISearch ab2 = new NegaScout();
		for (int i = 0; i < turns; i++) {
			n2 = ab2.search(n2, depth, Node.WHITE_DIR);
			n2 = ab2.search(n2, depth, Node.BLACK_DIR);
		}
		System.out.println("in " + (System.currentTimeMillis() - t) + " ms.");


		assertEquals(n1, n2);
	}

}