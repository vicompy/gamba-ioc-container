package org.homs.sokoban;

public class LockDetector {

	private final Mapa mapa;
	private final boolean[] isLockedPos;

	public LockDetector(final Mapa mapa) {
		super();
		this.mapa = mapa;

		isLockedPos = new boolean[mapa.ROWS * mapa.COLS];
		compute();
	}

	public boolean[] getIsLockedPos() {
		return isLockedPos;
	}

	private void compute() {

		for (int f = 0; f < mapa.ROWS; f++) {
			for (int c = 0; c < mapa.COLS; c++) {
				final int index = c + f * mapa.COLS;
				isLockedPos[index] = true;
			}
		}

		for (int f = 1; f < mapa.ROWS - 1; f++) {
			for (int c = 1; c < mapa.COLS - 1; c++) {

				final int index = c + f * mapa.COLS;
				isLockedPos[index] = posIsLockedOrder2(f, c) || posIsLockedOrder1(f, c);
			}
		}

	}

	private boolean posIsLockedOrder1(final int f, final int c) {
		final boolean locked = false;

		if (isGoal(f, c)) {
			return false;
		}
		if (isWall(f, c)) {
			return true;
		}

		if (isWall(f - 1, c) && isWall(f, c + 1)) {
			return true;
		}
		if (isWall(f + 1, c) && isWall(f, c + 1)) {
			return true;
		}
		if (isWall(f - 1, c) && isWall(f, c - 1)) {
			return true;
		}
		if (isWall(f + 1, c) && isWall(f, c - 1)) {
			return true;
		}

		return locked;
	}

	private boolean posIsLockedOrder2(final int f, final int c) {

		boolean locked1 = true;
		boolean locked2 = true;
		boolean locked3 = true;
		boolean locked4 = true;

		if (isWall(f, c)) {
			return true;
		}

		for (int cc = c; cc > 0; cc--) {
			if (isWall(f, cc)) {
				break;
			}
			if (isGoal(f, cc) || (!isWall(f - 1, cc) && !isWall(f + 1, cc))) {
				locked1 = false;
				break;
			}
		}
		for (int cc = c; cc < mapa.COLS; cc++) {
			if (isWall(f, cc)) {
				break;
			}
			if (isGoal(f, cc) || (!isWall(f - 1, cc) && !isWall(f + 1, cc))) {
				locked2 = false;
				break;
			}
		}

		for (int ff = f; ff > 0; ff--) {
			if (isWall(ff, c)) {
				break;
			}
			if (isGoal(ff, c) || (!isWall(ff, c - 1) && !isWall(ff, c + 1))) {
				locked3 = false;
				break;
			}
		}
		for (int ff = f; ff < mapa.COLS; ff++) {
			if (isWall(ff, c)) {
				break;
			}
			if (isGoal(ff, c) || (!isWall(ff, c - 1) && !isWall(ff, c + 1))) {
				locked4 = false;
				break;
			}
		}

		return locked1 && locked2 || locked3 && locked4;
	}

	private boolean isWall(final int f, final int c) {
		if (mapa.map[c + f * mapa.COLS] == '#') {
			return true;
		}
		return false;
	}

	private boolean isGoal(final int f, final int c) {
		final char cell = mapa.map[c + f * mapa.COLS];
		if (cell == '.' || cell == '*' || cell == '+') {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer();

		for (int i = 0; i < mapa.ROWS; i++) {
			for (int j = 0; j < mapa.COLS; j++) {
				if (this.isLockedPos[j + i * mapa.COLS]) {
					strb.append('#');
				} else {
					strb.append(' ');
				}
			}
			strb.append('\n');
		}
		strb.append('\n');

		return strb.toString();
	}
}
