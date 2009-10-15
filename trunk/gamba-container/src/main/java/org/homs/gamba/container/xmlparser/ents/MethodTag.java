package org.homs.gamba.container.xmlparser.ents;

import org.homs.gamba.container.exception.GambaConfigurationException;

/**
 * entitats directament corresponents amb el document XML, les propietats són
 * totes String, i es validen simplement els camps requerits, i els que són
 * incompatibles de definir.
 *
 * @author mhoms
 */
public class MethodTag extends ConstrTag {

	/**
	 * nom del mètode setter al que injectar
	 */
	public String methodName;

	public MethodTag(final String methodName, final String ref, final String value, final String type)
			throws GambaConfigurationException {
		super(ref, value, type);
		this.methodName = methodName;

		BeanTag.checkIfInvalidAttrThrowing("method", methodName);
	}

	// @Override
	// public String toString() {
	// final StringBuffer strb = new StringBuffer();
	//
	// strb.append("   methodName=" + methodName);
	// strb.append(" ref=" + ref);
	// strb.append(" value=" + value);
	// strb.append(" type=" + type);
	// strb.append('\n');
	//
	// return strb.toString();
	// }

}
