package org.homs.gamba.stub;

import java.lang.reflect.Method;

/**
 * entitat que registra una crida a l'objecte stub.
 *
 * @author mhoms
 */
class CallingElement {

	/**
	 * mètode cridat
	 */
	private Method method;

	/**
	 * llistat de valors dels arguments passats
	 */
	private Object[] callingArgsValues;

	/**
	 * valor de retorn desitjat
	 */
	private final Object returningObject;

	/**
	 * constructor que especifica, anticipadament, el valor de retorn desitjat
	 *
	 * @param returningObject
	 */
	public CallingElement(final Object returningObject) {
		super();
		this.returningObject = returningObject;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(final Method method) {
		this.method = method;
	}

	public Object[] getCallingArgsValues() {
		return callingArgsValues;
	}

	public void setCallingArgsValues(final Object[] callingArgsValues) {
		this.callingArgsValues = callingArgsValues;
	}

	public Object getReturningObject() {
		return returningObject;
	}

	/**
	 * retorna una representació en String de l'estat d'aquest objecte
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer();
		strb.append(returningObject.toString());
		strb.append(" <= ");
		strb.append(method.getName());
		strb.append('(');
		for (int i = 0; i < callingArgsValues.length; i++) {
			strb.append(callingArgsValues[i].toString());
			strb.append(", ");
		}
		strb.append(')');
		return strb.toString();
	}

}
