package org.gamba.mocks;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.gamba.mocks.sequences.ISequence;

/**
 * entitat que registra una crida a l'objecte mock.
 *
 * @author mhoms
 */
class CallActionConfig {

	private Method method;
	private Object[] callingArgs;
	private boolean[] anyMask;
	private final ISequence sequence;

	/**
	 * constructor que especifica, anticipadament, el comportament de la crida a
	 * mockar. L'objecte es construeix amb la seqüència i la màscara de
	 * paràmetres a aplicar; més tard es setteja el mètode i els valors
	 * d'argument.
	 *
	 * @param sequence
	 * @param anyMask
	 */
	public CallActionConfig(final ISequence sequence, final boolean[] anyMask) {
		this.sequence = sequence;
		this.anyMask = anyMask.clone();
	}

	/**
	 * setter que acaba de definir l'objecte, completant-lo.
	 *
	 * @param method
	 * @param callingArgsArray
	 */
	public void setCall(final Method method, final Object[] callingArgsArray) {
		this.method = method;
		if (callingArgsArray == null) {
			this.callingArgs = new Object[0];
		} else {
			this.callingArgs = callingArgsArray.clone();
		}

		// coneixent el nombre d'arguments, fer un resize de la màscara pq
		// coincideixin les mides. Això permet aplicar màscares de mides
		// diferents al nombre d'arguments de la crida.
		anyMask = Arrays.copyOf(anyMask, callingArgs.length);
	}

	public Object[] getCallingArgsValues() {
		return callingArgs.clone();
	}

	public ISequence getDelegator() {
		return sequence;
	}

	public Method getMethod() {
		return method;
	}

	public boolean[] getIsAnied() {
		return anyMask.clone();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer(100);
		strb.append("call setup: ");
		strb.append(getMethod().getName());
		strb.append('(');
		for (int i = 0; i < callingArgs.length; i++) {
			if (anyMask[i]) {
				strb.append('*');
			} else {
				strb.append(callingArgs[i].toString());
			}
			if (i < callingArgs.length - 1) {
				strb.append(',');
			}
		}
		strb.append("), sequence: ");
		strb.append(sequence.toString());
		return strb.toString();
	}

}
