package org.gro.chess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapHash<T> {

	private final Map<Integer, List<MapaW<T>>> hash;

	public MapHash() {
		this.hash = new HashMap<Integer, List<MapaW<T>>>();
	}

	public void put(final T mapaGen, final int level) {
		final MapaW<T> mapa = new MapaW<T>(mapaGen, level);
		final int hashValue = mapa.hashCode();
		List<MapaW<T>> span = this.hash.get(hashValue);
		if (span == null) {
			span = new ArrayList<MapaW<T>>();
			hash.put(hashValue, span);
		}
		span.add(mapa);
	}

	public MapaW<T> exists(final T mapaGen, final int level) {
		final MapaW<T> mapa = new MapaW<T>(mapaGen, level);
		final int hashValue = mapa.hashCode();

		final List<MapaW<T>> mapaList = this.hash.get(hashValue);

		if (mapaList == null) {
			return null;
		}

		final int index = mapaList.indexOf(mapa);
		if (index != -1) {
			return mapaList.get(index);
		}
		return null;
	}

	/**
	 * @see org.homs.sokoban.IMapHash#cut(org.homs.sokoban.MapaGen, int)
	 */
	public boolean cuttable(final T mapaGen, final int level) {

		final MapaW<?> exist = exists(mapaGen, level);
		if (exist == null) {
			// no trobat, desar mapa i seguir
			put(mapaGen, level);
			return false;
		} else {
			// trobat
			if (exist.getLevel() > level) {
				// updatar mapa per level, i seguir
				exist.setLevel(level);
				return false;
			} else {
				// es talla la recursió
				return true;
			}
		}
	}
}

class MapaW<T> {
	private final T mapa;
	private int level;

	public MapaW(final T mapa, final int level) {
		this.mapa = mapa;
		this.level = level;
	}

	@Override
	public int hashCode() {
		return this.mapa.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof MapaW<?>) {
			return this.getMapa().equals(((MapaW<?>) obj).getMapa());
		}
		throw new NullPointerException();
	}

	public T getMapa() {
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