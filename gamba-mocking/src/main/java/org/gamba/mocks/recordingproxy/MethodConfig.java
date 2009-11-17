package org.gamba.mocks.recordingproxy;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.gamba.mocks.sequences.ISequence;

public class MethodConfig {

	private final ISequence sequence;
	private final Boolean[] argMask;
	private final Method method;
	private final Object[] arguments;

	public MethodConfig(final ISequence sequence, final Boolean[] argMask, final Method method,
			final Object[] arguments) {
		super();
		this.sequence = sequence;

		this.method = method;
		if (arguments == null) {
			this.arguments = new Object[]{};
			this.argMask = new Boolean[]{};
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
