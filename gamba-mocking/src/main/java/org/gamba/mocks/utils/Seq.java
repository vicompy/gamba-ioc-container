package org.gamba.mocks.utils;

import java.util.Arrays;
import java.util.List;

public final class Seq {

	private Seq() {
		// all static members
	}

	public static List<Object> enList(final Object... os) {
		if (os == null) {
			return null;
		}
		return Arrays.asList(os);
	}

	public static Object[] rep(final int n, final Object t) {
		final Object[] r = new Object[n];
		Arrays.fill(r, 0, n, t);
		return r;
	}

}
