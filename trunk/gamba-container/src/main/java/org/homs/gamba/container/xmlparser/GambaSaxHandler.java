package org.homs.gamba.container.xmlparser;

import java.util.ArrayList;
import java.util.List;

import org.homs.gamba.container.exception.GambaException;
import org.homs.gamba.container.xmlparser.ents.BeanTag;
import org.homs.gamba.container.xmlparser.ents.ConstrTag;
import org.homs.gamba.container.xmlparser.ents.MethodTag;
import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX és un parser XML integrat en el propi JRE, i és event-based; doncs cada
 * element que parseja el passa a aquest Handler.
 *
 * @author mhoms
 */
class GambaSaxHandler extends DefaultHandler {

	private final List<BeanTag> beanTags;

	public GambaSaxHandler() {
		super();
		beanTags = new ArrayList<BeanTag>();
	}

	/**
	 * error de parsing XML; tira excepció informant-ne.
	 *
	 * @see org.xml.sax.helpers.DefaultHandler#error(org.xml.sax.SAXParseException)
	 */
	@Override
	public void error(final SAXParseException e) {
		throw new GambaException("error parsing XML context file", e);
	}

	/**
	 * error fatal de parsing XML; tira excepció informant-ne.
	 *
	 * @see org.xml.sax.helpers.DefaultHandler#fatalError(org.xml.sax.SAXParseException)
	 */
	@Override
	public void fatalError(final SAXParseException e) {
		throw new GambaException("error parsing XML context file", e);
	}

	/**
	 * Handleja l'inici d'una nova etiqueta, rellenant el corresponent bean amb
	 * els valors dels atributs XML.
	 *
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
	 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(final String uri, final String name, final String qName, final Attributes atts) {
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

	/**
	 * retorna el resultat del pasing del fitxer de context XML.
	 *
	 * @return una llista dels {@link BeanTag}s trobats
	 */
	public List<BeanTag> getBeanTags() {
		return beanTags;
	}

}
