package org.gro.validation;

import java.util.HashMap;
import java.util.Map;

import org.gro.validation.exception.RejectionException;
import org.gro.validation.exception.ValidationException;

public class ValidationDSL implements SetTargetSyntax, RejectSyntax {

	private final Map<String, String[]> paramsMap;
	private final Map<String, String> rejectionsMap;

	private String currentParamName;
	private String[] currentParamValue;
	private Boolean currentStateFailed;

	private ValidationDSL(final Map<String, String[]> paramsMap) {
		this.paramsMap = paramsMap;
		this.rejectionsMap = new HashMap<String, String>();
	}

	public static SetTargetSyntax getInstance(final Map<String, String[]> paramsMap) {
		return new ValidationDSL(paramsMap);
	}

	public RejectSyntax forParamName(final String targetParamName) {
		this.currentParamName = targetParamName;
		if (this.paramsMap.get(targetParamName) != null && this.paramsMap.get(targetParamName).length > 0) {
			this.currentParamValue = this.paramsMap.get(targetParamName);
		} else {
			throw new ValidationException("attribute not found: " + targetParamName);
		}
		this.currentStateFailed = false;
		return this;
	}

	public SetTargetSyntax validate() {
		return validate("");
	}

	public SetTargetSyntax validate(final String errorMessage) {
		if (currentStateFailed) {
			this.rejectionsMap.put(currentParamName, errorMessage);
		}
		return this;
	}

	public void throwErrors() throws RejectionException {
		if (!rejectionsMap.isEmpty()) {
			throw new RejectionException(this.rejectionsMap);
		}
	}

	public Map<String, String> getRejectionsMap() {
		return rejectionsMap;
	}

	public RejectSyntax reject() {
		currentStateFailed = true;
		return this;
	}

	public RejectSyntax rejectIfNull() {
		if (!currentStateFailed && new RejectIfNull().mustBeReject(currentParamValue)) {
			currentStateFailed = true;
		}
		return this;
	}

	public RejectSyntax rejectIfNotNull() {
		if (!currentStateFailed && new RejectIfNotNull().mustBeReject(currentParamValue)) {
			currentStateFailed = true;
		}
		return this;
	}

	public RejectSyntax rejectIfEmpty() {
		if (!currentStateFailed && new RejectIfEmpty().mustBeReject(currentParamValue)) {
			currentStateFailed = true;
		}
		return this;
	}

	public RejectSyntax rejectIfNotEmpty() {
		if (!currentStateFailed && new RejectIfNotEmpty().mustBeReject(currentParamValue)) {
			currentStateFailed = true;
		}
		return this;
	}

	public RejectSyntax rejectIfNotInteger() {
		if (!currentStateFailed && new RejectIfNotInteger().mustBeReject(currentParamValue)) {
			currentStateFailed = true;
		}
		return this;
	}

	public RejectSyntax rejectIfNotLong() {
		if (!currentStateFailed && new RejectIfNotLong().mustBeReject(currentParamValue)) {
			currentStateFailed = true;
		}
		return this;
	}

	public RejectSyntax rejectIfNotDouble() {
		if (!currentStateFailed && new RejectIfNotDouble().mustBeReject(currentParamValue)) {
			currentStateFailed = true;
		}
		return this;
	}

	public RejectSyntax rejectIfGreaterThan(final Double value) {
		if (!currentStateFailed && new RejectIfGreaterThan(value).mustBeReject(currentParamValue)) {
			currentStateFailed = true;
		}
		return this;
	}

	public RejectSyntax rejectIfLessThan(final Double value) {
		if (!currentStateFailed && new RejectIfLessThan(value).mustBeReject(currentParamValue)) {
			currentStateFailed = true;
		}
		return this;
	}

	public RejectSyntax rejectIfEqualsThan(final Double value) {
		if (!currentStateFailed && new RejectIfEqualsThan(value).mustBeReject(currentParamValue)) {
			currentStateFailed = true;
		}
		return this;
	}

	public RejectSyntax rejectIfNotEqualsThan(final Double value) {
		if (!currentStateFailed && new RejectIfNotEqualsThan(value).mustBeReject(currentParamValue)) {
			currentStateFailed = true;
		}
		return this;
	}

	public RejectSyntax rejectIfEquals(final String value) {
		if (!currentStateFailed && new RejectIfEquals(value).mustBeReject(currentParamValue)) {
			currentStateFailed = true;
		}
		return this;
	}

