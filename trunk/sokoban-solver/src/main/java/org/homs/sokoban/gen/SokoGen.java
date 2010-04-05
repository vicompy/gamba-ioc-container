package org.homs.sokoban.gen;

import org.homs.sokoban.MapHash;
import org.homs.sokoban.Mapa;
import org.homs.sokoban.MapaGen;
import org.homs.sokoban.SolutionResult;
import org.homs.sokoban.Solver;

public class SokoGen {

	private final MapHash hash = new MapHash();
	private SolutionResult best = null;
	private int checked = 0;

	public void generate(final Mapa mapa) {
		final int cols = mapa.getCols();
		final int rows = mapa.getRows();

		// TODO simplificació de mapes experimental: la hash fundeix! de 1360 a 374 mapes explorats!!
		final Mapa mapa2 = new Mapa(mapa);
		for (int n = 0; n < 4; n++)
		for (int f = 1; f < rows - 1; f++) {
			for (int c = 1; c < cols - 1; c++) {

				if (mapa2.getMap()[c+f*mapa2.getCols()] == ' ') {
					int numWalls = 0;
					if (mapa2.getMap()[(c+1)+f*mapa2.getCols()] == '#') numWalls++;
					if (mapa2.getMap()[(c-1)+f*mapa2.getCols()] == '#') numWalls++;
					if (mapa2.getMap()[c+(f+1)*mapa2.getCols()] == '#') numWalls++;
					if (mapa2.getMap()[c+(f-1)*mapa2.getCols()] == '#') numWalls++;
					if (numWalls >= 3) {
						mapa2.getMap()[c+f*mapa2.getCols()] = '#';
					}
				}
			}
		}

		if (hash.cuttable(new MapaGen(mapa2), 5)) {
			return;
		}

		checked++;
		final SolutionResult sr = new Solver(mapa, new MapHash()).solve(22);
		if (sr == null) {
			return;
		}

		if (best == null || !best.isWorseThan(sr)) {
			this.best = sr;
			System.out.println(sr.toString());
			System.out.println(mapa2.toString());
		}

		for (int f = 1; f < rows - 1; f++) {
			for (int c = 1; c < cols - 1; c++) {

				final int index = c + f * cols;
				if (mapa.getMap()[index] == ' ') {
					mapa.getMap()[index] = '#';
					generate(new Mapa(mapa));
					mapa.getMap()[index] = ' ';
				}
			}
		}

	}

	public int getChecked() {
		return checked;
	}


}
