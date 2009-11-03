package org.homs.sokoban;

import java.util.List;

public class Solver {

    private final MapaGen mapaGen;
    private int maxLevel;
    private int nodes;

    public int getNodes() {
        return nodes;
    }

    public Solver(final String l) {
	this.mapaGen = new MapaGen(l);
    }

    public SolutionResult solve(final int maxLevel) {
	this.nodes = 0;
	this.maxLevel = maxLevel;
	final SolutionResult sr = solve(0, this.mapaGen);
	sr.setNodes(nodes);
	return sr;
    }

    private SolutionResult solve(final int level, final MapaGen mapaGen) {
	this.nodes++;
//	System.out.println(mapaGen.toString());

	if (mapaGen.isSolved()) {
	    System.out.println("solved at level=" + level);
	    this.maxLevel = level - 1;
//	    System.exit(1);

	    return new SolutionResult(level, mapaGen);
	}

	if (level >= maxLevel) {
	    return null;
	}


	final List<MapaGen> mapaGenList = mapaGen.moveGen();
	SolutionResult bestSolutionResult = null;
	for(final MapaGen m : mapaGenList) {
	    final SolutionResult sr = solve(level+1, m);
	    if (bestSolutionResult != null) {
		if (bestSolutionResult.isWorseThan(sr)) { //TODO millorar expressió
		    bestSolutionResult = sr;
		}
	    } else {
		bestSolutionResult = sr;
	    }
	}

	return bestSolutionResult;

    }
}
