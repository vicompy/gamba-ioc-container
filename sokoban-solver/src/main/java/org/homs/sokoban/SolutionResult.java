package org.homs.sokoban;

public class SolutionResult {

	private final int level;
	private final Mapa mapa;
	private int nodes;

	public int getNodes() {
		return nodes;
	}

	public void setNodes(final int nodes) {
		this.nodes = nodes;
	}

	public SolutionResult(final int level, final Mapa mapa) {
		super();
		this.level = level;
		this.mapa = mapa;
	}

	public int getLevel() {
		return level;
	}

	public Mapa getMapa() {
		return mapa;
	}

	public boolean isWorseThan(final SolutionResult sr) {
		if (sr == null) {
			return false;
		}
		return this.level > sr.level;
	}

	@Override
	public String toString() {
		return nodes + " nodes, level=" + level + ":" + mapa.toString();
	}

}
