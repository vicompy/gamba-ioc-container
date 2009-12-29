package org.gro.chess.movgen;

import java.util.ArrayList;
import java.util.List;

import org.gro.chess.Node;

public class MovGen {

	private final Node nodeBoard;
	private final List<Move> movesM;
	private final List<Move> movesNM;

	public MovGen(final Node nodeBoard, final int myDir) {
		this.nodeBoard = nodeBoard;
		this.movesM = new ArrayList<Move>();
		this.movesNM = new ArrayList<Move>();
		generaMoves(myDir);
	}

	public List<Node> generaMovesMatadors() {
		final List<Node> r = new ArrayList<Node>();

		for (final Move m : movesM) {
			final Node n = (Node) nodeBoard.clone();
			n.movePiece(m.srcIndex, m.dstIndex);
			r.add(n);
		}
		return r;
	}

	// TODO for test only
	protected List<Node> generaMovesNoMatadors() {
		final List<Node> r = new ArrayList<Node>();

		for (final Move m : movesNM) {
			final Node n = (Node) nodeBoard.clone();
			n.movePiece(m.srcIndex, m.dstIndex);
			r.add(n);
		}
		return r;
	}

	public List<Node> generaAllMoves() {
		final List<Node> r = new ArrayList<Node>();

		for (final Move m : movesM) {
			final Node n = (Node) nodeBoard.clone();
			n.movePiece(m.srcIndex, m.dstIndex);
			r.add(n);
		}

		for (final Move m : movesNM) {
			final Node n = (Node) nodeBoard.clone();
			n.movePiece(m.srcIndex, m.dstIndex);
			r.add(n);
		}

		return r;
	}

	private void generaMoves(final int myDir) {
		final int[] pieceIndexs = nodeBoard.pieceIndexList(myDir);

		for (int ii = 0; ii < pieceIndexs.length; ii++) {
			generaMovesPiece(pieceIndexs[ii], myDir);
		}
	}

	private void generaMovesPiece(final int pieceIndex, final int myDir) {
		final int piece = nodeBoard.getPieceType(pieceIndex);

		switch (piece) {
		case Node.PEO:
			peoMoving(pieceIndex, myDir);
			break;
		case Node.TORRE:
			torreMoving(pieceIndex, myDir);
			break;
		case Node.ALFIL:
			alfilMoving(pieceIndex, myDir);
			break;
		case Node.CAVALL:
			cavallMoving(pieceIndex, myDir);
			break;
		case Node.REINA:
			alfilMoving(pieceIndex, myDir);
			torreMoving(pieceIndex, myDir);
			break;
		case Node.REI:
			reiMoving(pieceIndex, myDir);
			break;
		}

	}

	private void reiMoving(final int pieceIndex, final int myDir) {

		final int[] dirx = { 1, 0, -1, 1, -1, 1, 0, -1 };
		final int[] diry = { 1, 1, 1, 0, 0, -1, -1, -1 };

		for (int idir = 0; idir < dirx.length; idir++) {
			final int af = diry[idir];
			final int ac = dirx[idir];
			final int dstf = pieceIndex / 8 + af;
			final int dstc = pieceIndex % 8 + ac;

			if (Node.invalidCoords(dstf, dstc)) {
				continue;
			}

			final int dstIndex = updateIndex(pieceIndex, af, ac);

			if (!nodeBoard.isEmpty(dstIndex)) {
				if (nodeBoard.isOpponentPiece(dstIndex, myDir)) {
					movesM.add(new Move(pieceIndex, dstIndex, true));
				}
			} else {
				movesNM.add(new Move(pieceIndex, dstIndex, false));
			}
		}

		if (this.nodeBoard.isKingMoved(myDir)) {
			// TODO verifica si es pot enrrocar
		}

	}

	private void cavallMoving(final int pieceIndex, final int myDir) {

		final int[] dirx = { 2, -2, 2, -2, 1, 1, -1, -1 };
		final int[] diry = { 1, 1, -1, -1, 2, -2, 2, -2 };

		for (int idir = 0; idir < dirx.length; idir++) {
			final int af = diry[idir];
			final int ac = dirx[idir];
			final int dstf = pieceIndex / 8 + af;
			final int dstc = pieceIndex % 8 + ac;

			if (Node.invalidCoords(dstf, dstc)) {
				continue;
			}

			final int dstIndex = updateIndex(pieceIndex, af, ac);

			if (!nodeBoard.isEmpty(dstIndex)) {
				if (nodeBoard.isOpponentPiece(dstIndex, myDir)) {
					movesM.add(new Move(pieceIndex, dstIndex, true));
				}
			} else {
				movesNM.add(new Move(pieceIndex, dstIndex, false));
			}
		}

	}

