package org.gro.chess;

import java.util.List;

public class AlphaBeta2Hash implements ISearch {

	private Node bestNode;
	private long bestScore;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.gro.chess.ISearch#getBestScore()
	 */
	public long getBestScore() {
		return bestScore;
	}

	private long nodesAnalitzats;
	private MapHash<Node> hash;

	boolean usaHash;

	public AlphaBeta2Hash(final boolean usaHash) {
		super();
		this.usaHash = usaHash;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.gro.chess.ISearch#search(org.gro.chess.Node, int, int)
	 */
	public Node search(final Node node, final int maxDepth, final int myDir) {
		this.bestNode = null;
		this.bestScore = Long.MIN_VALUE;
		this.nodesAnalitzats = 0L;
		hash = new MapHash<Node>();

		search(node, maxDepth, maxDepth, Long.MIN_VALUE, Long.MAX_VALUE, myDir, myDir);
		System.out.println("analitzats** " + nodesAnalitzats + " nodes.");

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

		if (childs.size() == 0) {
			return BoardHeuristic.calc(node, maximizingDir);
		}

		if (myDir == maximizingDir) {

			for (final Node child : childs) {

				//final long score = search(child, initialDepth, depth - 1, alfa, beta, -myDir, maximizingDir);

				final MapaW<Node> exist = hash.exists(node);
				long score;
				if (exist == null || exist.getLevel() > depth) {

					score = search(child, initialDepth, depth - 1, alfa, beta, -myDir, maximizingDir);

					if (exist == null) {
						hash.put(node, depth, score);
					} else {
						exist.update(depth, score);
					}
				} else {
//					return exist.getScore();
					score = exist.getScore();
				}

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
			return alfa; // our best move
		} else {

			for (final Node child : childs) {
				final long score = search(child, initialDepth, depth - 1, alfa, beta, -myDir, maximizingDir);

				// final MapaW<Node> exist = hash.exists(node, depth);
				// final long score;
				// if (exist == null || exist.getLevel() < depth) {
				//
				// score = search(child, initialDepth, depth - 1, alfa, beta,
				// -myDir, maximizingDir);
				//
				// if (exist == null) {
				// hash.put(node, depth, score);
				// } else {
				// exist.update(depth, score);
				// }
				// } else {
				// return exist.getScore();
				// }

				if (score < beta) {
					beta = score;
				}
				if (alfa >= beta) {
					return beta; // cut-off
				}
			}
			return beta; // our best move
		}

	}

}
