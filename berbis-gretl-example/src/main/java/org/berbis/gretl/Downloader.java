package org.berbis.gretl;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.berbis.gretl.exceptions.BerbisException;
import org.berbis.gretl.ui.CFrame;

public class Downloader {

	public void download(final String pathToSave, final String urlFileToDownload, final String fileNameToSave,
			final CFrame frame) {
		URL url = null;
		URLConnection urlCon = null;

		final String filePathName = pathToSave + fileNameToSave;

		// obre la URL
		try {
			url = new URL(urlFileToDownload);
			urlCon = url.openConnection();
		} catch (final MalformedURLException e) {
			throw new BerbisException("", e);
		} catch (final IOException e) {
			throw new BerbisException("", e);
		}

		// obté l'stream de la url
		InputStream is = null;
		try {
			is = urlCon.getInputStream();
		} catch (final IOException e) {
			throw new BerbisException("", e);
		}

		// crea/buida fitxer de destí
		createFile(filePathName);

		// obre el fitxer de destí en escriptura
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePathName);
		} catch (final FileNotFoundException e) {
			throw new BerbisException("", e);
		}

		// buffer de fitxer
		final byte[] array = new byte[1000];

		// llegeix de l'stream de url i guarda en stream de fitxer
		int leido = -1;
		int readed = 0;
		try {
			leido = is.read(array);
			while (leido > 0) {
				fos.write(array, 0, leido);
				readed += leido;
				leido = is.read(array);
			}
		} catch (final IOException e) {
			throw new BerbisException("", e);
		}

		// tanca streams (connexió i fitxer)
		try {
			is.close();
			fos.close();
		} catch (final IOException e) {
			throw new BerbisException("", e);
		}

		// printa la línia de info de tot OK
		String log = "    OK " + (readed / 1024) + "Kb ";
		log += "[" + urlCon.getContentType() + "] ";
		log += " ==> ";
		log += filePathName;

		frame.addTextLine(log);
	}

	private void createFile(final String fileName) {
		try {
			final BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
			out.write("aString");
			out.close();
		} catch (final IOException e) {
			throw new BerbisException("", e);
		}
	}

}
