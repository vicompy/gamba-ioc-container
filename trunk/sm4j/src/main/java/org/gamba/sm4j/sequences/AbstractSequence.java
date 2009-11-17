package org.gamba.sm4j.sequences;

public abstract class AbstractSequence implements ISequence {

	protected int index = 0;
	protected final Object[] sequence;

	public AbstractSequence(final Object... sequence) {
		this.sequence = sequence;
	}

	public void reset() {
		index = 0;
	}

	public boolean checkIfFinished() {
		return index >= sequence.length;
	}

	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer();
		strb.append(this.getClass().getSimpleName());
		strb.append(" [");
		for (int i = 0; i < sequence.length; i++) {
			if (i == index) {
				strb.append('(');
			}
			strb.append(sequence[i].toString());
			if (i == index) {
				strb.append(')');
			}
			if (i < sequence.length - 1) {
				strb.append(',');
			}
		}
		if (index == sequence.length) {
			strb.append("()");
		}
		strb.append(']');
		return strb.toString();
	}

}
