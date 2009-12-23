package algols;
//package org.gro.chess.algols;
//
//import java.util.List;
//
//import org.gro.chess.BoardHeuristic;
//import org.gro.chess.MovGen;
//import org.gro.chess.Node;
//
///**
// * <h3>Negascout (Principal Variation Search)</h3>
// *
// * <pre>
// * function negascout(node, depth, α, β)
// *     if node is a terminal node or depth = 0
// *         return the heuristic value of node
// *     b := β                                          (* initial window is (-β, -α) *)
// *     foreach child of node
// *         a := -negascout (child, depth-1, -b, -α)
// *         if a>α
// *             α := a
// *         if α≥β
// *             return α                                (* Beta cut-off *)
// *         if α≥b                                      (* check if null-window failed high*)
// *            α := -negascout(child, depth-1, -β, -α)  (* full re-search *)
// *            if α≥β
// *                return α                             (* Beta cut-off *)
// *         b := α+1                                    (* set new null window *)
// *     return α
// * </pre>
// *
// * @author mhoms
// */
//public class NegaScout implements ISearch {
//
//	private Node bestNode;
//	private long bestScore;
//
//	public long getBestScore() {
//		return bestScore;
//	}
//
//	private long nodesAnalitzats;
//
//	public Node search(final Node node, final int maxDepth, final int myDir) {
//		this.bestNode = null;
//		this.bestScore = Long.MIN_VALUE;
//		this.nodesAnalitzats = 0L;
//
//		search(node, maxDepth, maxDepth, myDir, Long.MIN_VALUE, Long.MAX_VALUE);
//		System.out.println("analitzats** " + nodesAnalitzats + " nodes.");
//		return this.bestNode;
//	}
//
//	/**
//	 * <h3>Negascout (Principal Variation Search)</h3>
//	 *
//	 * <pre>
//	 * function negascout(node, depth, α, β)
//	 *     if node is a terminal node or depth = 0
//	 *         return the heuristic value of node
//	 *     b := β                                          (* initial window is (-β, -α) *)
//	 *     foreach child of node
//	 *         a := -negascout (child, depth-1, -b, -α)
//	 *         if a>α
//	 *             α := a
//	 *         if α≥β
//	 *             return α                                (* Beta cut-off *)
//	 *         if α≥b                                      (* check if null-window failed high*)
//	 *            α := -negascout(child, depth-1, -β, -α)  (* full re-search *)
//	 *            if α≥β
//	 *                return α                             (* Beta cut-off *)
//	 *         b := α+1                                    (* set new null window *)
//	 *     return α
//	 *
//	 * </pre>
//	 *
//	 * @author mhoms
//	 */
//	private long search(final Node node, final int initialDepth, final int depth, final int myDir,
//			long alfa, final long beta) {
//
//		nodesAnalitzats++;
//
//		if (depth == 0) {
//			return BoardHeuristic.calc(node, myDir);
//		}
//
//		final MovGen movGen = new MovGen(node, myDir);
//		final List<Node> childs = movGen.generaMovesMatadors();
//		childs.addAll(movGen.generaMovesNoMatadors());
//
//		long b = beta;
//
//		final int i = 0;
//		for (final Node child : childs) {
//
//			final long a = search(child, initialDepth, depth - 1, -myDir, -b, -alfa);
//			if (initialDepth == depth) {
//				if (bestScore < a) {
//					bestScore = a;
//					bestNode = child;
//				}
//			}
//
//			if (a > alfa) {
//				alfa = a;
//			}
//			if (alfa >= beta) {
//				return alfa;
//			}
//			if (alfa >= b) {
//				alfa = search(child, initialDepth, depth - 1, -myDir, -beta, -alfa);
//				if (initialDepth == depth) {
//					if (bestScore < alfa) {
//						bestScore = alfa;
//						bestNode = child;
//					}
//				}
//				if (alfa >= beta) {
//					return alfa;
//				}
//			}
//			b = alfa + 1;
////			i++;
//		}
//
//		return alfa;
//	}
//
//}
//
//// /**
//// * <pre>
//// *
//// * int NegaScout ( position p; int alpha, beta )
//// * { * compute minimax value of position p *
//// * int b, t, i;
//// * if ( d == maxdepth )
//// * return quiesce(p, alpha, beta); * final leaf node *
//// * final determine successors p_1,...,p_w final of p;
//// * b = beta;
//// * for ( i = 1; i <= w; i++ ) {
//// *
//// * t = -NegaScout ( p_i, -b, -alpha );
//// * if ( (t > a) && (t < beta) && (i > 1) )
//// * t = -NegaScout ( p_i, -beta, -alpha ); * re-search *
//// * alpha = max( alpha, t );
//// * if ( alpha >= beta )
//// * return alpha; * cut-off *
//// * b = alpha + 1; * set new null window *
//// *
//// * }
//// * return alpha;
//// * }
//// * </pre>
//// */
//// final long t = -search(child, initialDepth, depth - 1, -myDir, -b, -alfa);
//// if (initialDepth == depth) {
//// if (bestScore < t) {
//// bestScore = t;
//// bestNode = child;
//// }
//// }
//// if (t > a && t < beta && ichild > 0) {
//// alfa = -search(child, initialDepth, depth - 1, -myDir, -beta, -alfa);
//// if (initialDepth == depth) {
//// if (bestScore < alfa) {
//// bestScore = alfa;
//// bestNode = child;
//// }
//// }
//// }
////
//// alfa = Math.max(alfa, t);
//// if (alfa >= beta) {
//// return alfa;
//// }
//// b = alfa + 1;
