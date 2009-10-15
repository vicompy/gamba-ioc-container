package org.homs.gamba.container.xmlparser;

import java.io.IOException;
import java.util.List;

import org.homs.gamba.container.exception.GambaConfigurationException;
import org.homs.gamba.container.exception.GambaException;
import org.homs.gamba.container.xmlparser.ents.BeanTag;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class GambaSaxParser {

	private final GambaSaxHandler handler;

	// private XMLReader xmlReader;

	public static void main(final String[] args) {

		new GambaSaxParser("spring-context.xml");

	}

	public GambaSaxParser(final String propertiesFileName) throws GambaException {

		final InputSource is = new InputSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
				propertiesFileName));

		this.handler = new GambaSaxHandler();

		try {
			final XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			xmlReader.setContentHandler(this.handler);
			xmlReader.setErrorHandler(this.handler);
			xmlReader.parse(is);
		} catch (final SAXException e) {
			throw new GambaConfigurationException("error obtaining XMLReader from XMLReaderFactory", e);
		} catch (final IOException e) {
			throw new GambaConfigurationException("could not open from classpath: " + propertiesFileName, e);
		}

	}

	public List<BeanTag> getDefs() {
		return this.handler.getBeanTags();
	}

	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer();

		for (final BeanTag b : this.handler.getBeanTags()) {
			strb.append(b.toString());
		}

		return strb.toString();
	}
}
