package org.homs.gamba.validation;

public abstract class RejectStatement {

	protected abstract boolean reject(String value);

	public boolean mustBeReject(final String[] values) {
		for (final String s : values) {
			if (reject(s)) {
				return true;
			}
		}
		return false;
	}

}