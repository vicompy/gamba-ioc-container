package org.homs.gamba.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Seq {

	private Seq() {
	}

	private static Object[] enList(final List<?> tl) {
		return tl.toArray(new Object[tl.size()]);
	}

	public static Object[] rep(final int n, final Object... ts) {
		final List<Object> r = new ArrayList<Object>();
		final List<Object> l = Arrays.asList(seq(ts));

		for (int i = 0; i < n; i++) {
			r.addAll(l);
		}
		return enList(r);
	}

	public static Object[] seq(final Object... ts) {
		final List<Object> r = new ArrayList<Object>();

		for (final Object t : ts) {
			if (t instanceof List) {
				r.addAll((List<?>) t);
			} else {
				r.add(t);
			}
		}

		return enList(r);
	}

}
