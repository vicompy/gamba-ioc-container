package org.homs.gamba.container.ents;

import org.homs.gamba.container.exception.GambaException;

public class InjectableElement {

	public enum EInjType {
		STRING_VALUE, BEANREF
	};

	public final EInjType eInjType;
	public final BeanDef beanRef;
	public final String stringValue;
	public final Class<?> type;

	public Object typeInstance; // holds new type(stringValue)

	/**
	 * construeix en cas d'String inmediat
	 *
	 * @param stringValue valor String a injectar
	 */
	public InjectableElement(final String stringValue, final Class<?> type, final Object typeInstance) {
		super();
		this.eInjType = EInjType.STRING_VALUE;
		this.stringValue = stringValue;
		this.beanRef = null;
		this.type = type;
		this.typeInstance = typeInstance;
	}

	public InjectableElement(final BeanDef beanDef) {
		super();
		this.eInjType = EInjType.BEANREF;
		this.stringValue = null;
		this.beanRef = beanDef;
		this.type = null;
	}

	public Class<?> getInjElementClass() throws GambaException {
		if (eInjType == EInjType.STRING_VALUE) {
			return type == null ? String.class : type;
		} else {
			return beanRef.beanClass;
		}
	}

	// /**
	// * retorna una representació de l'estat actual de l'objecte
	// *
	// * @see java.lang.Object#toString()
	// */
	// @Override
	// public String toString() {
	// final StringBuffer strb = new StringBuffer();
	//
	// if (eInjType == EInjType.STRING_VALUE) {
	// strb.append("\"" + stringValue + "\"");
	// } else if (eInjType == EInjType.BEANREF) {
	// strb.append(beanRef.toString());
	// } else {
	// strb.append("{ " + beanRef.toString() + " }");
	// }
	// return strb.toString();
	// }

}
