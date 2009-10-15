package org.homs.gamba.container.entities;

import org.homs.gamba.container.exception.GambaException;

/**
 * codifica una dependència a injectar
 *
 * @author mhoms
 */
public class InjElem {

	/**
	 * codifica la naturalesa de la dependència
	 *
	 * @author mhoms
	 */
	public enum EInjType {
		STRING_VALUE, BEANREF, NESTED_ANONYMOUS_BEANDEF
	};

	/**
	 * naturalesa de la dependència
	 */
	public final EInjType eInjType;
	/**
	 * si la dependència és de naturalesa String-inmediata, guarda aquí el seu
	 * valor.
	 */
	public final String stringValue;
	/**
	 * si es tracta d'una referència a una altra definició de bean (encara que
	 * sigui inner-anonymous), en guarda aquí la referència a l'objecte de
	 * definició.
	 */
	public final BeanDef beanRef;

	/**
	 * construeix en cas d'String inmediat
	 *
	 * @param stringValue valor String a injectar
	 */
	public InjElem(final String stringValue) {
		super();
		this.eInjType = EInjType.STRING_VALUE;
		this.stringValue = stringValue;
		this.beanRef = null;
	}

	/**
	 * construeic en cas de referència a una altra definició de bean
	 *
	 * @param eInjType naturalesa de la referència
	 * @param beanDef definició del bean
	 */
	public InjElem(final EInjType eInjType, final BeanDef beanDef) {
		super();
		this.eInjType = eInjType;
		this.stringValue = null;
		this.beanRef = beanDef;
	}

	public Class<?> getInjElementClass() throws GambaException {
		if (eInjType == EInjType.STRING_VALUE) {
			return String.class;
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
			strb.append("\"" + stringValue + "\"");
		} else if (eInjType == EInjType.BEANREF) {
			strb.append(beanRef.toString());
		} else {
			strb.append("{ " + beanRef.toString() + " }");
		}
		return strb.toString();
	}

}
