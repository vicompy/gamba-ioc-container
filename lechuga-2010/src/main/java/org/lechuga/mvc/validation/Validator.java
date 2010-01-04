package org.lechuga.mvc.validation;

import java.util.HashMap;
import java.util.Map;

public abstract class Validator {

	private Map<String, String[]> params;
//
//	protected Validator(final Map<String, String[]> params) {
//
//		super();
//		this.params = params;
//	}

	protected String[] getParamValue(final String paramName) {

		final String[] values = params.get(paramName);
		if (values == null) {
			throw new RuntimeException("validator: param not found: " + paramName);
		}
		return values;
	}

	protected Pair notEmpty(final String paramName, final String errorMessage) {
		final String[] values = getParamValue(paramName);
		for (final String s : values) {
			if (s == null || "".equals(s)) {
				return new Pair(paramName, errorMessage);
			}
		}
		return null;
	}

	protected Pair isEmpty(final String paramName, final String errorMessage) {
		final String[] values = getParamValue(paramName);
		for (final String s : values) {
			if (!"".equals(s)) {
				return new Pair(paramName, errorMessage);
			}
		}
		return null;
	}

	protected Pair isLengthLessThan(final String paramName, final int length, final String errorMessage) {
		final String[] values = getParamValue(paramName);
		for (final String s : values) {
			if (s == null || s.length() >= length) {
				return new Pair(paramName, errorMessage);
			}
		}
		return null;
	}

	protected Pair isLengthGreaterThan(final String paramName, final int length, final String errorMessage) {
		final String[] values = getParamValue(paramName);
		for (final String s : values) {
			if (s == null || s.length() <= length) {
				return new Pair(paramName, errorMessage);
			}
		}
		return null;
	}

	protected Pair isEqualsThan(final String paramName, final String value, final String errorMessage) {
		final String[] values = getParamValue(paramName);
		for (final String s : values) {
			if (!value.equals(s)) {
				return new Pair(paramName, errorMessage);
			}
		}
		return null;
	}

	protected Pair isInteger(final String paramName, final String errorMessage) {
		final String[] values = getParamValue(paramName);
		for (final String s : values) {
			try {
				Integer.valueOf(s);
			} catch (final NumberFormatException e) {
				return new Pair(paramName, errorMessage);
			}
		}
		return null;
	}

	protected Pair isDouble(final String paramName, final String errorMessage) {
		final String[] values = getParamValue(paramName);
		for (final String s : values) {
			try {
				Double.valueOf(s);
			} catch (final NumberFormatException e) {
				return new Pair(paramName, errorMessage);
			} catch (final NullPointerException e) {
				return new Pair(paramName, errorMessage);
			}
		}
		return null;
	}

	protected Map<String, String> predicate(final Pair... pairs) {

		final Map<String, String> result = new HashMap<String, String>();

		for (final Pair p : pairs) {
			if (p != null && result.get(p.paramName) == null) {
				result.put(p.paramName, p.errorMessage);
			}
		}

		if (result.isEmpty()) {
			return null;
		} else {
			return result;
		}
	}

	protected Map<String, String> predicatesOr(final Map<String, String>... predicates) {

		Map<String, String> r = null;

		for (int i = predicates.length - 1; i >= 0; i--) {
			final Map<String, String> p = predicates[i];

			if (p != null) {
				r = p;
			} else {
				return null;
			}

		}

		return r;
	}

	abstract protected Map<String, String> validate(Map<String, String[]> params);

	public Map<String, String> publicValidate(final Map<String, String[]> params) {
		this.params = params;
		return validate(params);
	}


}
