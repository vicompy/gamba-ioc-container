package org.homs.gamba.container.xmlparser.ents;

import java.util.ArrayList;
import java.util.List;

import org.homs.gamba.container.exception.GambaConfigurationException;

/**
 * entitats directament corresponents amb el document XML, les propietats són
 * totes String, i es validen simplement els camps requerits, i els que són
 * incompatibles de definir.
 *
 * @author mhoms
 */
public class BeanTag {

	/** identificador del bean */
	public String id;
	/** nom de classe del bean */
	public String className;
	/** indica instància única */
	public String singleton;

	/** injeccions per constructor */
	public List<ConstrTag> constrTags;
	/** injeccions per mètode */
	public List<MethodTag> methodTags;

	public BeanTag(final String id, final String className, final String singleton) {
		super();
		this.id = id;
		this.className = className;
		this.singleton = singleton;
		this.methodTags = new ArrayList<MethodTag>();
		this.constrTags = new ArrayList<ConstrTag>();

		checkIfInvalidAttrThrowing("id", id);
		checkIfInvalidAttrThrowing("class", className);
		if (singleton != null && !"true".equals(singleton.toLowerCase()) && !"false".equals(singleton.toLowerCase())) {
			throw new GambaConfigurationException("'singleton' attribute must be 'true' or 'false', if specified");
		}
	}

	/**
	 * tira excepció si l'atribut suministrat val null
	 *
	 * @param attrName nom d'atrinut a mostrar
	 * @param attrValue valor a testar
	 */
	public static void checkIfInvalidAttrThrowing(final String attrName, final String attrValue) {
		if (attrValue == null) {
			throw new GambaConfigurationException("'" + attrName + "' atribute is required, but not specified");
		}
	}

	// @Override
	// public String toString() {
	// final StringBuffer strb = new StringBuffer();
	//
	// strb.append("id=" + id);
	// strb.append(" class=" + className);
	// strb.append(" singleton=" + singleton);
	// strb.append('\n');
	// for (final ConstrTag c : constrTags) {
	// strb.append(c.toString());
	// }
	// for (final MethodTag m : methodTags) {
	// strb.append(m.toString());
	// }
	//
	// return strb.toString();
	// }

}
