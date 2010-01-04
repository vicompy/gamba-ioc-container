package org.lechuga.mvc.validation;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class ValidatorTest {

	@Test
	public void testAgeValidator() {

		try {
			checkAge(null);
			fail("");
		} catch (final Exception e) {

		}
		Assert.assertEquals("{age=is empty}", checkAge( new String[] { null }).toString());
		Assert.assertEquals(null, checkAge( new String[] { "" }));
		Assert.assertEquals("{age=is not numeric}", checkAge( new String[] { "a" }).toString());
		Assert.assertEquals(null, checkAge( new String[] { "12" }));
	}

	@Test
	public void testNameValidator() {

		try {
			checkAge(null);
			fail("");
		} catch (final Exception e) {

		}
		Assert.assertEquals("{name=is empty}", checkName( new String[] { null }).toString());
		Assert.assertEquals("{name=is empty}", checkName( new String[] { "" }).toString());
		Assert.assertEquals(null, checkName( new String[] { "-" }));
		Assert.assertEquals("{name=minimum is 2}", checkName( new String[] { "a" }).toString());
		Assert.assertEquals(null, checkName( new String[] { "aa" }));
		Assert.assertEquals(null, checkName( new String[] { "aaa" }));
		Assert.assertEquals(null, checkName( new String[] { "aaaa" }));
		Assert.assertEquals(null, checkName( new String[] { "aaaaa" }));
		Assert.assertEquals("{name=5 is maximum}", checkName( new String[] { "aaaaaa" }).toString());
		Assert.assertEquals("{name=5 is maximum}", checkName( new String[] { "aaaaaaa" }).toString());
		Assert.assertEquals("{name=5 is maximum}", checkName( new String[] { "aaaaaaaa" }).toString());
		Assert.assertEquals("{name=5 is maximum}", checkName( new String[] { "aaaaaaaaa" }).toString());

		Assert.assertEquals(null, checkName( new String[] { "1.5" }));
		Assert.assertEquals(null, checkName( new String[] { "1.55555555555" }));
	}



	public Map<String, String> checkAge( final String[] values) {
		final Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("age", values);
		final Validator v = new AgeValidator();
		return v.publicValidate(params);
	}

	public Map<String, String> checkName( final String[] values) {
		final Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("name", values);
		final Validator v = new NameValidator();
		return v.publicValidate(params);
	}

}

class AgeValidator extends Validator {

	@SuppressWarnings("unchecked")
	@Override
	protected Map<String, String> validate(final Map<String, String[]> params) {
		return predicatesOr(
			predicate(notEmpty("age", "is empty"), isInteger("age", "is not numeric")),
			predicate(isEmpty("age", "is not empty nor numeric"))
		);
	}
}

class NameValidator extends Validator {

	@SuppressWarnings("unchecked")
	@Override
	protected Map<String, String> validate(final Map<String, String[]> params) {
		return predicatesOr(
			predicate(notEmpty("name", "is empty"), isLengthGreaterThan("name", 1, "minimum is 2"),isLengthLessThan("name", 6, "5 is maximum")),
			predicate(notEmpty("name", "is empty"), isEqualsThan("name", "-", "")),
			predicate(isDouble("name", "is not double empty"))
		);
	}
}
