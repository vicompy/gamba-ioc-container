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
	private final Boolean returningObjectisAnExceptionToThrow;
	private final IDelegator delegator;

	/**
	 * constructor que especifica, anticipadament, el comportament de la crida a
	 * stubbar
	 *
	 * @param returningObject
	 */
	public CallingElement(final Object returningObject, final boolean returningObjectisAnExceptionToThrow) {
		this.returningObject = returningObject;
		this.returningObjectisAnExceptionToThrow = returningObjectisAnExceptionToThrow;
		this.delegator = null;
	}

	public CallingElement(final IDelegator delegator) {
		this.returningObject = null;
		this.returningObjectisAnExceptionToThrow = null;
		this.delegator = delegator;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(final Method method) {
		this.method = method;
	}

	public Object[] getCallingArgsValues() {
		return callingArgsValues.clone();
	}

	public void setCallingArgsValues(final Object[] callingArgsValues) {
		this.callingArgsValues = callingArgsValues.clone();
	}

	public Object getReturningObject() {
		return returningObject;
	}

	public Boolean getReturningObjectisAnExceptionToThrow() {
		return returningObjectisAnExceptionToThrow;
	}

	public IDelegator getDelegator() {
		return delegator;
	}

	/**
	 * retorna una representació en String de l'estat d'aquest objecte
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer(100);
		if (returningObjectisAnExceptionToThrow != null && !returningObjectisAnExceptionToThrow) {
			strb.append(returningObject);
		}
		strb.append(" <= ");
		strb.append(method.getName());
		strb.append('(');
		for (int i = 0; i < callingArgsValues.length; i++) {
			strb.append(callingArgsValues[i]);
			strb.append(", ");
		}
		strb.append(") : throwing: ");
		if (returningObjectisAnExceptionToThrow != null && returningObjectisAnExceptionToThrow) {
			strb.append(this.returningObject);
		}
		return strb.toString();
	}

}
