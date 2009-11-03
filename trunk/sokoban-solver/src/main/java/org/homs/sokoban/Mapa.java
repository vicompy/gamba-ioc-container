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

    private final int ROWS;
    private final int COLS;

    private final char[] map;
    private final boolean[] accMap;
    private int playerIndex;
    private final List<Integer> boxList;

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
	this.boxList = new ArrayList<Integer>();
	findElements();

	/*
	 * calcula el mapa d'accessibilitat
	 */
	this.accMap = computeAccessMap();

	// System.out.println(this.toString());
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

    private void findElements() {
	Integer playerPos = null;
	for (int i = 0; i < this.map.length; i++) {
	    if (map[i] == '@' || map[i] == '+') {
		playerPos = i;
	    }
	    if (map[i] == '$' || map[i] == '*') {
		this.boxList.add(i);
	    }
	}
	if (playerPos == null) {
	    throw new NullPointerException("kjhkjh");
	}
	this.playerIndex = playerPos;
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

    private boolean[] computeAccessMap() {

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
	strb.append(this.boxList.size());
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

	return strb.toString();
    }


    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + Arrays.hashCode(accMap);
	result = prime * result + ((boxList == null) ? 0 : boxList.hashCode());
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
	} else if (!boxList.equals(other.boxList))
	    return false;
	return true;
    }

}
