package org.homs.gamba.logging.handlers;

import org.homs.gamba.logging.ILogHandler;

public class ConsoleHandler implements ILogHandler {

	public void sendMessage(final String msg) {
		synchronized (this) {
			System.err.println(msg);
		}
	}

}
