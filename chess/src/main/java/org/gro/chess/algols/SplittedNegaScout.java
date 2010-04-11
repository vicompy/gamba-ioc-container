//package org.gro.chess.algols;
//
//import java.util.List;
//
//import org.gro.chess.BoardHeuristic;
//import org.gro.chess.Node;
//import org.gro.chess.movgen.MovGen;
//
//public class SplittedNegaScout implements ISearch {
//
//	private Node bestNode;
//	private long bestScore;
//	private long nodesAnalitzats;
//
//	private final int quiescentMaxDepth;
//
//
//	public SplittedNegaScout(final int quiescentMaxDepth) {
//		super();
//		this.quiescentMaxDepth = quiescentMaxDepth;
//	}
//
//	public Node search(final Node node, final int maxDepth, final int myDir) {
//		// this.bestNode = null;
//		this.bestScore = Long.MIN_VALUE;
//		this.nodesAnalitzats = 0L;
//
//		final long r = search(node, maxDepth, maxDepth, Long.MIN_VALUE, Long.MAX_VALUE, myDir, myDir);
//		System.out.println("analitzats " + nodesAnalitzats + " nodes, score="+r);
//
//		return this.bestNode;
//	}
//
//	private long search(final Node node, final int initialDepth, final int depth, long alfa, long beta,
//			final int myDir, final int maximizingDir) {
//
//
//		if (depth == 0) {
////			return BoardHeuristic.calcDiff(node, maximizingDir);
//			return quiescentAlfaBeta(node, quiescentMaxDepth, alfa, beta, myDir, maximizingDir);
//		}
//
//		final List<Node> childs = new MovGen(node, myDir).generaAllMoves();
//
//		if (childs.isEmpty()) {
//			nodesAnalitzats++;
//			return BoardHeuristic.calcDiff(node, maximizingDir);
//		}
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
//					return alfa; // cut-off
//				}
//
//				if (a >= beta) {
//					score = search(child, initialDepth, depth - 1, alfa, beta, -myDir, maximizingDir);
//					if (score < beta) {
//						beta = score;
//					}
//					if (alfa >= beta) {
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
//
//
//
//
//
//	private long quiescentAlfaBeta(final Node node, final int depth, long alfa, long beta,
//			final int myDir, final int maximizingDir) {
//
//
//		if (depth == 0) {
//			nodesAnalitzats++;
//			return BoardHeuristic.calcDiff(node, maximizingDir);
//		}
//
//		final List<Node> childs = new MovGen(node, myDir).generaAllMoves();
////		final List<Node> childs = new MovGen(node, myDir).generaMovesMatadors();
//
//		if (childs.isEmpty()) {
//			nodesAnalitzats++;
//			return BoardHeuristic.calcDiff(node, maximizingDir);
//		}
//
//		if (myDir == maximizingDir) {
//
//			for (final Node child : childs) {
//				final long score = quiescentAlfaBeta(child, depth - 1, alfa, beta, -myDir, maximizingDir);
//				if (score > alfa) {
//					alfa = score;
//				}
//				if (alfa >= beta) {
//					return alfa; // cut-off
//				}
//			}
//			return alfa; // our best move
//		} else {
//
//			for (final Node child : childs) {
//				final long score = quiescentAlfaBeta(child, depth - 1, alfa, beta, -myDir, maximizingDir);
//				if (score < beta) {
//					beta = score;
//				}
//				if (alfa >= beta) {
//					return beta; // cut-off
//				}
//			}
//			return beta; // our best move
//		}
//
//
//
//	}
//
//
//
//
//}
