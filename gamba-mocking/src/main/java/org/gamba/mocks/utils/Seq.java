package org.gamba.mocks.utils;

import java.util.Arrays;
import java.util.List;

public final class Seq {

	private Seq() {
		// all static members
	}

//	private static Object[] toArray(final List<?> tl) {
//		return tl.toArray(new Object[tl.size()]);
//	}

	public static List<Object> enList(final Object... os) {
		if (os == null) {
			return null;
		}
		return Arrays.asList(os);
	}

	public static Object[] rep(final int n, final Object t) {
//		final List<Object> r = new ArrayList<Object>();
//		final List<Object> l = Arrays.asList(ts); //Arrays.asList(seq(ts));
//
//		for (int i = 0; i < n; i++) {
//			r.addAll(l);
//		}
//		return toArray(r);

		final Object[] r = new Object[n];
		Arrays.fill(r, 0, n, t);
		return r;


	}

//	@Deprecated
//	public static Object[] seq(final Object... ts) {
//		final List<Object> r = new ArrayList<Object>();
//
//		for (final Object t : ts) {
//			if (t.getClass().isArray()) {
//				r.addAll(enList((Object[]) t));
//			} else {
//				r.add(t);
//			}
//		}
//
//		return toArray(r);
//	}

}
