package org.homs.sokoban;

import java.util.ArrayList;
import java.util.List;

public class MapaGen extends Mapa {

	public MapaGen(final String textLevel) {
		super(textLevel);
	}

	public MapaGen(final Mapa mapa) {
		super(mapa);
	}

	protected MapaGen(final MapaGen mapaGen, final int indexOrgBox, final int indexDstBox) {
		super(mapaGen, indexOrgBox, indexDstBox);
	}

	private MapaGen doMove(final int indexOrgBox, final int indexDstBox) {
		return new MapaGen(this, indexOrgBox, indexDstBox);
	}

	private final int[] dirs = new int[] { -1, 1, super.cols, -super.cols };

	public List<MapaGen> moveGen() {

		final List<MapaGen> r = new ArrayList<MapaGen>();

		for (final int boxPos : super.boxList) {
			for (final int dir : dirs) {
				if (super.accMap[boxPos - dir] && boxableCell(boxPos + dir)) {
					r.add(doMove(boxPos, boxPos + dir));
				}
			}

		}

		return r;
	}

	private boolean boxableCell(final int index) {
		return super.map[index] == ' ' || super.map[index] == '.' || super.map[index] == '+'
				|| super.map[index] == '@';
	}

	public boolean isSolved() {
		for (final int boxPos : super.boxList) {
			if (super.map[boxPos] != '*') {
				return false;
			}
		}
		return true;
	}

}
