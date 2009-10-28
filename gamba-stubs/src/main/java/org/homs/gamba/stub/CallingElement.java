package org.homs.gamba.stub;

import java.lang.reflect.Method;

/**
 * entitat que registra una crida a l'objecte stub.
 *
 * @author mhoms
 */
class CallingElement {

	private Method method;
	private Object[] callingArgsValues;

	private final ECallingElementType callingElementType;
	private final Object returningObject;

	/**
	 * constructor que especifica, anticipadament, el comportament de la crida a
	 * stubbar
	 *
	 * @param returningObject
	 */
	public CallingElement(final Object returningObject, final boolean isAnExceptionToThrow) {
		this.returningObject = returningObject;
		if (isAnExceptionToThrow) {
			this.callingElementType = ECallingElementType.THROWING;
		} else {
			this.callingElementType = ECallingElementType.RETURNING;
		}
	}

	public CallingElement(final IDelegator delegator) {
		this.returningObject = delegator;
		this.callingElementType = ECallingElementType.DELEGATING;
	}

	public Method getMethod() {
		return method;
	}

	public void setCall(final Method method, final Object[] callingArgsValues) {
		this.method = method;
		if (callingArgsValues != null) {
			this.callingArgsValues = callingArgsValues.clone();
		} else {
			this.callingArgsValues = new Object[0];
		}
	}

	public Object[] getCallingArgsValues() {
		return callingArgsValues.clone();
	}

	public Object getReturningObject() {
		return returningObject;
	}

	public ECallingElementType getCallingElementType() {
		return callingElementType;
	}

	// /** TODO
	// * retorna una representació en String de l'estat d'aquest objecte
	// *
	// * @see java.lang.Object#toString()
	// */
	// @Override
	// public String toString() {
	// final StringBuffer strb = new StringBuffer(100);
	// if (returningObjectisAnExceptionToThrow != null &&
	// !returningObjectisAnExceptionToThrow) {
	// strb.append(returningObject);
	// }
	// strb.append(" <= ");
	// strb.append(method.getName());
	// strb.append('(');
	// for (int i = 0; i < callingArgsValues.length; i++) {
	// strb.append(callingArgsValues[i]);
	// strb.append(", ");
	// }
	// strb.append(") : throwing: ");
	// if (returningObjectisAnExceptionToThrow != null &&
	// returningObjectisAnExceptionToThrow) {
	// strb.append(this.returningObject);
	// }
	// return strb.toString();
	// }

}
