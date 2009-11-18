package org.gro.sm4j.utils;

import java.util.Arrays;
import java.util.List;

public final class Seq {

	private Seq() {
		// all static members
	}

	public static List<Object> enList(final Object... objects) {
		if (objects == null) {
			return null;
		}
		return Arrays.asList(objects);
	}

	public static Object[] rep(final int times, final Object object) {
		final Object[] result = new Object[times];
		Arrays.fill(result, 0, times, object);
		return result;
	}

}
