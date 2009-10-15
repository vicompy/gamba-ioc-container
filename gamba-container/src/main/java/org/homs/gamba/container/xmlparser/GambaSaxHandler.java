package org.homs.gamba.container.xmlparser;

import java.util.ArrayList;
import java.util.List;

import org.homs.gamba.container.exception.GambaConfigurationException;
import org.homs.gamba.container.xmlparser.ents.BeanTag;
import org.homs.gamba.container.xmlparser.ents.ConstrTag;
import org.homs.gamba.container.xmlparser.ents.MethodTag;
import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class GambaSaxHandler extends DefaultHandler {

	private final List<BeanTag> beanTags;

	public GambaSaxHandler() {
		super();
		beanTags = new ArrayList<BeanTag>();
	}

	@Override
	public void error(final SAXParseException e) {
		throw new GambaConfigurationException("error parsing XML context file", e);
	}

	@Override
	public void fatalError(final SAXParseException e) {
		throw new GambaConfigurationException("error parsing XML context file", e);
	}

	// @Override
	// public void startDocument() {
	// System.out.println("Start document");
	// }
	//
	// @Override
	// public void endDocument() {
	// System.out.println("End document");
	// }

	@Override
	public void startElement(final String uri, final String name, final String qName, final Attributes atts) {
		// System.out.println("Start element: " + name);

		if ("bean".equals(name)) {
			beanTags.add(new BeanTag(atts.getValue("id"), atts.getValue("class"), atts.getValue("singleton")));
		}
		if ("method".equals(name)) {
			final BeanTag bean = beanTags.get(beanTags.size() - 1);
			bean.methodTags.add(new MethodTag(atts.getValue("name"), atts.getValue("ref"), atts.getValue("value"), atts
					.getValue("type")));
		}
		if ("constr-arg".equals(name)) {
			final BeanTag bean = beanTags.get(beanTags.size() - 1);
			bean.constrTags.add(new ConstrTag(atts.getValue("ref"), atts.getValue("value"), atts.getValue("type")));
		}

	}

	// @Override
	// public void endElement(final String uri, final String name, final String
	// qName) {
	// System.out.println("End element:   " + name);
	// }

	public List<BeanTag> getBeanTags() {
		return beanTags;
	}

}
