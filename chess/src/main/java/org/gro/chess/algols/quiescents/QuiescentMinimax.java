package org.gro.chess.algols.quiescents;

import java.util.List;

import org.gro.chess.BoardHeuristic;
import org.gro.chess.Node;
import org.gro.chess.movgen.MovGen;

public class QuiescentMinimax extends AbstractQuiescentableNegaScout {

	public QuiescentMinimax(final int quiescentMaxDepth) {
		super(quiescentMaxDepth);
	}

	@Override
	public long quiescentSearch(final Node node, final int depth, final long alfa, final long beta,
			final int myDir, final int maximizingDir) {

		if (depth == 0) {
			return BoardHeuristic.calcDiff(node, myDir);
		}

		final List<Node> childs = new MovGen(node, myDir).generaMovesMatadors();

		if (childs.isEmpty()) {
			return BoardHeuristic.calcDiff(node, myDir);
		}

		nodesAnalitzats++;

		long a = Long.MIN_VALUE;

		for (final Node child : childs) {

			final long childScore = -quiescentSearch(child, depth - 1,0,0, -myDir, maximizingDir);

			if (a < childScore) {
				a = childScore;
			}
		}

		return alfa;
	}

}
