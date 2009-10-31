package org.homs.gamba.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Seq {

	private Seq() {
		// all static members
	}

	private static Object[] toArray(final List<?> tl) {
		return tl.toArray(new Object[tl.size()]);
	}

	public static List<Object> enList(final Object... os) {
		return Arrays.asList(os);
	}

	public static Object[] rep(final int n, final Object... ts) {
		final List<Object> r = new ArrayList<Object>();
		final List<Object> l = Arrays.asList(seq(ts));

		for (int i = 0; i < n; i++) {
			r.addAll(l);
		}
		return toArray(r);
	}

	public static Object[] seq(final Object... ts) {
		final List<Object> r = new ArrayList<Object>();

		for (final Object t : ts) {
			if (t.getClass().isArray()) {
				r.addAll(enList((Object[]) t));
			} else {
				r.add(t);
			}
		}

		return toArray(r);
	}

}
