package org.homs.gamba.stub.delegator;

public abstract class AbstractDelegator implements IDelegator {

	protected int index = 0;
	protected final Object[] sequence;

	public AbstractDelegator(final Object single) {
		this.sequence = new Object[] { single };
	}

	public AbstractDelegator(final Object... sequence) {
		this.sequence = sequence;
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
