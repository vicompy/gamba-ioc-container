package org.gamba.mocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gamba.mocks.fluent.Mask;

/**
 * entitat que recull el registre de crides efectuades sobre el mock,
 * extraient-ne estadístiques i tal.
 *
 * @author mhoms
 */
public class CallingLog {

	private final List<CallLogEntry> callLogEntry;

	public CallingLog() {
		callLogEntry = new ArrayList<CallLogEntry>();
	}

	/**
	 * registra una crida feta.
	 *
	 * @param callLogEntry
	 */
	public void add(final CallLogEntry callLogEntry) {
		this.callLogEntry.add(callLogEntry);
	}

	@Override
	public String toString() {
		return callLogEntry.toString();
	}

	public int countGroupingBy(final String methodName) {
		int count = 0;
		for (final CallLogEntry cle : callLogEntry) {
			if (cle.method.getName().equals(methodName)) {
				count++;
			}
		}
		return count;
	}

	public int countGroupingBy(final String methodName, final Object... args) {
		int count = 0;
		for (final CallLogEntry cle : callLogEntry) {
			if (cle.method.getName().equals(methodName) && Arrays.equals(cle.arguments, args)) {
				count++;
			}
		}
		return count;
	}

	public int countGroupingBy(final Mask mask, final String methodName, final Object... args) {
		int count = 0;
		for (final CallLogEntry cle : callLogEntry) {
			boolean founded = true;
			if (cle.method.getName().equals(methodName)) {
				for (int i = 0; i < args.length; i++) {
					if (!args[i].equals(cle.arguments[i]) && !mask.getMask()[i]) {
						founded = false;
						break;
					}
				}
				if (founded) {
					count++;
				}
			}
		}
		return count;
	}
}
