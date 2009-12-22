package org.gro.chess;

import java.util.List;

import org.gro.chess.MovGen.Move;

public class BoardHeuristic {

	public static long calc(final Node board, final int myDir) {

		// evalua qualitat del material de cada jugador
		long score = 0L;
		for (int i = 0; i < 8 * 8; i++) {
			if (board.isFriendlyPiece(i, myDir)) {
				score += computePieceWeigth(board.getPieceType(i));
			}
		}

		// evalua la quantitat d'amenaçes que es fa al contrari
		{
			final MovGen movGen = new MovGen(board, myDir);
			final List<Move> moves = movGen.getMoves();
			for (final Move m : moves) {
				if (m.isMatador) {
					score += computePieceWeigth(board.getPieceType(m.dstIndex)) / 5;
				}
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
			return 250L;
		case Node.REINA:
			return 2000L;
		case Node.REI:
			return 100000L;
		}
		return 0L;
	}
}

// 6343 6454 7655 6050 5534 3453 6656 5444 7245 (l'A blanc amenaça un buit en
// defensa negra, que respon OK)
// (cont;) 7152 (el C avançat amenaça la Q negra, que la fa retrocedir!) 7346
// (treu Q blanca en escena, i la T negra fa un moviment estúpid)

