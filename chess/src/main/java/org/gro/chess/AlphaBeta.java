package org.gro.chess;

import java.util.List;

public class AlphaBeta {

	private Node bestNode;
	private long bestAlfa;
	private long nodesAnalitzats;

	public Node search(final Node node, final int maxDepth, final int myDir) {
		this.bestNode = null;
		this.bestAlfa = Long.MIN_VALUE;
		this.nodesAnalitzats = 0L;

		search(node, maxDepth, maxDepth, Long.MIN_VALUE, Long.MAX_VALUE, myDir);
		System.out.println("analitzats " + nodesAnalitzats + " nodes.");
		return this.bestNode;
	}

	private long search(final Node node, final int initialDepth, final int depth, long alfa, final long beta,
			final int myDir) {

		nodesAnalitzats++;

		if (depth == 0) {
			return BoardHeuristic.calc(node, myDir);
		}

		final MovGen movGen = new MovGen(node, myDir);
		final List<Node> childs = movGen.generaMovesMatadors();
		childs.addAll(movGen.generaMovesNoMatadors());

		for (final Node child : childs) {
			final long childScore = search(child, initialDepth, depth - 1, -beta, -alfa, -myDir);
			alfa = Math.max(alfa, -childScore);

			if (initialDepth == depth) {
				if (bestAlfa < alfa) {
					bestAlfa = alfa;
					bestNode = child;
				}
			}

			if (beta <= alfa) {
				break;
			}
		}

		return alfa;
	}

}
