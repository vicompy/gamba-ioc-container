package org.berbis.gretl.ui;

import java.awt.FileDialog;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FilenameFilter;

import org.berbis.gretl.BerbisProps;
import org.berbis.gretl.Downloader;

public class Controller implements WindowListener {

	private CFrame cframe;
	private Downloader downloader;
	private BerbisProps berbisProps;

	public void windowActivated(final WindowEvent e) {
	}

	public void windowClosed(final WindowEvent e) {

	}

	public void windowClosing(final WindowEvent e) {
		System.exit(0);
	}

	public void windowDeactivated(final WindowEvent e) {

	}

	public void windowDeiconified(final WindowEvent e) {

	}

	public void windowIconified(final WindowEvent e) {

	}

	/**
	 * un cop oberta i cargada la finestra, mostra el filechooser i comença a
	 * downloadar, mostrant logs per textarea
	 */
	public void windowOpened(final WindowEvent e) {
		if (e.getSource() instanceof CFrame) {
			this.chooseFileAndDownload((CFrame) e.getSource());
		}
	}

	private String openFile(final CFrame cframe) {
		final FileDialog fd = new FileDialog(cframe, "Escull un fitxer de propietats", FileDialog.LOAD);
		fd.setDirectory("./properties");

		fd.setFilenameFilter(new FilenameFilter() {
			public boolean accept(final File dir, final String name) {
				return name.endsWith(".properties");
			}
		});

		fd.setVisible(true);
		return fd.getFile();
	}

	private void chooseFileAndDownload(final CFrame cframe) {
		final String propertiesPathName = this.openFile(cframe);
		cframe.addTextLine("configuració escollida: " + propertiesPathName);

		berbisProps.setup(propertiesPathName);

		for (final String key : berbisProps.getKeysAProcessar()) {
			final String url = berbisProps.getProperty("url." + key);
			final String fitxer = berbisProps.getProperty("fitxer." + key);

			downloader.download(berbisProps.getPathDeDescarrega(), url, fitxer, cframe);
		}

		cframe.addTextLine("OK, fet.");

	}

	public Downloader getDownloader() {
		return downloader;
	}

	public void setDownloader(final Downloader downloader) {
		this.downloader = downloader;
	}

	public BerbisProps getBerbisProps() {
		return berbisProps;
	}

	public void setBerbisProps(final BerbisProps berbisProps) {
		this.berbisProps = berbisProps;
	}

	public CFrame getCframe() {
		return cframe;
	}

	public void setCframe(final CFrame cframe) {
		this.cframe = cframe;
	}

}
