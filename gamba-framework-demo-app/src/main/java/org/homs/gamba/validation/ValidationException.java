package org.homs.gamba.validation;

import java.util.Map;

@SuppressWarnings("serial")
public class ValidationException extends RuntimeException {

	private final Map<String, String> rejectionsMap;

	public ValidationException(final Map<String, String> rejectionsMap) {
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
		return "\nrejected http parameters: " + rejectionsMap.toString();
	}

}