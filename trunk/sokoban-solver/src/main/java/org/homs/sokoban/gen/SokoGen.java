package org.homs.sokoban.gen;

import org.homs.sokoban.MapHash;
import org.homs.sokoban.Mapa;
import org.homs.sokoban.MapaGen;
import org.homs.sokoban.SolutionResult;
import org.homs.sokoban.Solver;

public class SokoGen {

	private final MapHash hash = new MapHash();
//	private final Mapa m;

	public SokoGen() {
//		final String l = "" +
//		"######\n"+
//		"## ..#\n"+
//		"##$@ #\n"+
//		"#  $ #\n"+
//		"#   ##\n"+
//		"######\n"+
//		"\n";
//		this.m = new Mapa(l);
	}

	SolutionResult best = null;

	public void generate(final Mapa mapa) {
		final int cols = mapa.getCols();
		final int rows = mapa.getRows();

		if (hash.cuttable(new MapaGen(mapa), 5)) {
			return;
		}

		final SolutionResult sr = new Solver(mapa, new MapHash()).solve(14);
		if (sr == null) {
			return;
		}

		if (best == null || !best.isWorseThan(sr)) {
			this.best = sr;
			System.out.println(sr.toString());
			System.out.println(mapa.toString());
		}

//		if (mapa.equals(this.m)) {
//			System.out.println("***************************************************");
//		}

//		if (sr.getLevel() > 8) {
//			System.out.println(sr.toString());
//			System.out.println(mapa.toString());
//		}

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
}
