package algols;

import java.util.List;

import org.gro.chess.BoardHeuristic;
import org.gro.chess.MovGen;
import org.gro.chess.Node;

public class AlphaBeta implements ISearch {

	private Node bestNode;
	private long bestScore;
	private long nodesAnalitzats;

	public Node search(final Node node, final int maxDepth, final int myDir) {
		this.bestNode = null;
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

		if (childs.size() == 0) {
			return BoardHeuristic.calc(node, maximizingDir);
		}

		if (myDir == maximizingDir) {

//			if (initialDepth == depth) {
//				System.out.print("rumiant: 0%, ");
//			}
			for (final Node child : childs) {
				final long score = search(child, initialDepth, depth - 1, alfa, beta, -myDir, maximizingDir);
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
//				if (initialDepth == depth) {
//					System.out.print((childs.indexOf(child) * 100) / childs.size() + "%, ");
//				}
			}
//			if (initialDepth == depth) {
//				System.out.println("OK");
//			}
			return alfa; // our best move
		} else {

			for (final Node child : childs) {
				final long score = search(child, initialDepth, depth - 1, alfa, beta, -myDir, maximizingDir);
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
