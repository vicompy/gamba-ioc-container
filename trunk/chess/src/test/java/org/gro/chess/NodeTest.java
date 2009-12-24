package org.gro.chess;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NodeTest {

	//	final String board = ""+
    //	"........"+
    //	"........"+
    //	"........"+
    //	"........"+
    //	"........"+
    //	"........"+
    //	"........"+
    //	"........";

	private Node n;

	@Before
	public void setUp() {

		final String board = ""+
		"........"+
		"pppppppp"+
		".......q"+
		"........"+
		"........"+
		"..P··A.."+
		".....K.."+
		"........";

		n = new Node(board);
	}

//	@Test
//	public void testConstruction() {
//
////		System.out.println(n.toString());
//
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
//	}

	@Test
	public void testWhitePieceList() {

		final int[] p = n.pieceIndexList(Node.WHITE_DIR);
		final List<Integer> wx = new ArrayList<Integer>();
		final List<Integer> wy = new ArrayList<Integer>();

		for (final int i : p) {
			wx.add(i % 8);
			wy.add(i / 8);
		}
//		System.out.println(wx);
//		System.out.println(wy);

		assertEquals("[2, 5, 5]", wx.toString());
		assertEquals("[5, 5, 6]", wy.toString());
	}


	@Test
	public void testBlackPieceList() {

		final int[] p = n.pieceIndexList(Node.BLACK_DIR);
		final List<Integer> wx = new ArrayList<Integer>();
		final List<Integer> wy = new ArrayList<Integer>();

		for (final int i : p) {
			wx.add(i % 8);
			wy.add(i / 8);
		}
//		System.out.println(wx);
//		System.out.println(wy);

		assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 7]", wx.toString());
		assertEquals("[1, 1, 1, 1, 1, 1, 1, 1, 2]", wy.toString());
	}


}
