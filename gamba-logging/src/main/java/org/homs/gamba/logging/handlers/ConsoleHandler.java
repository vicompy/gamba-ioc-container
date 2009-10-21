package org.homs.gamba.logging.handlers;


import org.homs.gamba.logging.LoggerLevelConstants;
import org.homs.gamba.logging.interfaces.ILogHandler;

public class ConsoleHandler implements ILogHandler {

	// TODO falta emcanisme per a configurar els handlers

	public void sendMessage(final int level, final String msg) {
		synchronized (this) {
			if (level <= LoggerLevelConstants.ERROR) {
				System.err.println(msg);
			} else {
				System.out.println(msg);
			}
		}
	}

}
