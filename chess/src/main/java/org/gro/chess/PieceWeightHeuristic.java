package org.gro.chess;

public class PieceWeightHeuristic {

	public static long calc(final Node board, final int myDir) {

		long score = 0L;
		for (int i = 0; i < 8 * 8; i++) {
			if (board.isFriendlyPiece(i, myDir)) {
				score += computePieceWeigth(board.getPieceType(i));
			} else if (board.isOpponentPiece(i, myDir)) {
				score -= computePieceWeigth(board.getPieceType(i));
			}
		}

		return score;
	}

	private static long computePieceWeigth(final int pieceType) {
		switch (pieceType) {
    		case Node.PEO: return 50L;
    		case Node.TORRE: return 400L;
    		case Node.CAVALL: return 300L;
    		case Node.ALFIL: return 250L;
    		case Node.REINA: return 1000L;
    		case Node.REI: return 100000L;
		}
		return 0L;
	}
}
