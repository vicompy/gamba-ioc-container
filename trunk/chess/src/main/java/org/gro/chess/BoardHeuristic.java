package org.gro.chess;

import java.util.List;

import org.gro.chess.MovGen.Move;

public class BoardHeuristic {

	public static long calc(final Node board, final int myDir) {

		long score = 0L;
		for (int i = 0; i < 8 * 8; i++) {
			if (board.isFriendlyPiece(i, myDir)) {
				score += computePieceWeigth(board.getPieceType(i));
			} else if (board.isOpponentPiece(i, myDir)) {
				score -= computePieceWeigth(board.getPieceType(i));
			}
		}
		final MovGen movGen = new MovGen(board, myDir);
		final List<Move> moves = movGen.getMoves();

		for (final Move m : moves) {
			if (m.isMatador) {
				score += computePieceWeigth(board.getPieceType(m.dstIndex)) / 5;
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
    		case Node.REINA: return 2000L;
    		case Node.REI: return 100000L;
		}
		return 0L;
	}
}

/*
 * 6343
 * 6454
 * 7655
 * 7553
 * 5543
 * <-- es deixa matar la reina
 * 5443
 *
 *
 *
 *
 */
