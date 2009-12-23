//package org.gro.chess.algols;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class MapHash<T> {
//
//	private final Map<Integer, List<MapaW<T>>> hash;
//
//	public MapHash() {
//		this.hash = new HashMap<Integer, List<MapaW<T>>>();
//	}
//
//	public void put(final T mapaGen, final int level, final long score) {
//		final MapaW<T> mapa = new MapaW<T>(mapaGen, level, score);
////		final int hashValue = mapa.hashCode();
//		final int hashValue = mapaGen.hashCode();
//		List<MapaW<T>> span = this.hash.get(hashValue);
//		if (span == null) {
//			span = new ArrayList<MapaW<T>>();
//			hash.put(hashValue, span);
//		}
//		span.add(mapa);
//	}
//
//	public MapaW<T> exists(final T mapaGen) {
//		final MapaW<T> mapa = new MapaW<T>(mapaGen, -1, -1L); // TODO
//		final int hashValue = mapaGen.hashCode();
//
//		final List<MapaW<T>> mapaList = this.hash.get(hashValue);
//
//		if (mapaList == null) {
//			return null;
//		}
//
////		for (final MapaW m : mapaList) {
////			if (m.equals(mapa)) {
////				return m;
////			}
////		}
//		final int index = mapaList.indexOf(mapa);
//		if (index != -1) {
//			return mapaList.get(index);
//		}
//		return null;
//	}
//
//
////	public Boolean cuttable(final T mapaGen, final int level) {
////
////		final MapaW<T> exist = exists(mapaGen, level);
////		if (exist == null) {
////			// no trobat, cal calcuular l'score, i desar
//////			put(mapaGen, level, score);
////			return false;
////		} else {
////			// trobat
////			if (exist.getLevel() < level) {
////				// updatar mapa per level, i seguir
//////				exist.update(level, score);
////				return false;
////			} else {
////				// es talla la recursió
////				return true;
////			}
////		}
////	}
//
//
//
//
//}
//
//
//class MapaW<T> {
//	private final T mapa;
//	private int level;
//	private long score;
//
//
//	public MapaW(final T mapa, final int level, final long score) {
//		super();
//		this.mapa = mapa;
//		this.level = level;
//		this.score = score;
//	}
//
//	@Override
//	public int hashCode() {
//		return this.mapa.hashCode();
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public boolean equals(final Object obj) {
//		if (obj instanceof MapaW<?>) {
//			return this.mapa.equals(((MapaW<T>) obj).mapa);
//		}
//		throw new NullPointerException();
//	}
//
////	public T getMapa() {
////		return mapa;
////	}
//
//	public int getLevel() {
//		return level;
//	}
//
//	public long getScore() {
//		return score;
//	}
//
//	public void update(final int level, final long score) {
//		this.level = level;
//		this.score = score;
//	}
//
//	// @Override
//	// public String toString() {
//	// return this.mapa.toString();
//	// }
//
//
//}
//

