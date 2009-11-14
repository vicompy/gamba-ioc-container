package org.homs.gamba.stub;

import java.util.ArrayList;
import java.util.List;

public class CallingLog {

	private final List<CallLogEntry> callLogEntry;

	public CallingLog() {
		callLogEntry = new ArrayList<CallLogEntry>();
	}

	public void add(final CallLogEntry callLogEntry) {
		this.callLogEntry.add(callLogEntry);
	}

	@Override
	public String toString() {
		return callLogEntry.toString();
	}

}