	public RejectSyntax rejectIfNotEquals(final String value) {
		if (!currentStateFailed && new RejectIfNotEquals(value).mustBeReject(currentParamValue)) {
			currentStateFailed = true;
		}
		return this;
	}

	public RejectSyntax rejectIfLengthIsOutOfRange(final Integer min, final Integer max) {
		if (!currentStateFailed && new RejectIfLengthIsOutOfRange(min, max).mustBeReject(currentParamValue)) {
			currentStateFailed = true;
		}
		return this;
	}

	public RejectSyntax reject(final AbstractRejectStatement rejectStatement) {
		try {
			for (final String s : currentParamValue) {
				if (rejectStatement.reject(s)) {
					currentStateFailed = true;
					break;
				}
			}
		} catch (final Exception e) {
			currentStateFailed = true;
		}
		return this;
	}

}

class RejectIfNull extends AbstractRejectStatement {
	@Override
	public boolean reject(final String value) {
		return value == null;
	}
}

class RejectIfNotNull extends AbstractRejectStatement {
	@Override
	public boolean reject(final String value) {
		return value != null;
	}
}

class RejectIfEmpty extends AbstractRejectStatement {
	@Override
	public boolean reject(final String value) {
		return "".equals(value);
	}
}

class RejectIfNotEmpty extends AbstractRejectStatement {
	@Override
	public boolean reject(final String value) {
		return !"".equals(value);
	}
}

class RejectIfNotInteger extends AbstractRejectStatement {
	@Override
	public boolean reject(final String value) {
		try {
			Integer.valueOf(value);
		} catch (final Exception e) {
			return true;
		}
		return false;
	}
}

class RejectIfNotLong extends AbstractRejectStatement {
	@Override
	public boolean reject(final String value) {
		try {
			Long.valueOf(value);
		} catch (final Exception e) {
			return true;
		}
		return false;
	}
}

class RejectIfNotDouble extends AbstractRejectStatement {
	@Override
	public boolean reject(final String value) {
		try {
			Double.valueOf(value);
		} catch (final Exception e) {
			return true;
		}
		return false;
	}
}

class RejectIfGreaterThan extends AbstractRejectStatement {
	private final Double doubleValue;

	public RejectIfGreaterThan(final Double doubleValue) {
		super();
		this.doubleValue = doubleValue;
	}

	@Override
	public boolean reject(final String value) {
		if (Double.valueOf(value) > doubleValue) {
			return true;
		}
		return false;
	}
}

class RejectIfLessThan extends AbstractRejectStatement {
	private final Double doubleValue;

	public RejectIfLessThan(final Double doubleValue) {
		super();
		this.doubleValue = doubleValue;
	}

	@Override
	public boolean reject(final String value) {
		if (Double.valueOf(value) < doubleValue) {
			return true;
		}
		return false;
	}
}

class RejectIfEqualsThan extends AbstractRejectStatement {
	private final Double doubleValue;

	public RejectIfEqualsThan(final Double doubleValue) {
		super();
		this.doubleValue = doubleValue;
	}

	@Override
	public boolean reject(final String value) {
		if (Double.valueOf(value).equals(doubleValue)) {
			return true;
		}
		return false;
	}
}

class RejectIfNotEqualsThan extends AbstractRejectStatement {
	private final Double doubleValue;

	public RejectIfNotEqualsThan(final Double doubleValue) {
		super();
		this.doubleValue = doubleValue;
	}

	@Override
	public boolean reject(final String value) {
		if (!Double.valueOf(value).equals(doubleValue)) {
			return true;
		}
		return false;
	}
}

class RejectIfEquals extends AbstractRejectStatement {
	private final String stringValue;

	public RejectIfEquals(final String stringValue) {
		super();
		this.stringValue = stringValue;
	}

	@Override
	public boolean reject(final String value) {
		if (stringValue.equals(value)) {
			return true;
		}
		return false;
	}
}

class RejectIfNotEquals extends AbstractRejectStatement {
	private final String stringValue;

	public RejectIfNotEquals(final String stringValue) {
		super();
		this.stringValue = stringValue;
	}

	@Override
	public boolean reject(final String value) {
		if (!stringValue.equals(value)) {
			return true;
		}
		return false;
	}
}

class RejectIfLengthIsOutOfRange extends AbstractRejectStatement {
	private final Integer min, max;

	public RejectIfLengthIsOutOfRange(final Integer min, final Integer max) {
		super();
		this.min = min;
		this.max = max;
	}

	@Override
	public boolean reject(final String value) {
		if (value.length() < min || value.length() > max) {
			return true;
		}
		return false;
	}

}
