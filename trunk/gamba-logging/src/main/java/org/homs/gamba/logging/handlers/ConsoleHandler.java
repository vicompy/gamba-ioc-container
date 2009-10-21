package org.homs.gamba.logging.handlers;


import org.homs.gamba.logging.interfaces.ILogHandler;

public class ConsoleHandler implements ILogHandler {

	public synchronized void sendMessage(final String msg) {
		System.err.println(msg);
	}

}
