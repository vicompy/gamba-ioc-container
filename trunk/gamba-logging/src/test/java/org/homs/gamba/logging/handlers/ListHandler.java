package org.homs.gamba.logging.handlers;

import java.util.ArrayList;
import java.util.List;

import org.homs.gamba.logging.interfaces.ILogHandler;

public class ListHandler implements ILogHandler {

	private final List<String> logs;

	public ListHandler() {
		logs = new ArrayList<String>();
	}

	public void sendMessage(final String msg) {
		synchronized (this) {
			logs.add(msg);
//			System.out.println(msg);//TODO
		}
	}

	public String getReportedLog(final int index) {
		return logs.get(index);
	}

	public List<String> getLogs() {
		return logs;
	}

	public void clear() {
		logs.clear();
	}

}
