package algols;

import java.util.List;

import org.gro.chess.BoardHeuristic;
import org.gro.chess.MovGen;
import org.gro.chess.Node;
import org.gro.chess.algols.ISearch;

public class Minimax implements ISearch {

	private Node bestNode;
//	private long bestScore;
	private long nodesAnalitzats;

	public Node search(final Node node, final int maxDepth, final int myDir) {
		this.bestNode = null;
//		this.bestScore = Long.MIN_VALUE;
		this.nodesAnalitzats = 0L;

		search(node, maxDepth, maxDepth, myDir);
		System.out.println("analitzats " + nodesAnalitzats + " nodes.");
		return this.bestNode;
	}

	private long search(final Node node, final int initialDepth, final int depth, final int myDir) {

		nodesAnalitzats++;

		if (depth == 0) {
			return BoardHeuristic.calc(node, myDir);
		}

		final MovGen movGen = new MovGen(node, myDir);
		final List<Node> childs = movGen.generaMovesMatadors();
		childs.addAll(movGen.generaMovesNoMatadors());

		long alfa = Long.MIN_VALUE;

		for (final Node child : childs) {

			final long childScore;
			childScore = -search(child, initialDepth, depth - 1, -myDir);

			if (alfa < childScore) {
				alfa = childScore;
				if (initialDepth == depth) {
//					bestScore = childScore;
					bestNode = child;
				}
			}

		}

		return alfa;
	}

}
