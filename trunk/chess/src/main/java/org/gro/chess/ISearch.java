package org.gro.chess;

public interface ISearch {

	public abstract long getBestScore();

	public abstract Node search(final Node node, final int maxDepth, final int myDir);

}