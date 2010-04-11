package org.gro.chess.algols;

import java.util.List;

import org.gro.chess.BoardHeuristic;
import org.gro.chess.Node;
import org.gro.chess.algols.ISearch;
import org.gro.chess.movgen.MovGen;

public class Minimax implements ISearch {

	private Node bestNode;
	// private long bestScore;
	private long nodesAnalitzats;

	public Node search(final Node node, final int maxDepth, final int myDir) {
		this.bestNode = null;
		// this.bestScore = Long.MIN_VALUE;
		this.nodesAnalitzats = 0L;

		final long r = search(node, maxDepth, maxDepth, myDir, myDir);
		System.out.println("analitzats " + nodesAnalitzats + " nodes, score="+r);
		return this.bestNode;
	}

	private long search(final Node node, final int initialDepth, final int depth, final int myDir,
			final int maximizingDir) {

		nodesAnalitzats++;

		if (depth == 0) {
			return BoardHeuristic.calcDiff(node, maximizingDir);
		}

		final List<Node> childs = new MovGen(node, myDir).generaAllMoves();

		if (childs.isEmpty()) {
			return BoardHeuristic.calcDiff(node, maximizingDir);
		}

		long alfa = Long.MIN_VALUE;

		for (final Node child : childs) {

			final long childScore = -search(child, initialDepth, depth - 1, -myDir, maximizingDir);

			if (alfa < childScore) {
				alfa = childScore;
				if (initialDepth == depth) {
					bestNode = child;
				}
			}
		}

		return alfa;
	}

	public long analitzats() {
		return nodesAnalitzats;
	}
}
