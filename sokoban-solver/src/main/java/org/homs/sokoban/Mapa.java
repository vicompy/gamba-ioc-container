package org.homs.sokoban;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mapa {

	public final static char BLANK = ' ';
	public final static char WALL = '#';
	public final static char PLAY = '@';
	public final static char BOX = '$';
	public final static char GOAL = '.';
	public final static char GOAL_PLAY = '+';
	public final static char GOAL_BOX = '*';

	protected final int ROWS;
	protected final int COLS;

	protected final char[] map;
	protected final boolean[] accMap;
	protected final int playerIndex;
	protected final Integer[] boxList;

	public Mapa(final String l) {

		/*
		 * calcula les dimensions del mapa
		 */
		final Dimension d = computeLevelSize(l);
		this.COLS = d.width;
		this.ROWS = d.height;

		/*
		 * carrega el mapa
		 */
		this.map = encodeMap(l);

		/*
		 * cerca les caixes i el player
		 */
		// this.boxList = new ArrayList<Integer>();
		this.playerIndex = findPlayer();
		this.boxList = findBoxes();

		/*
		 * calcula el mapa d'accessibilitat
		 */
		this.accMap = computeAccessMap();

		// System.out.println(this.toString());
		checkMap();
	}

	protected Mapa(final Mapa mapa, final int indexOrgBox, final int indexDstBox) {

		this.ROWS = mapa.ROWS;
		this.COLS = mapa.COLS;

		this.map = mapa.map.clone();

		if (map[mapa.playerIndex] == '@') {
			map[mapa.playerIndex] = ' ';
		} else if (map[mapa.playerIndex] == '+') {
			map[mapa.playerIndex] = '.';
		}

		this.playerIndex = indexOrgBox;

		final Integer[] bl = mapa.boxList.clone();
		for (int i = 0; i < bl.length; i++) {
			if (bl[i] == indexOrgBox) {
				bl[i] = indexDstBox;
			}
		}
		this.boxList = bl;

		if (map[indexOrgBox] == '$') {
			map[indexOrgBox] = '@';
		} else if (map[indexOrgBox] == '*') {
			map[indexOrgBox] = '+';
		} else {
			throw new NullPointerException("kjhkjh");
		}

		if (map[indexDstBox] == ' ') {
			map[indexDstBox] = '$';
		} else if (map[indexDstBox] == '.') {
			map[indexDstBox] = '*';
		} else {
			throw new NullPointerException("kjhkjh");
		}

		this.accMap = computeAccessMap();
		checkMap();
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
			if (map[i] == '@' || map[i] == '+') {
				return i;
			}
		}
		throw new NullPointerException("kjhkjh");
	}

	private Integer[] findBoxes() {
		final List<Integer> bl = new ArrayList<Integer>();

		for (int i = 0; i < this.map.length; i++) {
			if (map[i] == '$' || map[i] == '*') {
				bl.add(i);
			}
		}

		return bl.toArray(new Integer[bl.size()]);
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

	protected boolean[] computeAccessMap() {

		final boolean[] ac = new boolean[ROWS * COLS];
		for (int i = 0; i < ac.length; i++) {
			ac[i] = false;
		}

		return computeAccessMap(ac, playerIndex);
	}

	private boolean[] computeAccessMap(final boolean[] ac, final int index) {

		if (ac[index] || map[index] != ' ' && map[index] != '.' && map[index] != '@' && map[index] != '+') {
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

	public String toString2() {
		final StringBuffer strb = new StringBuffer();

		strb.append(ROWS);
		strb.append(',');
		strb.append(COLS);
		strb.append(',');
		strb.append(this.boxList.length);
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

	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer();

		strb.append('\n');
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				strb.append(map[j + i * COLS]);
			}
			strb.append('\n');
		}
		strb.append('\n');

		return strb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(accMap);
		result = prime * result + Arrays.hashCode(boxList);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Mapa other = (Mapa) obj;
		if (!Arrays.equals(accMap, other.accMap))
			return false;
		if (boxList == null) {
			if (other.boxList != null)
				return false;
		} else {
			if (!Arrays.equals(boxList, other.boxList))
				return false;
		}
		return true;
	}

	// private static <T> boolean equalsList(final List<T> l1, final List<T> l2)
	// {
	//
	// if (l1.size() != l2.size()) {
	// return false;
	// }
	// for (int i = 0; i < l1.size(); i++) {
	// if (l1.get(i) != l2.get(i)) {
	// return false;
	// }
	// }
	//
	// return true;
	// }

	@Deprecated
	protected void checkMap() {
		int ngoals = 0;
		for (int i = 0; i < map.length; i++) {
			if (map[i] == '.' || map[i] == '*' || map[i] == '+') {
				ngoals++;
			}
		}
		if (ngoals != this.boxList.length) {
			throw new NullPointerException();
		}

		for (final int boxPos : boxList) {
			if (map[boxPos] != '$' && map[boxPos] != '*') {
				throw new NullPointerException();
			}
		}

		if (map[playerIndex] != '@' && map[playerIndex] != '+') {
			throw new NullPointerException("" + map[playerIndex]);
		}
	}

}
