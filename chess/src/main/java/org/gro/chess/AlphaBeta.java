package org.gro.chess;

import java.util.List;

public class AlphaBeta implements ISearch {

	private Node bestNode;
	private long bestScore;
	/* (non-Javadoc)
	 * @see org.gro.chess.ISearch#getBestScore()
	 */
	public long getBestScore() {
		return bestScore;
	}

	private long nodesAnalitzats;
	private MapHash<Node> hash;

	boolean usaHash;

	public AlphaBeta(final boolean usaHash) {
		super();
		this.usaHash = usaHash;
	}

	/* (non-Javadoc)
	 * @see org.gro.chess.ISearch#search(org.gro.chess.Node, int, int)
	 */
	public Node search(final Node node, final int maxDepth, final int myDir) {
		this.bestNode = null;
		this.bestScore = Long.MIN_VALUE;
		this.nodesAnalitzats = 0L;
		hash = new MapHash<Node>();

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

		if (childs.size() == 0) {
			return BoardHeuristic.calc(node, myDir);
		}

		for (final Node child : childs) {

//			final long childScore;
//			if (usaHash) {
//    			final MapaW<Node> exist = hash.exists(node, depth);
//    			if (exist == null || exist.getLevel() < depth) {
//
//    				childScore = -search(child, initialDepth, depth - 1, -beta, -alfa, -myDir);
//
//    				if (exist == null) {
//    					hash.put(node, depth, childScore);
//    				} else {
//    					exist.update(depth, childScore);
//    				}
//    			} else {
//    				return exist.getScore();
//    			}
//			} else {
//				childScore = -search(child, initialDepth, depth - 1, -beta, -alfa, -myDir);
//			}

			alfa = Math.max(alfa, -search(child, initialDepth, depth - 1, -beta, -alfa, -myDir));

			if (initialDepth == depth) {
				if (bestScore < alfa) {
					bestScore = alfa;
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

/*
function alphabeta(node, depth, α, β)
    (* β represents previous player best choice - doesn't want it if α would worsen it *)
    if  depth = 0 "or" node is a terminal node
        return the heuristic value of node
    foreach child of node
        α := max(α, -alphabeta(child, depth-1, -β, -α))
        (* use symmetry, -β becomes subsequently pruned α *)
        if β≤α
            break                             (* Beta cut-off *)
    return α

(* Initial call *)
alphabeta(origin, depth, -infinity, +infinity)
6454 6343 7531 5443
*/
