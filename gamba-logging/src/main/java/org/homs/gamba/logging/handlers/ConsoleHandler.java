package org.homs.gamba.logging.handlers;


import org.homs.gamba.logging.interfaces.ILogHandler;

public class ConsoleHandler implements ILogHandler {

	// TODO falta mecanisme per a configurar els handlers (nom de fitxer de sortida, etc)

	public void sendMessage(final String msg) {
		synchronized (this) {
			System.err.println(msg);
		}
	}

}