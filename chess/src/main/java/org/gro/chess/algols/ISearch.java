package org.gro.chess.algols;

import org.gro.chess.Node;

public interface ISearch {

	public abstract Node search(final Node node, final int maxDepth, final int myDir);

}