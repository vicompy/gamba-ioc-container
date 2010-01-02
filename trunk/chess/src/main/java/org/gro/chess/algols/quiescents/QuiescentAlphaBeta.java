package org.gro.chess.algols.quiescents;

import java.util.List;

import org.gro.chess.BoardHeuristic;
import org.gro.chess.Node;
import org.gro.chess.movgen.MovGen;

public class QuiescentAlphaBeta extends AbstractQuiescentableNegaScout {

	public QuiescentAlphaBeta(final int quiescentMaxDepth) {
		super(quiescentMaxDepth);
	}

	@Override
	public long quiescentSearch(final Node node, final int depth, final long alfa, final long beta,
			final int myDir, final int maximizingDir) {

		return _quiescentSearch(node, depth, Long.MIN_VALUE, Long.MAX_VALUE, myDir, maximizingDir);
//		 return _quiescentSearch(node, depth, alfa, beta, myDir,
//		 maximizingDir);
	}

	public long _quiescentSearch(final Node node, final int depth, long alfa, long beta, final int myDir,
			final int maximizingDir) {

		if (depth == 0) {
			return BoardHeuristic.calcDiff(node, myDir);
		}

		final List<Node> childs = new MovGen(node, myDir).generaMovesMatadors();

		if (childs.isEmpty()) {
			return BoardHeuristic.calcDiff(node, maximizingDir);
		}

		nodesAnalitzats++;

		if (myDir == maximizingDir) {

			for (final Node child : childs) {
				final long score = _quiescentSearch(child,  depth - 1, alfa, beta, -myDir, maximizingDir);
				if (score > alfa) {
					alfa = score;
				}
//				if (alfa >= beta) {
//					return alfa; // cut-off
//				}
			}
			return alfa; // our best move
		} else {

			for (final Node child : childs) {
				final long score = _quiescentSearch(child,  depth - 1, alfa, beta, -myDir, maximizingDir);
				if (score < beta) {
					beta = score;
				}
//				if (alfa >= beta) {
//					return beta; // cut-off
//				}
			}
			return beta; // our best move
		}


	}

}
