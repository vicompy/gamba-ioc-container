package org.gro.chess;

import java.util.List;

import org.gro.chess.MovGen.Move;

public class BoardHeuristic {

	public static long calc(final Node board, final int myDir) {
		return calque(board, myDir) - calque(board, -myDir);
	}
	private static long calque(final Node board, final int myDir) {

		long score = 0L;

		// evalua qualitat del material de cada jugador
		for (int i = 0; i < 8 * 8; i++) {
			if (board.isFriendlyPiece(i, myDir)) {
				score += computePieceWeigth(board.getPieceType(i));
			}
		}

		// // final evalua la quantitat d'amenaçes que es fa al contrari
		// {
		// final MovGen movGen = new MovGen(board, myDir);
		// final List<Move> moves = movGen.getMoves();
		// for (final Move m : moves) {
		// if (m.isMatador) {
		// score += computePieceWeigth(board.getPieceType(m.dstIndex)) / 10;
		// }
		// }
		// }

		// evalua la bona formació peonil
		{
			long pawnScore = 0L;
			for (int i = 8; i < 8 * 8 - 8; i++) {
				if (board.isFriendlyPawn(i, myDir)) {
					if (i % 8 > 0 && board.isFriendlyPawn(MovGen.updateIndex(i, myDir, -1), myDir)) {
						pawnScore += 1;
					}
					if (i % 8 < 7 && board.isFriendlyPawn(MovGen.updateIndex(i, myDir, 1), myDir)) {
						pawnScore += 1;
					}
				}
			}
			// System.out.println("pawnScore="+pawnScore);
			score += pawnScore;
		}

		{
			long openScore = 0L;
			for (int i = 0; i < 8 * 8; i++) {
				if (board.isFriendlyPiece(i, myDir)
						&& (board.getPieceType(i) == Node.ALFIL || board.getPieceType(i) == Node.CAVALL)) {
					if (i / 7 > 0 && i / 7 < 7) {
						openScore += 1;
					}
				}
			}
			score += openScore;
		}

		if (myDir == Node.BLACK_DIR) {
			if (!board.isFriendlyPawn(8 * 1 + 3, myDir)) {
				score += 1;
			}
			if (!board.isFriendlyPawn(8 * 1 + 4, myDir)) {
				score += 1;
			}
		} else {
			if (!board.isFriendlyPawn(8 * 6 + 3, myDir)) {
				score += 1;
			}
			if (!board.isFriendlyPawn(8 * 6 + 4, myDir)) {
				score += 1;
			}
		}

		return score;
	}

	private static long computePieceWeigth(final int pieceType) {
		switch (pieceType) {
		case Node.PEO:
			return 50L;
		case Node.TORRE:
			return 400L;
		case Node.CAVALL:
			return 300L;
		case Node.ALFIL:
			return 300L;
		case Node.REINA:
			return 2000L;
		case Node.REI:
			return 100000L;
		}
		return 0L;
	}
}
