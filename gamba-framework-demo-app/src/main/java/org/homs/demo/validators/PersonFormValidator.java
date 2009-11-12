package org.homs.demo.validators;

import java.util.Map;

import org.homs.gamba.validation.IGambaValidator;
import org.homs.gamba.validation.SetTargetSyntax;

public class PersonFormValidator implements IGambaValidator {

	public Map<String, String> validate(final SetTargetSyntax setTargetSyntax) {
		return setTargetSyntax
            .forParamName("name")
            	.rejectIfEmpty()
            	.rejectIfLengthIsOutOfRange(3, 10)
            .validate("name field must have 3-10 characters")
            .forParamName("age")
            	.rejectIfNotInteger()
            	.rejectIfLessThan(0.0)
            	.rejectIfGreaterThan(100.0)
            .validate("age field must be an integer ranged by 0..100")
        .getRejectionsMap();
	}

}
