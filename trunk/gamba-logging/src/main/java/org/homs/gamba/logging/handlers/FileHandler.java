package org.homs.gamba.logging.handlers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.homs.gamba.logging.exception.GambaException;
import org.homs.gamba.logging.interfaces.ILogHandler;

public class FileHandler implements ILogHandler {

	private final Writer out;

	public FileHandler() {
		try {
			out = new FileWriter("log.txt");
		} catch (final IOException e) {
			throw new GambaException(this.getClass().getSimpleName() + ": error obrint fitxer: log.txt", e);
		}
	}

	public synchronized void sendMessage(final String msg) {
		try {
			out.write(msg + '\n');
			out.flush();
		} catch (final IOException e) {
			try {
				out.close();
			} catch (final IOException e1) {
			}
			throw new GambaException(this.getClass().getSimpleName() + ": error d'escriptura de log a fitxer: log.txt", e);
		}
	}

}