	private void alfilMoving(final int pieceIndex, final int myDir) {

		final int[] dirx = { 1, -1, 1, -1 };
		final int[] diry = { 1, 1, -1, -1 };

		for (int idir = 0; idir < dirx.length; idir++) {
			int step = 1;

			while (true) {
				final int af = diry[idir] * step;
				final int ac = dirx[idir] * step;
				final int dstf = pieceIndex / 8 + af;
				final int dstc = pieceIndex % 8 + ac;

				if (Node.invalidCoords(dstf, dstc)) {
					break;
				}

				final int dstIndex = updateIndex(pieceIndex, af, ac);

				if (!nodeBoard.isEmpty(dstIndex)) {
					if (nodeBoard.isOpponentPiece(dstIndex, myDir)) {
						movesM.add(new Move(pieceIndex, dstIndex, true));
					}
					break;
				} else {
					movesNM.add(new Move(pieceIndex, dstIndex, false));
				}
				step++;
			}
		}

	}

	private void torreMoving(final int pieceIndex, final int myDir) {

		final int[] dirx = { 1, -1, 0, 0 };
		final int[] diry = { 0, 0, 1, -1 };

		for (int idir = 0; idir < dirx.length; idir++) {
			int step = 1;

			while (true) {
				final int af = diry[idir] * step;
				final int ac = dirx[idir] * step;
				final int dstf = pieceIndex / 8 + af;
				final int dstc = pieceIndex % 8 + ac;

				if (Node.invalidCoords(dstf, dstc)) {
					break;
				}

				final int dstIndex = updateIndex(pieceIndex, af, ac);

				if (!nodeBoard.isEmpty(dstIndex)) {
					if (nodeBoard.isOpponentPiece(dstIndex, myDir)) {
						movesM.add(new Move(pieceIndex, dstIndex, true));
					}
					break;
				}
				movesNM.add(new Move(pieceIndex, dstIndex, false));
				step++;
			}
		}

	}

	private void peoMoving(final int pieceIndex, final int myDir) {
		// avançament normal
		if (Node.invalidCoords(pieceIndex / 8 + myDir, pieceIndex % 8)) {
			return;
		}
		if (nodeBoard.isEmpty(updateIndex(pieceIndex, myDir, 0))) {
			movesNM.add(new Move(pieceIndex, updateIndex(pieceIndex, myDir, 0), false));
		}
		// mate per la dreta
		if (pieceIndex % 8 < 7) {
			if (nodeBoard.isOpponentPiece(updateIndex(pieceIndex, myDir, 1), myDir)) {
				movesM.add(new Move(pieceIndex, updateIndex(pieceIndex, myDir, 1), true));
			}
		}
		// mate per l'esquerra
		if (pieceIndex % 8 > 0) {
			if (nodeBoard.isOpponentPiece(updateIndex(pieceIndex, myDir, -1), myDir)) {
				movesM.add(new Move(pieceIndex, updateIndex(pieceIndex, myDir, -1), true));
			}
		}
		// if en sortida, avança 2
		if (myDir == Node.WHITE_DIR && pieceIndex / 8 == 6 || myDir == Node.BLACK_DIR && pieceIndex / 8 == 1) {
			if (nodeBoard.isEmpty(updateIndex(pieceIndex, myDir, 0))
					&& nodeBoard.isEmpty(updateIndex(pieceIndex, myDir * 2, 0))) {
				movesNM.add(new Move(pieceIndex, updateIndex(pieceIndex, myDir * 2, 0), false));
			}
		}
	}

	public static int updateIndex(final int index, final int af, final int ac) {
		return index + ac + af * 8;
	}

	protected static class Move {
		public int srcIndex;
		public int dstIndex;
		public boolean isMatador;
//		public boolean isPromotion; //TODO
//		public boolean isCastling;  //TODO
		public long killedPieceWeight;

		public Move(final int srcIndex, final int dstIndex, final boolean isMatador) {
			super();
			this.srcIndex = srcIndex;
			this.dstIndex = dstIndex;
			this.isMatador = isMatador;
		}

	}

}
