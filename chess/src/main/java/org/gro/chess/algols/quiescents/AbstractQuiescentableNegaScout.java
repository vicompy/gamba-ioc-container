package org.gro.chess.algols.quiescents;

import java.util.List;

import org.gro.chess.BoardHeuristic;
import org.gro.chess.Node;
import org.gro.chess.algols.ISearch;
import org.gro.chess.movgen.MovGen;

public abstract class AbstractQuiescentableNegaScout implements ISearch {

	private Node bestNode;
	private long bestScore;
	protected long nodesAnalitzats;
	protected final int quiescentMaxDepth;

	public AbstractQuiescentableNegaScout(final int quiescentMaxDepth) {
		this.quiescentMaxDepth = quiescentMaxDepth;
	}

	public Node search(final Node node, final int maxDepth, final int myDir) {
		this.bestScore = Long.MIN_VALUE;
		this.nodesAnalitzats = 0L;

		search(node, maxDepth, maxDepth, Long.MIN_VALUE, Long.MAX_VALUE, myDir, myDir);
		System.out.println("analitzats** " + nodesAnalitzats + " nodes.");

		return this.bestNode;
	}

	public abstract long quiescentSearch(final Node node, final int depth, final long alfa, final long beta,
			final int myDir, final int maximizingDir);

	private long search(final Node node, final int initialDepth, final int depth, long alfa, long beta,
			final int myDir, final int maximizingDir) {

		nodesAnalitzats++;

		if (depth == 0) {
			// return BoardHeuristic.calcDiff(node, maximizingDir);
			return quiescentSearch(node, quiescentMaxDepth, alfa, beta, myDir, maximizingDir);

//			return quiescentSearch(node, quiescentMaxDepth, alfa, beta, myDir, -maximizingDir);
//			return quiescentSearch(node, quiescentMaxDepth, alfa, beta, -myDir, maximizingDir);
//			return quiescentSearch(node, quiescentMaxDepth, alfa, beta, myDir, myDir);
//			return quiescentSearch(node, quiescentMaxDepth, alfa, beta, myDir, -myDir);
//			return quiescentSearch(node, quiescentMaxDepth, alfa, beta, -myDir, myDir);
		}

		final List<Node> childs = new MovGen(node, myDir).generaAllMoves();

		if (childs.isEmpty()) {
			return BoardHeuristic.calcDiff(node, maximizingDir);
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
				if (score < beta) {
					beta = score;
				}
				if (alfa >= beta) {
					return alfa; // cut-off
				}

				if (a >= beta) {
					score = search(child, initialDepth, depth - 1, alfa, beta, -myDir, maximizingDir);
					if (score < beta) {
						beta = score;
					}
					if (alfa >= beta) {
						return beta; // cut-off
					}
				}
				a = beta - 1;
			}
			return beta; // our best move
		}

	}

	public long analitzats() {
		return nodesAnalitzats;
	}

}