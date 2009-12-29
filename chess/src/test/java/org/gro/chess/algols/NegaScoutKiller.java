//package org.gro.chess.algols;
//
//import java.util.List;
//
//import org.gro.chess.BoardHeuristic;
//import org.gro.chess.Node;
//import org.gro.chess.movgen.MovGen;
//
//public class NegaScoutKiller implements ISearch {
//
//	private Node bestNode;
//	private long bestScore;
//	private long nodesAnalitzats;
//
//	private final int[][][] maximizingHistory;
//	private final int[][][] minimizingHistory;
//	private final int maxDepth;
//
//	public NegaScoutKiller(final int maxDepth) {
//		this.maxDepth = maxDepth;
//
//		maximizingHistory = new int[this.maxDepth+1][8 * 8][8 * 8];
//		minimizingHistory = new int[this.maxDepth+1][8 * 8][8 * 8];
//
//		for (int d = 0; d < this.maxDepth; d++) {
//			for (int src = 0; src < 8 * 8; src++) {
//				for (int dst = 0; dst < 8 * 8; dst++) {
//					maximizingHistory[d][src][dst] = 0;
//					minimizingHistory[d][src][dst] = 0;
//				}
//			}
//		}
//	}
//
//	public Node search(final Node node, final int maxDepth/* TODO arg ignored */, final int myDir) {
//		// this.bestNode = null;
//		this.bestScore = Long.MIN_VALUE;
//		this.nodesAnalitzats = 0L;
//
//		// for (int src = 0; src < 8*8; src++) {
//		// for (int dst = 0; dst < 8*8; dst++) {
//		// maximizingHistory[src][dst] = 0;
//		// minimizingHistory[src][dst] = 0;
//		// }
//		// }
//
//		search(node, this.maxDepth, this.maxDepth, Long.MIN_VALUE, Long.MAX_VALUE, myDir, myDir);
//		System.out.println("analitzats* " + nodesAnalitzats + " nodes.");
//
//		return this.bestNode;
//	}
//
//	private long search(final Node node, final int initialDepth, final int depth, long alfa, long beta,
//			final int myDir, final int maximizingDir) {
//
//		nodesAnalitzats++;
//
//		if (depth == 0) {
//			return BoardHeuristic.calcDiff(node, maximizingDir);
//		}
//
//		final List<Node> childs;
//
//		if (myDir == maximizingDir) {
////			System.out.println(depth+" -- "+maximizingHistory.length);
//			childs = new MovGen(node, myDir).generaAllMoves(maximizingHistory[depth]);
//		} else {
//			childs = new MovGen(node, myDir).generaAllMoves(minimizingHistory[depth]);
//		}
//
//		if (childs.isEmpty()) {
//			return BoardHeuristic.calcDiff(node, maximizingDir);
//		}
//
//		// // ordena els moves segons l'heurística històrica
//		// final List<Node> childs = new ArrayList<Node>();
//
//		long a = alfa;
//		long b = beta;
//		long score;
//
//		if (myDir == maximizingDir) {
//
//			for (final Node child : childs) {
//				score = search(child, initialDepth, depth - 1, alfa, b, -myDir, maximizingDir);
//				if (initialDepth == depth) {
//					if (bestScore < score) {
//						bestScore = score;
//						bestNode = child;
//					}
//				}
//				if (score > alfa) {
//					alfa = score;
//				}
//				if (alfa >= beta) {
//					maximizingHistory[depth][child.getLastMoveSrc()][child.getLastMoveDst()]++;
//					return alfa; // cut-off
//				}
//				if (alfa >= b) {
//					score = search(child, initialDepth, depth - 1, alfa, beta, -myDir, maximizingDir);
//					if (initialDepth == depth) {
//						if (bestScore < score) {
//							bestScore = score;
//							bestNode = child;
//						}
//					}
//					if (score > alfa) {
//						alfa = score;
//					}
//					if (alfa >= beta) {
//						maximizingHistory[depth][child.getLastMoveSrc()][child.getLastMoveDst()]++;
//						return alfa; // cut-off
//					}
//				}
//				b = alfa + 1;
//			}
//			return alfa; // our best move
//		} else {
//
//			for (final Node child : childs) {
//
//				score = search(child, initialDepth, depth - 1, a, beta, -myDir, maximizingDir);
//				if (score < beta) {
//					beta = score;
//				}
//				if (alfa >= beta) {
//					minimizingHistory[depth][child.getLastMoveSrc()][child.getLastMoveDst()]++;
//					return alfa; // cut-off
//				}
//
//				if (a >= beta) {
//					score = search(child, initialDepth, depth - 1, alfa, beta, -myDir, maximizingDir);
//					if (score < beta) {
//						beta = score;
//					}
//					if (alfa >= beta) {
//						minimizingHistory[depth][child.getLastMoveSrc()][child.getLastMoveDst()]++;
//						return beta; // cut-off
//					}
//				}
//				a = beta - 1;
//			}
//			return beta; // our best move
//		}
//
//	}
//
//	public long analitzats() {
//		return nodesAnalitzats;
//	}
//}
