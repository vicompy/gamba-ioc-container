package org.homs.sokoban;

import java.awt.Dimension;

public class Mapa {

	public final static char BLANK = ' ';
	public final static char WALL = '#';
	public final static char PLAY = '@';
	public final static char BOX = '$';
	public final static char GOAL = '.';
	public final static char GOAL_PLAY = '+';
	public final static char GOAL_BOX = '*';

	private final int ROWS;
	private final int COLS;

	private final char[] map;
	private final boolean[] accMap;
	private final int playerIndex;

	public Mapa(final String l) {

		final Dimension d = computeLevelSize(l);
		this.COLS = d.width;
		this.ROWS = d.height;

		this.map = encodeMap(l);
		this.playerIndex = findPlayer();
		this.accMap = computeAccessMap();

//		System.out.println(this.toString());
	}

	private char[] encodeMap(final String l) {

		int index = 0;
		int mi = 0;

		final char[] map = new char[ROWS * COLS];

		do {
			int c = 0;
			while (l.charAt(index) != '\n') {
				map[mi++] = l.charAt(index);
				c++;
				index++;
			}
			for (int i = c; i < this.COLS; i++) {
				map[mi++] = WALL;
			}
			index++;
		} while (l.charAt(index) != '\n');

		return map;
	}

	private int findPlayer() {
		for (int i = 0; i < this.map.length; i++) {
			if ((map[i] & PLAY) != 0) {
				return i;
			}
		}
		throw new NullPointerException("kjhkjh");
	}

	private Dimension computeLevelSize(final String l) {

		int maxc = 0;
		int maxf = 0;
		int index = 0;

		do {
			int c = 0;
			while (l.charAt(index) != '\n') {
				c++;
				index++;
			}
			if (maxc < c) {
				maxc = c;
			}
			maxf++;
			index++;
		} while (l.charAt(index) != '\n');

		return new Dimension(maxc, maxf);
	}

	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer();

		strb.append(ROWS);
		strb.append(',');
		strb.append(COLS);
		strb.append('\n');

		for (int i = 0; i < playerIndex % COLS; i++) {
			strb.append(' ');
		}
		strb.append("|\n");

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				strb.append(map[j + i * COLS]);
			}
			if (i == playerIndex / COLS) {
				strb.append('-');
			}
			strb.append('\n');
		}
		strb.append('\n');

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				strb.append(accMap[j + i * COLS] ? ' ' : '#');
			}
			strb.append('\n');
		}
		strb.append('\n');

		return strb.toString();
	}

	private boolean[] computeAccessMap() {

		final boolean[] ac = new boolean[ROWS * COLS];
		for (int i = 0; i < ac.length; i++) {
			ac[i] = false;
		}

		return computeAccessMap(ac, playerIndex);
	}

	private boolean[] computeAccessMap(final boolean[] ac, final int index) {

		if (map[index] != ' ' && map[index] != '.' && map[index] != '@'
				&& map[index] != '+') {
			return ac;
		}
		if (ac[index]) {
			return ac;
		}

		boolean[] ac2 = ac;
		ac2[index] = true;

		if (!ac2[index + 1]) {
			ac2 = computeAccessMap(ac2, index + 1);
		}
		if (!ac2[index - 1]) {
			ac2 = computeAccessMap(ac2, index - 1);
		}
		if (!ac2[index - COLS]) {
			ac2 = computeAccessMap(ac2, index - COLS);
		}
		if (!ac2[index + COLS]) {
			ac2 = computeAccessMap(ac2, index + COLS);
		}

		return ac2;
	}
}
