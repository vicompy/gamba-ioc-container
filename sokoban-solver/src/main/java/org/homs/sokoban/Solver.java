package org.homs.sokoban;

import java.util.List;

public class Solver {

	private final MapaGen mapaGen;
	private int maxLevel;
	private int nodes;
	private final IMapHash mapHash;
	private final LockDetector lockDetector;

	public Solver(final String textLevel, final IMapHash mapHash) {
		this.mapaGen = new MapaGen(textLevel);
		this.mapHash = mapHash;
		this.lockDetector = new LockDetector(this.mapaGen);
	}

	public Solver(final Mapa mapa, final IMapHash mapHash) {
		this.mapaGen = new MapaGen(mapa);
		this.mapHash = mapHash;
		this.lockDetector = new LockDetector(this.mapaGen);
	}

	public SolutionResult solve(final int maxLevel) {
		this.nodes = 0;
		this.maxLevel = maxLevel;
		final SolutionResult sr = solve(0, this.mapaGen);
		if (sr != null) {
			sr.setNodes(nodes);
		}
		return sr;
	}

	private SolutionResult solve(final int level, final MapaGen mapaGen) {

		this.nodes++;

		if (mapaGen.isSolved()) {
//			System.out.println("solved at level=" + level);
			this.maxLevel = level - 1;
			return new SolutionResult(level, mapaGen);
		}

		if (level >= maxLevel) {
			return null;
		}

		if (mapHash.cuttable(mapaGen, level)) {
			return null;
		}

		for (final int boxPos : mapaGen.boxList) {
			if (lockDetector.getIsLockedPos()[boxPos]) {
				return null;
			}
		}

		final List<MapaGen> mapaGenList = mapaGen.moveGen();

		SolutionResult bestSolutionResult = null;
		for (final MapaGen m : mapaGenList) {
			final SolutionResult sr = solve(level + 1, m);
			if (bestSolutionResult != null) {
				if (bestSolutionResult.isWorseThan(sr)) {
					bestSolutionResult = sr;
				}
			} else {
				bestSolutionResult = sr;
			}
		}

		return bestSolutionResult;
	}

	// public int getNodes() {
	// return nodes;
	// }

}
