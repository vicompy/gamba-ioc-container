package org.gro.chess.algols;

import java.util.List;

import org.gro.chess.BoardHeuristic;
import org.gro.chess.MovGen;
import org.gro.chess.Node;

public class NegaScout implements ISearch {

	private Node bestNode;
	private long bestScore;
	private long nodesAnalitzats;

	public Node search(final Node node, final int maxDepth, final int myDir) {
		// this.bestNode = null;
		this.bestScore = Long.MIN_VALUE;
		this.nodesAnalitzats = 0L;

		search(node, maxDepth, maxDepth, Long.MIN_VALUE, Long.MAX_VALUE, myDir, myDir);
		System.out.println("analitzats* " + nodesAnalitzats + " nodes.");

		return this.bestNode;
	}

	private long search(final Node node, final int initialDepth, final int depth, long alfa, long beta,
			final int myDir, final int maximizingDir) {

		nodesAnalitzats++;

		if (depth == 0) {
			return BoardHeuristic.calc(node, maximizingDir);
		}

		final MovGen movGen = new MovGen(node, myDir);
		final List<Node> childs = movGen.generaMovesMatadors();
		childs.addAll(movGen.generaMovesNoMatadors());

		if (childs.isEmpty()) {
			return BoardHeuristic.calc(node, maximizingDir);
		}

		long a = alfa;
		long b = beta;
		long score;

		if (myDir == maximizingDir) {

			for (final Node child : childs) {
				score = search(child, initialDepth, depth - 1, alfa, b, -myDir, maximizingDir);
				if (initialDepth == depth) {
					if (bestScore < score) {
						bestScore = score;
						bestNode = child;
					}
				}
				if (score > alfa) {
					alfa = score;
				}
				if (alfa >= beta) {
					return alfa; // cut-off
				}
				if (alfa >= b) {
					score = search(child, initialDepth, depth - 1, alfa, beta, -myDir, maximizingDir);
					if (initialDepth == depth) {
						if (bestScore < score) {
							bestScore = score;
							bestNode = child;
						}
					}
					if (score > alfa) {
						alfa = score;
					}
					if (alfa >= beta) {
						return alfa; // cut-off
					}
				}
				b = alfa + 1;
			}
			return alfa; // our best move
		} else {

			for (final Node child : childs) {

				score = search(child, initialDepth, depth - 1, a, beta, -myDir, maximizingDir);
				if (initialDepth == depth) {
					if (bestScore < score) {
						bestScore = score;
						bestNode = child;
					}
				}
				if (score < beta) {
					beta = score;
				}
				if (alfa >= beta) {
					return alfa; // cut-off
				}

				if (a >= beta) {
					score = search(child, initialDepth, depth - 1, alfa, beta, -myDir, maximizingDir);
					if (initialDepth == depth) {
						if (bestScore < score) {
							bestScore = score;
							bestNode = child;
						}
					}
					if (score < beta) {
						beta = score;
					}
					if (alfa >= beta) {
						return beta; // cut-off
					}
				}
//				a = alfa + 1; //320440
//				a = alfa - 1; //280574
				a = beta - 1; //253837, i tb 1319065 vs. 818703
//				a = beta + 1; //347632
			}
			return beta; // our best move
		}

	}

	public long analitzats() {
		return nodesAnalitzats;
	}
}
