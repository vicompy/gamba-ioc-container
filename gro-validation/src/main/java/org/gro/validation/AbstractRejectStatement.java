package org.gro.validation;

public abstract class AbstractRejectStatement {

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