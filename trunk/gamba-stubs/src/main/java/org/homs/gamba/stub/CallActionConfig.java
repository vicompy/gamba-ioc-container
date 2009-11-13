package org.homs.gamba.stub;

import java.lang.reflect.Method;

import org.homs.gamba.stub.bsyntax.ForAny;
import org.homs.gamba.stub.delegator.IDelegator;

/**
 * entitat que registra una crida a l'objecte stub.
 *
 * @author mhoms
 */
class CallActionConfig {

	private Method method;
	private Object[] callingArgs;
	private final ForAny[] forAnies;
	private boolean[] isAnied;

	private final IDelegator delegator;

	/**
	 * constructor que especifica, anticipadament, el comportament de la crida a
	 * stubbar
	 *
	 * @param delegator
	 */
	public CallActionConfig(final IDelegator delegator, final ForAny[] forAnies) {
		this.delegator = delegator;
		this.forAnies = forAnies;
	}

	public void setCall(final Method method, final Object[] callingArgsArray) {
		this.method = method;
		if (callingArgsArray == null) {
			this.callingArgs = new Object[0];
		} else {
			this.callingArgs = callingArgsArray.clone();
		}
		this.isAnied = new boolean[this.callingArgs.length];

		for(int i = 0; i < this.isAnied.length; i++) {
			isAnied[i] = false;
			for (int j = 0; j < this.forAnies.length; j++) {
				if (i == forAnies[j].getAniedArgNumber()) {
					isAnied[i] = true;
				}
			}
		}
	}

	public Object[] getCallingArgsValues() {
		return callingArgs.clone();
	}

	public IDelegator getDelegator() {
		return delegator;
	}

	public Method getMethod() {
		return method;
	}

	public boolean[] getIsAnied() {
		return isAnied;
	}

	/**
	 * TODO retorna una representació en String de l'estat d'aquest objecte
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer(100);
		strb.append("call setup: ");
		strb.append(getMethod().getName());
		strb.append('(');
		for (int i = 0; i < callingArgs.length; i++) {
			if (isAnied[i]) {
				strb.append('*');
			} else {
				strb.append(callingArgs[i].toString());
			}
			if (i < callingArgs.length -1) {
				strb.append(',');
			}
		}
		strb.append("), delegator: ");
		strb.append(delegator.toString());
		return strb.toString();
	}

}
