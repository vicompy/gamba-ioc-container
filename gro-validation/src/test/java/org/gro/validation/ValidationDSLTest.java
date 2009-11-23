package org.gro.validation;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

import org.gro.validation.RejectSyntax;
import org.gro.validation.ValidationDSL;
import org.gro.validation.exception.RejectionException;
import org.junit.Test;

public class ValidationDSLTest {

	private final Map<String, String[]> paramsMap;

	public ValidationDSLTest() {
		paramsMap = new HashMap<String, String[]>();
		paramsMap.put("shortName", new String[] { "mhc" });
		paramsMap.put("Name", new String[] { "m. homs" });
		paramsMap.put("longName", new String[] { "marti homs" });
		paramsMap.put("numericAge", new String[] { "27" });
		paramsMap.put("literalAge", new String[] { "vintiset" });
		paramsMap.put("empty", new String[] { "" });
		paramsMap.put("null", new String[] { null });

		paramsMap.put("50", new String[] { "50" });
		paramsMap.put("100", new String[] { "100" });
	}

	/**
	 * valida un mateix predicat contra dos atributs: el primer destinat a ésser
	 * acceptat, el segon a ser rebutjat.
	 *
	 * @author mhoms
	 */
	abstract class H {

		final String paramName1;
		final String paramName2;

		public H(final String paramName1, final String paramName2) {
			super();
			this.paramName1 = paramName1;
			this.paramName2 = paramName2;
		}

		public void validate() {
			this.v(ValidationDSL.getInstance(paramsMap).forParamName(paramName1));

			try {
				this.v(ValidationDSL.getInstance(paramsMap).forParamName(paramName2));
				fail();
			} catch (final Exception e) {
			}
		}

		protected abstract void v(final RejectSyntax t);

	}

	@Test
	public void testNull() {

		new H("shortName", "null") {
			@Override
			protected void v(final RejectSyntax t) {
				t.rejectIfNull().validate("hola").throwErrors();
			}
		}.validate();
	}

	@Test
	public void testNotNull() {

		new H("null", "empty") {
			@Override
			protected void v(final RejectSyntax t) {
				t.rejectIfNotNull().validate("hola").throwErrors();
			}
		}.validate();
	}

	@Test
	public void testEmpty() {

		new H("shortName", "empty") {
			@Override
			protected void v(final RejectSyntax t) {
				t.rejectIfEmpty().validate("hola").throwErrors();
			}
		}.validate();
	}

	@Test
	public void testNotEmpty() {

		new H("empty", "shortName") {
			@Override
			protected void v(final RejectSyntax t) {
				t.rejectIfNotEmpty().validate("hola").throwErrors();
			}
		}.validate();
	}

	@Test
	public void testNotInteger() {

		new H("numericAge", "shortName") {
			@Override
			protected void v(final RejectSyntax t) {
				t.rejectIfNotInteger().validate("hola").throwErrors();
			}
		}.validate();
	}

	@Test
	public void testNotLong() {

		new H("numericAge", "shortName") {
			@Override
			protected void v(final RejectSyntax t) {
				t.rejectIfNotLong().validate("hola").throwErrors();
			}
		}.validate();
	}

	@Test
	public void testNotDouble() {

		new H("numericAge", "shortName") {
			@Override
			protected void v(final RejectSyntax t) {
				t.rejectIfNotDouble().validate("hola").throwErrors();
			}
		}.validate();
	}

	@Test
	public void testIfGreaterThan() {

		new H("50", "100") {
			@Override
			protected void v(final RejectSyntax t) {
				t.rejectIfGreaterThan(75.0).validate("hola").throwErrors();
			}
		}.validate();
	}

	@Test
	public void testIfLessThan() {

		new H("100", "50") {
			@Override
			protected void v(final RejectSyntax t) {
				t.rejectIfLessThan(75.0).validate("hola").throwErrors();
			}
		}.validate();
	}

	@Test
	public void testIfEqualsThan() {

		new H("100", "50") {
			@Override
			protected void v(final RejectSyntax t) {
				t.rejectIfEqualsThan(50.0).validate("hola").throwErrors();
			}
		}.validate();
	}

	@Test
	public void testIfNotEqualsThan() {

		new H("100", "50") {
			@Override
			protected void v(final RejectSyntax t) {
				t.rejectIfNotEqualsThan(100.0).validate("hola").throwErrors();
			}
		}.validate();
	}

	@Test
	public void testIfEquals() {

		new H("100", "50") {
			@Override
			protected void v(final RejectSyntax t) {
				t.rejectIfEquals("50").validate("hola").throwErrors();
			}
		}.validate();
	}

	@Test
	public void testIfNotEquals() {

		new H("100", "50") {
			@Override
			protected void v(final RejectSyntax t) {
				t.rejectIfNotEquals("100").validate("hola").throwErrors();
			}
		}.validate();
	}



	@Test
	public void testIfLengthIsOutOfRange() {

		new H("shortName", "Name") {
			@Override
			protected void v(final RejectSyntax t) {
				t.rejectIfLengthIsOutOfRange(2,5).validate("hola").throwErrors();
			}
		}.validate();
	}

	@Test(expected=RejectionException.class)
	public void testNullAccepted() {

		ValidationDSL.getInstance(paramsMap).forParamName("numericAge").reject().validate()
				.throwErrors();
	}

	// @Test(expected = ValidationException.class)
	// public void testNullRejected() {
	//
	// ValidationDSL.getInstance(paramsMap).forParamName("null").rejectIfNull().validate(
	// "unexpected null value for this field").throwErrors();
	// }
	//
	// @Test
	// public void testNullAccepted() {
	//
	// ValidationDSL.getInstance(paramsMap).forParamName("numericAge").rejectIfNull().validate()
	// .throwErrors();
	//
	// }
	//
	// @Test(expected = ValidationException.class)
	// public void test3() {
	//
	// ValidationDSL.getInstance(paramsMap).forParamName("empty").rejectIfEmpty()
	// .rejectIfLengthIsOutOfRange(3,
	// 10).validate("hey, its empty!").forParamName("literalAge")
	// .rejectIfNull().validate().throwErrors();
	//
	// }

}
