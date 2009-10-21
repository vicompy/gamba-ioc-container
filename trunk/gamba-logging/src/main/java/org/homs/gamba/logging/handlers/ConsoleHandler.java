package org.homs.gamba.logging.handlers;


import org.homs.gamba.logging.interfaces.ILogHandler;

public class ConsoleHandler implements ILogHandler {

	// TODO falta emcanisme per a configurar els handlers

	public void sendMessage(final String msg) {
		synchronized (this) {
			System.err.println(msg);
		}
	}

}
