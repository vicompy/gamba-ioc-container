package org.gro.validation.exception;

import java.util.Map;

@SuppressWarnings("serial")
public class RejectionException extends RuntimeException {

	private final Map<String, String> rejectionsMap;

	public RejectionException(final Map<String, String> rejectionsMap) {
		super("http parameters validation failed");
		this.rejectionsMap = rejectionsMap;
	}

	public Map<String, String> getRejectionsMap() {
		return rejectionsMap;
	}

	public String getErrorMessageFor(final String parameterName) {
		return rejectionsMap.get(parameterName);
	}

	@Override
	public String toString() {
		return super.toString() + "\nrejected http parameters: " + rejectionsMap;
	}

}