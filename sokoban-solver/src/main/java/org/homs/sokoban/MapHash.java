package org.homs.sokoban;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapHash implements IMapHash {

	private final Map<Integer, List<MapaW>> hash;

	public MapHash() {
		this.hash = new HashMap<Integer, List<MapaW>>();
	}

	public void put(final MapaGen mapaGen, final int level) {
		final MapaW mapa = new MapaW(mapaGen, level);
		final int hashValue = mapa.hashCode();
		List<MapaW> span = this.hash.get(hashValue);
		if (span == null) {
			span = new ArrayList<MapaW>();
			hash.put(hashValue, span);
		}
		span.add(mapa);
	}

	public MapaW exists(final MapaGen mapaGen, final int level) {
		final MapaW mapa = new MapaW(mapaGen, level);
		final int hashValue = mapa.hashCode();

		final List<MapaW> mapaList = this.hash.get(hashValue);

		if (mapaList == null) {
			return null;
		}

		for (final MapaW m : mapaList) {
			if (m.equals(mapa)) {
				return m;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.homs.sokoban.IMapHash#cut(org.homs.sokoban.MapaGen, int)
	 */
	public boolean cuttable(final MapaGen mapaGen, final int level) {

		final MapaW exist = exists(mapaGen, level);
		if (exist == null) {
			// no trobat
			put(mapaGen, level);
			return false;
		} else {
			// trobat
			if (exist.getLevel() > level) {
				// updatar mapa per level
				exist.setLevel(level);
				return false;
			} else {
				// es pot tallar
				return true;
			}
		}
	}
}

class MapaW {
	private final MapaGen mapa;
	private int level;

	public MapaW(final MapaGen mapa, final int level) {
		this.mapa = mapa;
		this.level = level;
	}

	@Override
	public int hashCode() {
		return this.mapa.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof MapaW) {
			return this.getMapa().equals(((MapaW) obj).getMapa());
		} else {
			throw new NullPointerException();
		}
	}

	public MapaGen getMapa() {
		return mapa;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(final int level) {
		this.level = level;
	}

	// @Override
	// public String toString() {
	// return this.mapa.toString();
	// }

}