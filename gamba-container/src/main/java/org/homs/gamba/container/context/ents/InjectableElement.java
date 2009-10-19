package org.homs.gamba.container.context.ents;

import org.homs.gamba.container.exception.GambaException;

public class InjectableElement {

	public enum EInjType {
		STRING_VALUE, BEANREF
	};

	public final EInjType eInjType;
	public final BeanDef beanRef;
	public final String stringValue;
	public final Class<?> type;

	/**
	 * en cas de definició d'injecció de classse directament construïda per
	 * string, aquest valor és desat aquí com a singleton.
	 */
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

	/**
	 * retorna una representació de l'estat actual de l'objecte
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer();

		if (eInjType == EInjType.STRING_VALUE) {
			strb.append("value=\"" + stringValue + "\"");
			if (type != null) {
				strb.append(" type=\"" + type.getName() + "\"");
			}
		} else if (eInjType == EInjType.BEANREF) {
			strb.append("ref=\"" + beanRef.beanId + "\"");
		}
		return strb.toString();
	}

}
