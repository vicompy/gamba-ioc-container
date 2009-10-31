package org.homs.gamba.stub;

import java.lang.reflect.Method;

import org.homs.gamba.stub.delegator.IDelegator;

/**
 * entitat que registra una crida a l'objecte stub.
 *
 * @author mhoms
 */
class CallActionConfig {

	private Method method;
	private Object[] callingArgs;

	private final IDelegator delegator;

	/**
	 * constructor que especifica, anticipadament, el comportament de la crida a
	 * stubbar
	 *
	 * @param delegator
	 */
	public CallActionConfig(final IDelegator delegator) {
		this.delegator = delegator;
	}

	public Method getMethod() {
		return method;
	}

	public void setCall(final Method method, final Object[] callingArgsArray) {
		this.method = method;
		if (callingArgsArray == null) {
			this.callingArgs = new Object[0];
		} else {
			this.callingArgs = callingArgsArray.clone();
		}
	}

	public Object[] getCallingArgsValues() {
		return callingArgs.clone();
	}

	public IDelegator getDelegator() {
		return delegator;
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
