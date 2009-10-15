package org.berbis.gretl;

public class BerbisProps {

	protected Props props;
	protected String pathDeDescarrega;
	protected String[] keysAProcessar;

	public BerbisProps(final Props props) {
		this.props = props;
	}

	public void setup(final String propertiesFileName) {
		this.props.setPropertiesFileName(propertiesFileName);
		this.pathDeDescarrega = this.props.getProperty("path.de.descarrega");
		this.keysAProcessar = this.props.getProperty("fitxers.a.actualitzar").split(",");
	}

	public String getPathDeDescarrega() {
		return pathDeDescarrega;
	}

	public String[] getKeysAProcessar() {
		return keysAProcessar;
	}

	public String getProperty(final String key) {
		return this.props.getProperty(key);
	}

}
