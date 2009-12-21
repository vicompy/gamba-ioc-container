package org.gro.chess;

import java.util.Arrays;

public class Node implements Cloneable {

	public final static int WHITE_DIR = -1;
	public final static int BLACK_DIR = 1;

	private final static int WHITE = 10;
	private final static int BLACK = 20;

	public final static int BUIT = 0;
	public final static int PEO = 1;
	public final static int TORRE = 2;
	public final static int CAVALL = 3;
	public final static int ALFIL = 4;
	public final static int REINA = 5;
	public final static int REI = 6;

	private final int[] board;
	private boolean whiteKingIsMoved;
	private boolean blackKingIsMoved;



	private Node(final int[] board, final boolean whiteKingIsMoved, final boolean blackKingIsMoved) {
		super();
		this.board = board;
		this.whiteKingIsMoved = whiteKingIsMoved;
		this.blackKingIsMoved = blackKingIsMoved;
	}

	public Node(final String mapa) {
		whiteKingIsMoved = false;
		blackKingIsMoved = false;
		board = new int[8*8];

		for (int f = 0; f < 8*8; f++) {
			switch(mapa.charAt(f)) {
				case 'p': board[f] = BLACK+PEO; break;
				case 't': board[f] = BLACK+TORRE; break;
				case 'c': board[f] = BLACK+CAVALL; break;
				case 'a': board[f] = BLACK+ALFIL; break;
				case 'q': board[f] = BLACK+REINA; break;
				case 'k': board[f] = BLACK+REI; break;

				case 'P': board[f] = WHITE+PEO; break;
				case 'T': board[f] = WHITE+TORRE; break;
				case 'C': board[f] = WHITE+CAVALL; break;
				case 'A': board[f] = WHITE+ALFIL; break;
				case 'Q': board[f] = WHITE+REINA; break;
				case 'K': board[f] = WHITE+REI; break;

				default:  board[f] = BUIT;
			}
		}
	}

	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer();

		strb.append(" | 0 1 2 3 4 5 6 7\n");
		strb.append("-+----------------\n");
		for (int f = 0; f < 8; f++) {
			strb.append(f + "| ");
			for (int c = 0; c < 8; c++) {
				switch(board[c+f*8]) {
				case BLACK+PEO: strb.append("p"); break;
				case BLACK+TORRE: strb.append("t"); break;
				case BLACK+CAVALL: strb.append("c"); break;
				case BLACK+ALFIL: strb.append("a"); break;
				case BLACK+REINA: strb.append("q"); break;
				case BLACK+REI: strb.append("k"); break;

				case WHITE+PEO: strb.append("P"); break;
				case WHITE+TORRE: strb.append("T"); break;
				case WHITE+CAVALL: strb.append("C"); break;
				case WHITE+ALFIL: strb.append("A"); break;
				case WHITE+REINA: strb.append("Q"); break;
				case WHITE+REI: strb.append("K"); break;
				default: strb.append("·"); break;
				}
				strb.append(" ");
			}
			strb.append("\n");
		}
		strb.append("\n");

		return strb.toString();
	}

	@Override
	protected Object clone() {
		return new Node(this.board.clone(), this.whiteKingIsMoved, this.blackKingIsMoved);
	}

	public boolean movePiece(final int src, final int dst) {

		if (board[src] == BLACK + REI) {
			this.blackKingIsMoved = true;
		} else if (board[src] == WHITE + REI) {
			this.whiteKingIsMoved = true;
		}

		// TODO si el rei no està mogut, i fa moviment d'enroc, mout¡re tb la
		// torre!!

		final boolean isKillingMove = false;
		board[dst] = board[src];
		board[src] = BUIT;
		return isKillingMove;
	}

	public boolean isBlack(final int index) {
		return board[index] > BLACK;
	}

	public boolean isWhite(final int index) {
		return board[index] > WHITE && board[index] < BLACK;
	}

	public boolean isEmpty(final int index) {
		return board[index] % 10 == BUIT;
	}

	public int getColorDir(final int index) {
		if (isBlack(index)) {
			return BLACK_DIR;
		} else if (isWhite(index)) {
			return WHITE_DIR;
		} else {
			throw new RuntimeException(index + " -- " + board[index]);
		}
	}

	public boolean isOpponentPiece(final int index, final int myDir) {
		if (isEmpty(index)) {
			return false;
		}
		return getColorDir(index) == -myDir;
	}

	public boolean isFriendlyPiece(final int index, final int myDir) {
		if (isEmpty(index)) {
			return false;
		}
		return getColorDir(index) == myDir;
	}

	/**
	 * retorna el tipus de peça (sense info del color)
	 *
	 * @param index
	 * @return
	 */
	public int getPieceType(final int index) {
		return board[index] % 10;
	}

	private int[] blackPieceIndexList() {
		final int[] r = new int[16];
		int ir = 0;

		for (int i = 0; i < 8 * 8; i++) {
			if (!isEmpty(i) && isBlack(i)) {
				r[ir++] = i;
			}
		}
		return Arrays.copyOf(r, ir);
	}

	private int[] whitePieceIndexList() {
		final int[] r = new int[16];
		int ir = 0;

		for (int i = 0; i < 8 * 8; i++) {
			if (!isEmpty(i) && isWhite(i)) {
				r[ir++] = i;
			}
		}
		return Arrays.copyOf(r, ir);
	}

	public int[] pieceIndexList(final int dir) {
		if (dirToColor(dir) == BLACK) {
			return blackPieceIndexList();
		} else {
			return whitePieceIndexList();
		}
	}

	private int dirToColor(final int dir) {
		if (dir == WHITE_DIR) {
			return WHITE;
		} else if (dir == BLACK_DIR) {
			return BLACK;
		} else {
			throw new RuntimeException();
		}
	}

	public static boolean invalidCoords(final int f, final int c) {
		return f < 0 || f > 7 || c < 0 || c > 7;
	}

	public boolean isKingMoved(final int dir) {
		if (dir == WHITE_DIR) {
			return whiteKingIsMoved;
		} else if (dir == BLACK_DIR) {
			return blackKingIsMoved;
		} else {
			throw new RuntimeException();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (blackKingIsMoved ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(board);
		result = prime * result + (whiteKingIsMoved ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Node other = (Node) obj;
		if (blackKingIsMoved != other.blackKingIsMoved)
			return false;
		if (!Arrays.equals(board, other.board))
			return false;
		if (whiteKingIsMoved != other.whiteKingIsMoved)
			return false;
		return true;
	}


}
