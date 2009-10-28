package org.homs.gamba.utils;

import java.util.ArrayList;
import java.util.List;

public final class Seq {

	private Seq() {
	}

	public static <T> List<T> rep(final int n, final T... ts) {
		final List<T> r = new ArrayList<T>();
		final List<T> l = seq(ts);

		for (int i = 0; i < n; i++) {
			r.addAll(l);
		}
		return r;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> seq(final T... ts) {
		final List<T> r = new ArrayList<T>();

		for (final T t : ts) {
			if (t instanceof List) {
				r.addAll((List<T>) t);
			} else {
				r.add(t);
			}
		}

		return r;
	}

}
