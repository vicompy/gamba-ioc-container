//package org.gro.chess;
//
////import static junit.framework.Assert.*;
//
//
//import static org.junit.Assert.assertEquals;
//
//import org.gro.chess.algols.ISearch;
//import org.gro.chess.algols.SplittedNegaScout;
//import org.junit.Test;
//
//
//public class QuiescentAlgolsTest {
//
//
//	private final static String board1 = ""+
//	"t..q.tk."+
//	"..p..ppp"+
//	"p...p..."+
//	".p.cP..."+
//	"....p..."+
//	"P.P....."+
//	"..P..PPP"+
//	"T.AQ.TK.";
//
//	private final static String board2 = ""+
//	"········"+
//	"········"+
//	"·k······"+
//	"········"+
//	"···p····"+
//	"··Q·····"+
//	"·····A··"+
//	"········";
//	private final static String board3 = ""+
//	"tcaqkact"+
//	"pppppppp"+
//	"········"+
//	"········"+
//	"········"+
//	"········"+
//	"PPPPPPPP"+
//	"TCAQKACT";
//
////	private final int nIters = 2;
//
//	@Test
//	public void test0() {
//		final int nIters = 1;
//		testNSVsQNSAB(board1, nIters, 2, 0);
//		testNSVsQNSAB(board1, nIters, 4, 0);
//		testNSVsQNSAB(board1, nIters, 2, 2);
//		testNSVsQNSAB(board1, nIters, 2, 4);
//		testNSVsQNSAB(board1, nIters, 4, 2);
//	}
//
//	@Test
//	public void test1() {
//		final int nIters = 1;
//		testNSVsQNSAB(board2, nIters, 2, 0);
//		testNSVsQNSAB(board2, nIters, 4, 0);
//		testNSVsQNSAB(board2, nIters, 2, 2);
//		testNSVsQNSAB(board2, nIters, 2, 4);
//		testNSVsQNSAB(board2, nIters, 4, 2);
//	}
//
//	@Test
//	public void test2() {
//		final int nIters = 1;
//		testNSVsQNSAB(board3, nIters, 2, 0);
//		testNSVsQNSAB(board3, nIters, 4, 0);
//		testNSVsQNSAB(board3, nIters, 2, 2);
//		testNSVsQNSAB(board3, nIters, 2, 4);
//		testNSVsQNSAB(board3, nIters, 4, 2);
//	}
//
//	public void testNSVsQNSAB(final String board, final int turns, final int depth, final int quiescentDepth) {
//		long t;
//
//		System.out.println("===============================================");
//
//		/*
//		 * testa MiniMax
//		 */
//		t = System.currentTimeMillis();
//		Node n1 = new Node(board);
//		final ISearch ab1 = new SplittedNegaScout(0);
//		for (int i = 0; i < turns; i++) {
//			n1 = ab1.search(n1, depth+quiescentDepth, Node.WHITE_DIR);
//			n1 = ab1.search(n1, depth+quiescentDepth, Node.BLACK_DIR);
//		}
//		System.out.println("in " + (System.currentTimeMillis() - t) + " ms.");
//
//		/*
//		 * testa MiniMax-Alfa/Beta
//		 */
//		t = System.currentTimeMillis();
//		Node n2 = new Node(board);
//		final ISearch ab2 = new SplittedNegaScout(quiescentDepth);
//		for (int i = 0; i < turns; i++) {
//			n2 = ab2.search(n2, depth, Node.WHITE_DIR);
//			n2 = ab2.search(n2, depth, Node.BLACK_DIR);
//		}
//		System.out.println("in " + (System.currentTimeMillis() - t) + " ms.");
//
//
//		assertEquals(n1, n2);
//	}
//
//
//}