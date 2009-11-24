package org.gro.demo.validators;

import java.util.Map;

import org.gro.validation.IGroValidator;
import org.gro.validation.SetTargetSyntax;

public class PersonValidator implements IGroValidator {

	@Override
	public Map<String, String> validate(final SetTargetSyntax target) {
		return target
    		.forParamName("name")
    			.rejectIfLengthIsOutOfRange(3, 10)
    		.validate("[3..10] characters")
    		.forParamName("age")
        		.rejectIfNotInteger()
        		.rejectIfLessThan(0.0)
        		.rejectIfGreaterThan(130.0)
    		.validate("age must be [0..130]")
		.getRejectionsMap();
	}

}
