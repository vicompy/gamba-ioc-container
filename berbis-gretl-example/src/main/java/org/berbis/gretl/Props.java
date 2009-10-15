package org.berbis.gretl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.berbis.gretl.exceptions.BerbisException;

public class Props {

	protected Properties properties;

	public void setPropertiesFileName(final String propertiesFileName) {
		this.properties = new Properties();

		try {
			properties.load(new FileInputStream(propertiesFileName));
		} catch (final IOException e) {
			throw new BerbisException("excepció en BerbisProps", e);
		}

	}

	public String getProperty(final String key) {
		return this.properties.getProperty(key);
	}

}
