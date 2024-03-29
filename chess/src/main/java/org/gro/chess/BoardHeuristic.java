package org.gro.chess;

import org.gro.chess.movgen.MovGen;

/**
 * classe que estàticament, proporciona mètodes per a l'evaluació heurística de
 * taulells.
 *
 * @author mhoms
 */
public final class BoardHeuristic {

	private BoardHeuristic() {
	}

	/**
	 * calcula la puntuació de l'estat del taulell, per a un bàndol especificat,
	 * per simetria; doncs retorna la difarància de puntuacions d'un i altre
	 * bàndol.
	 *
	 * @param board objecte que codifica l'estat del taulell
	 * @param myDir indica el bàndol pel que evaluar la situació. (veure
	 *            {@link Node#WHITE_DIR} i {@link Node#BLACK_DIR}).
	 * @return la puntuació, millor com més positiva (major)
	 */
	public static long calcDiff(final Node board, final int myDir) {
		return calque(board, myDir) - calque(board, -myDir);
	}

	/**
	 * evalua l'estat de taulell per a un bàndol especificat, sense comptar la
	 * del bandol oposat.
	 *
	 * @param board objecte que codifica l'estat del taulell
	 * @param myDir indica el bàndol pel que evaluar la situació. (veure
	 *            {@link Node#WHITE_DIR} i {@link Node#BLACK_DIR}).
	 * @return la puntuació, millor com més positiva (major)
	 */
	private static long calque(final Node board, final int myDir) {

		long score = 0L;

		// evalua qualitat del material de cada jugador
		for (int i = 0; i < 8 * 8; i++) {
			if (board.isFriendlyPiece(i, myDir)) {
				score += computePieceWeigth(board.getPieceType(i));
			}
		}

		// bonifica els alfils i cavalls que han abandonat la línia de reserva
		// bonifica les torres i rei mantinguts en línia de reserva
		for (int i = 0; i < 8 * 8; i++) {
			if (board.isFriendlyPiece(i, myDir)) {
				if (board.getPieceType(i) == Node.ALFIL || board.getPieceType(i) == Node.CAVALL) {
					if (myDir == Node.BLACK_DIR && i / 7 > 0 || myDir == Node.WHITE_DIR && i / 7 < 7) {
						score += 1;
					}
				}
				if (board.getPieceType(i) == Node.TORRE || board.getPieceType(i) == Node.REI) {
					if (myDir == Node.BLACK_DIR && i / 7 == 0 || myDir == Node.WHITE_DIR && i / 7 == 7) {
						score += 1;
					}
				}
			}
		}

		// bonifica la bona formació peonil
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
		score += pawnScore;

		// bonifica l'obertura central de peons
		if (myDir == Node.BLACK_DIR) {
			for (int i = 0; i < 8; i++) {
				if (i == 3 || i == 4) {
					if (!board.isFriendlyPawn(8 * 1 + i, myDir)) {
						score += 2;
					}
				} else {
					if (board.isFriendlyPawn(8 * 1 + i, myDir)) {
						score += 2;
					}
				}
			}
		} else {
			for (int i = 0; i < 8; i++) {
				if (i == 3 || i == 4) {
					if (!board.isFriendlyPawn(8 * 6 + i, myDir)) {
						score += 2;
					}
				} else {
					if (board.isFriendlyPawn(8 * 6 + i, myDir)) {
						score += 2;
					}
				}
			}
		}

		return score;
	}

	/**
	 * calcula el valor (en pes) de cadascuna de les peces.
	 *
	 * @param pieceType tipus de la peça (veure {@link Node#PEO},
	 *            {@link Node#CAVALL}, etc)
	 * @return el valor puntuat
	 */
	private static long computePieceWeigth(final int pieceType) {
		switch (pieceType) {
		case Node.PEO:
			return 100L;
		case Node.TORRE:
			return 300L;
		case Node.CAVALL:
			return 300L;
		case Node.ALFIL:
			return 500L;
		case Node.REINA:
			return 900L;
		default: // Node.REI
			return 100000L;
		}
	}

}
