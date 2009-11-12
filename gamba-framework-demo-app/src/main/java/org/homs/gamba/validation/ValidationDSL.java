package org.homs.gamba.validation;

import java.util.HashMap;
import java.util.Map;

public class ValidationDSL implements SetTargetSyntax, RejectSyntax  {

	final Map<String, String[]> paramsMap;
	final Map<String, String> rejectionsMap;

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
			this.currentParamValue = null;
		}
		this.currentStateFailed = false;
		return this;
	}

	public RejectSyntax forParamName(final String targetParamName, final String paramAlias) {
		this.currentParamName = paramAlias;
		if (this.paramsMap.get(targetParamName) != null && this.paramsMap.get(targetParamName).length > 0) {
			this.currentParamValue = this.paramsMap.get(targetParamName);
		} else {
			this.currentParamValue = null;
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

	public void throwErrors() throws ValidationException {
		if (!rejectionsMap.isEmpty()) {
			throw new ValidationException(this.rejectionsMap);
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
		currentStateFailed = new RejectIfNull().mustBeReject(currentParamValue);
		return this;
	}
	public RejectSyntax rejectIfNotNull() {
		currentStateFailed = new RejectIfNotNull().mustBeReject(currentParamValue);
		return this;
	}


	public RejectSyntax rejectIfEmpty() {
		currentStateFailed = new RejectIfEmpty().mustBeReject(currentParamValue);
		return this;
	}
	public RejectSyntax rejectIfNotEmpty() {
		currentStateFailed = new RejectIfNotEmpty().mustBeReject(currentParamValue);
		return this;
	}
	public RejectSyntax rejectIfNotInteger() {
		currentStateFailed = new RejectIfNotInteger().mustBeReject(currentParamValue);
		return this;
	}
	public RejectSyntax rejectIfNotLong() {
		currentStateFailed = new RejectIfNotLong().mustBeReject(currentParamValue);
		return this;
	}
	public RejectSyntax rejectIfNotDouble() {
		currentStateFailed = new RejectIfNotDouble().mustBeReject(currentParamValue);
		return this;
	}


	public RejectSyntax rejectIfGreaterThan(final Double value) {
		currentStateFailed = new RejectIfGreaterThan(value).mustBeReject(currentParamValue);
		return this;
	}

	public RejectSyntax rejectIfLessThan(final Double value) {
		currentStateFailed = new RejectIfLessThan(value).mustBeReject(currentParamValue);
		return this;
	}

	public RejectSyntax rejectIfEqualsThan(final Double value) {
		currentStateFailed = new RejectIfEqualsThan(value).mustBeReject(currentParamValue);
		return this;
	}

	public RejectSyntax rejectIfNotEqualsThan(final Double value) {
		currentStateFailed = new RejectIfNotEqualsThan(value).mustBeReject(currentParamValue);
		return this;
	}

	public RejectSyntax rejectIfEquals(final String value) {
		currentStateFailed = new RejectIfEquals(value).mustBeReject(currentParamValue);
		return this;
	}

	public RejectSyntax rejectIfNotEquals(final String value) {
		currentStateFailed = new RejectIfNotEquals(value).mustBeReject(currentParamValue);
		return this;
	}

	public RejectSyntax rejectIfLengthIsOutOfRange(final Integer min, final Integer max) {
		currentStateFailed = new RejectIfLengthIsOutOfRange(min, max).mustBeReject(currentParamValue);
		return this;
	}


//	public RejectSyntax rejectIfGreaterThan(final Double value) {
//		try {
//			for (final String s : currentParamValue) {
//				if (Double.valueOf(s) > value) {
//					currentStateFailed = true;
//					break;
//				}
//			}
//		} catch (final Exception e) {
//			currentStateFailed = true;
//		}
//		return this;
//	}
//
//	public RejectSyntax rejectIfLessThan(final Double value) {
//		try {
//			for (final String s : currentParamValue) {
//				if (Double.valueOf(s) < value) {
//					currentStateFailed = true;
//					break;
//				}
//			}
//		} catch (final Exception e) {
//			currentStateFailed = true;
//		}
//		return this;
//	}
//
//	public RejectSyntax rejectIfEqualsThan(final Double value) {
//		try {
//			for (final String s : currentParamValue) {
//				if (Double.valueOf(s) == value) {
//					currentStateFailed = true;
//					break;
//				}
//			}
//		} catch (final Exception e) {
//			currentStateFailed = true;
//		}
//		return this;
//	}
//
//	public RejectSyntax rejectIfNotEqualsThan(final Double value) {
//		try {
//			for (final String s : currentParamValue) {
//				if (Double.valueOf(s) != value) {
//					currentStateFailed = true;
//					break;
//				}
//			}
//		} catch (final Exception e) {
//			currentStateFailed = true;
//		}
//		return this;
//	}
//
//	public RejectSyntax rejectIfEquals(final String str) {
//		try {
//			for (final String s : currentParamValue) {
//				if (str.equals(s)) {
//					currentStateFailed = true;
//					break;
//				}
//			}
//		} catch (final Exception e) {
//			currentStateFailed = true;
//		}
//		return this;
//	}
//
//
//	public RejectSyntax rejectIfNotEquals(final String str) {
//		try {
//			for (final String s : currentParamValue) {
//				if (!s.equals(s)) {
//					currentStateFailed = true;
//					break;
//				}
//			}
//		} catch (final Exception e) {
//			currentStateFailed = true;
//		}
//		return this;
//	}
//
//
//	public RejectSyntax rejectIfLengthIsGreaterThan(final int length) {
//		try {
//			for (final String s : currentParamValue) {
//				if (s.length() > length) {
//					currentStateFailed = true;
//					break;
//				}
//			}
//		} catch (final Exception e) {
//			currentStateFailed = true;
//		}
//		return this;
//	}
//
//	public RejectSyntax rejectIfLengthIsLessThan(final int length) {
//		try {
//			for (final String s : currentParamValue) {
//				if (s.length() < length) {
//					currentStateFailed = true;
//					break;
//				}
//			}
//		} catch (final Exception e) {
//			currentStateFailed = true;
//		}
//		return this;
//	}
//
//
//	public RejectSyntax rejectIfLengthIsGreaterOrEqualsThan(final int length) {
//		try {
//			for (final String s : currentParamValue) {
//				if (s.length() >= length) {
//					currentStateFailed = true;
//					break;
//				}
//			}
//		} catch (final Exception e) {
//			currentStateFailed = true;
//		}
//		return this;
//	}
//
//
//	public RejectSyntax rejectIfLengthIsLessOrEqualsThan(final int length) {
//		try {
//			for (final String s : currentParamValue) {
//				if (s.length() <= length) {
//					currentStateFailed = true;
//					break;
//				}
//			}
//		} catch (final Exception e) {
//			currentStateFailed = true;
//		}
//		return this;
//	}
//
//
//	public RejectSyntax rejectIfLengthIsOutOfRange(final int min, final int max) {
//		try {
//			for (final String s : currentParamValue) {
//				if (s.length() < min || s.length() > max) {
//					currentStateFailed = true;
//					break;
//				}
//			}
//		} catch (final Exception e) {
//			currentStateFailed = true;
//		}
//		return this;
//	}

	public RejectSyntax reject(final RejectStatement rejectStatement) {
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


//public RejectSyntax rejectIfNull() {
//	for (final String s : currentParamValue) {
//		if (s == null) {
//			currentStateFailed = true;
//			break;
//		}
//	}
//	return this;
//}
class RejectIfNull extends RejectStatement {
	@Override
	public boolean reject(final String value) {
		return value == null;
	}
}
class RejectIfNotNull extends RejectStatement {
	@Override
	public boolean reject(final String value) {
		return value != null;
	}
}
class RejectIfEmpty extends RejectStatement {
	@Override
	public boolean reject(final String value) {
		return "".equals(value);
	}
}
class RejectIfNotEmpty extends RejectStatement {
	@Override
	public boolean reject(final String value) {
		return !"".equals(value);
	}
}
class RejectIfNotInteger extends RejectStatement {
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
class RejectIfNotLong extends RejectStatement {
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
class RejectIfNotDouble extends RejectStatement {
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





class RejectIfGreaterThan extends RejectStatement {
	private final Double doubleValue;
	public RejectIfGreaterThan(final Double doubleValue) {
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
class RejectIfLessThan extends RejectStatement {
	private final Double doubleValue;
	public RejectIfLessThan(final Double doubleValue) {
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
class RejectIfEqualsThan extends RejectStatement {
	private final Double doubleValue;
	public RejectIfEqualsThan(final Double doubleValue) {
		this.doubleValue = doubleValue;
	}
	@Override
	public boolean reject(final String value) {
		if (Double.valueOf(value) == doubleValue) {
			return true;
		}
		return false;
	}
}
class RejectIfNotEqualsThan extends RejectStatement {
	private final Double doubleValue;
	public RejectIfNotEqualsThan(final Double doubleValue) {
		this.doubleValue = doubleValue;
	}
	@Override
	public boolean reject(final String value) {
		if (Double.valueOf(value) != doubleValue) {
			return true;
		}
		return false;
	}
}

//--rejectIfGreaterThan(final Double value) {
//--rejectIfLessThan(final Double value) {
//--rejectIfEqualsThan(final Double value) {
//--rejectIfNotEqualsThan(final Double value) {

class RejectIfEquals extends RejectStatement {
	private final String stringValue;
	public RejectIfEquals(final String stringValue) {
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
class RejectIfNotEquals extends RejectStatement {
	private final String stringValue;
	public RejectIfNotEquals(final String stringValue) {
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

//--rejectIfEquals(final String str) {
//--rejectIfNotEquals(final String str) {



//rejectIfLengthIsGreaterThan(final int length) {
//rejectIfLengthIsLessThan(final int length) {
//rejectIfLengthIsGreaterOrEqualsThan(final int length) {
//rejectIfLengthIsLessOrEqualsThan(final int length) {
//rejectIfLengthIsOutOfRange(final int min, final int max) {


class RejectIfLengthIsOutOfRange extends RejectStatement {
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
