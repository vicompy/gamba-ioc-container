package org.gro.chess;

//import static junit.framework.Assert.*;


import static org.junit.Assert.*;


import org.gro.chess.algols.NegascoutAlphaBeta;
import org.junit.Test;

import algols.AlphaBeta;
import algols.ISearch;
import algols.Minimax;

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
		long t;

		/*
		 * testa MiniMax
		 */
		t = System.currentTimeMillis();
		Node n = new Node(board);
		final ISearch ab = new Minimax();
		for (int i = 0; i < 6; i++) {
			n = ab.search(n, 3, Node.WHITE_DIR);
			n = ab.search(n, 3, Node.BLACK_DIR);
		}
		System.out.println("in " + (System.currentTimeMillis() - t) + " ms.");

		/*
		 * testa MiniMax-Alfa/Beta
		 */
		t = System.currentTimeMillis();
		Node n2 = new Node(board);
		final ISearch ab2 = new AlphaBeta();
		for (int i = 0; i < 6; i++) {
			n2 = ab2.search(n2, 3, Node.WHITE_DIR);
			n2 = ab2.search(n2, 3, Node.BLACK_DIR);
		}
		System.out.println("in " + (System.currentTimeMillis() - t) + " ms.");


		assertEquals(n, n2);
	}


	@Test
	public void test2() {
		long t;

		/*
		 * testa MiniMax-Alfa/Beta
		 */
		t = System.currentTimeMillis();
		Node n2 = new Node(board);
		final ISearch ab2 = new AlphaBeta();
		for (int i = 0; i < 4; i++) {
			n2 = ab2.search(n2, 5, Node.WHITE_DIR);
			n2 = ab2.search(n2, 5, Node.BLACK_DIR);
		}
		System.out.println("in " + (System.currentTimeMillis() - t) + " ms.");

		/*
		 * testa NegaScout
		 */
		t = System.currentTimeMillis();
		Node n3 = new Node(board);
		final ISearch ab3 = new NegascoutAlphaBeta();
		for (int i = 0; i < 4; i++) {
			n3 = ab3.search(n3, 5, Node.WHITE_DIR);
//			System.out.println(n3);
			n3 = ab3.search(n3, 5, Node.BLACK_DIR);
		}
		System.out.println("in " + (System.currentTimeMillis() - t) + " ms.");

//		assertEquals(n, n2);
		assertEquals(n2, n3);
	}

}