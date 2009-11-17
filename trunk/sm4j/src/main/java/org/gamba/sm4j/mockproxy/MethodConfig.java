package org.gamba.sm4j.mockproxy;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.gamba.sm4j.exception.GambaMockingException;
import org.gamba.sm4j.sequences.ISequence;

/**
 * encapsula la informació referent a la configuració d'una expectació. Degut a
 * la naturalesa fluent de les operacions, aquesta entitat serà controlada
 * transaccionalment, i construïda de cop.
 *
 * @author mhoms
 */
public class MethodConfig {

	private final ISequence sequence;
	private final Boolean[] argMask;
	private final Method method;
	private final Object[] arguments;

	public MethodConfig(final ISequence sequence, final Boolean[] argMask, final Method method,
			final Object[] arguments) {
		super();
		this.sequence = sequence;

		// preveu l'ús de màscara en només alguns paràmetres; doncs en aplicar
		// una màscara, tots els arguments han de passare per 'eq()' ó per
		// 'anyXXX()'.
		if (arguments != null && argMask.length > 0) {
			if (arguments.length != argMask.length) {
				throw new GambaMockingException(
						"en utilitzar almenys una màscara, s'ha d'enmascarar a tots els paràmetres.");
			}
		}

		this.method = method;
		if (arguments == null) {
			this.arguments = new Object[] {};
			this.argMask = new Boolean[] {};
		} else {
			this.arguments = arguments.clone();
			this.argMask = Arrays.copyOf(argMask, arguments.length);
		}

		// TODO
		for (int i = 0; i < this.argMask.length; i++) {
			if (this.argMask[i] == null) {
				this.argMask[i] = false;
			}
		}
	}

	public ISequence getSequence() {
		return sequence;
	}

	public Method getMethod() {
		return method;
	}

	public Object[] getArguments() {
		return arguments.clone();
	}

	public Boolean[] getArgMask() {
		return argMask.clone();
	}

	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer();

		strb.append(method.getName());
		strb.append('(');
		for (int i = 0; i < arguments.length; i++) {
			if (argMask[i]) {
				strb.append('*');
			} else {
				strb.append(arguments[i].toString());
			}
			if (i < arguments.length - 1) {
				strb.append(',');
			}
		}
		strb.append(") ==> ");
		strb.append(sequence.toString());
		strb.append('\n');

		return strb.toString();
	}
}
