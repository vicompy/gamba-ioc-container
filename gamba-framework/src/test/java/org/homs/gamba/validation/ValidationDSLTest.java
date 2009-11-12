package org.homs.gamba.validation;

import java.util.HashMap;
import java.util.Map;

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
	}



	@Test(expected=ValidationException.class)
	public void testNullRejected() {

		ValidationDSL.getInstance(paramsMap)
		.forParamName("null").rejectIfNull().validate("unexpected null value for this field")
		.throwErrors();
	}

	@Test
	public void testNullAccepted() {

		ValidationDSL.getInstance(paramsMap)
		.forParamName("numericAge").rejectIfNull().validate()
		.throwErrors();

	}



	@Test(expected=ValidationException.class)
	public void test3() {

		ValidationDSL.getInstance(paramsMap)
        .forParamName("empty")
    	.rejectIfEmpty()
    	.rejectIfLengthIsOutOfRange(3, 10)
		.validate("hey, its empty!")
		.throwErrors();

	}

}
