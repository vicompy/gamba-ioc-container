package org.gro.chess.algols;

import java.util.List;

import org.gro.chess.BoardHeuristic;
import org.gro.chess.MovGen;
import org.gro.chess.Node;

public class NegaScout implements ISearch {

	private Node bestNode;
	private long bestScore;

	public long getBestScore() {
		return bestScore;
	}

	private long nodesAnalitzats;

	public Node search(final Node node, final int maxDepth, final int myDir) {
		this.bestNode = null;
		this.bestScore = Long.MIN_VALUE;
		this.nodesAnalitzats = 0L;

		search(node, maxDepth, maxDepth, myDir, Long.MIN_VALUE, Long.MAX_VALUE);
		System.out.println("analitzats " + nodesAnalitzats + " nodes.");
		return this.bestNode;
	}

	/**
	 * <pre>
	 * function negascout(node, depth, α, β)
	 *     if node is a terminal node or depth = 0
	 *         return the heuristic value of node
	 *     b := β                                          (* initial window is (-β, -α) *)
	 *     foreach child of node
	 *         a := -negascout (child, depth-1, -b, -α)
	 *         if a>α
	 *             α := a
	 *         if α≥β
	 *             return α                                (* Beta cut-off *)
	 *         if α≥b                                      (* check if null-window failed high*)
	 *            α := -negascout(child, depth-1, -β, -α)  (* full re-search *)
	 *            if α≥β
	 *                return α                             (* Beta cut-off *)
	 *         b := α+1                                    (* set new null window *)
	 *     return α
	 * </pre>
	 */
	private long search(final Node node, final int initialDepth, final int depth, final int myDir, long alfa,
			final long beta) {

		nodesAnalitzats++;

		if (depth == 0) {
			return BoardHeuristic.calc(node, myDir);
		}

		final MovGen movGen = new MovGen(node, myDir);
		final List<Node> childs = movGen.generaMovesMatadors();
		childs.addAll(movGen.generaMovesNoMatadors());

		long b = beta;

		for (final Node child : childs) {

			final long a = -search(child, initialDepth, depth - 1, -myDir, -b, -alfa);
			if (initialDepth == depth) {
				if (bestScore < a) {
					bestScore = a;
					bestNode = child;
				}
			}

			if (a > alfa) {
				alfa = a;
			}
			if (alfa >= beta) {
				return alfa;
			}
			if (alfa >= b) {
				alfa = -search(child, initialDepth, depth - 1, -myDir, -beta, -alfa);
				if (initialDepth == depth) {
					if (bestScore < alfa) {
						bestScore = alfa;
						bestNode = child;
					}
				}
				if (alfa >= beta) {
					return alfa;
				}
			}
			b = alfa + 1;

			// if (alfa < childScore) {
			// alfa = childScore;
			// if (initialDepth == depth) {
			// bestScore = childScore;
			// bestNode = child;
			// }
			// }

		}

		return alfa;
	}

}
