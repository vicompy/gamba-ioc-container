package org.homs.gamba.container.xmlparser.ents;

import org.homs.gamba.container.exception.GambaConfigurationException;
import org.homs.gamba.container.exception.GambaException;

/**
 * entitats directament corresponents amb el document XML, les propietats són
 * totes String, i es validen simplement els camps requerits, i els que són
 * incompatibles de definir.
 *
 * @author mhoms
 */
public class ConstrTag {

	/** referenced beanId dependency */
	public String ref;

	/** inmediate string value */
	public String value;

	/** inmediately String constructable class, if value!=null */
	public String type;

	public ConstrTag(final String ref, final String value, final String type) {
		super();
		this.ref = ref;
		this.value = value;
		this.type = type;

		try {
			if (ref == null) {
				BeanTag.checkIfInvalidAttrThrowing("value", value);
			} else {
				if (value != null) {
					throw new GambaConfigurationException("'value' atribute must be null");
				}
				if (type != null) {
					throw new GambaConfigurationException("'type' atribute must be null");
				}
			}
		} catch (final GambaException e) {
			throw new GambaConfigurationException("bean definition attributes: ref | (value [type])", e);
		}
	}

	// @Override
	// public String toString() {
	// final StringBuffer strb = new StringBuffer();
	//
	// strb.append("   ref=" + ref);
	// strb.append(" value=" + value);
	// strb.append(" type=" + type);
	// strb.append('\n');
	//
	// return strb.toString();
	// }

}
