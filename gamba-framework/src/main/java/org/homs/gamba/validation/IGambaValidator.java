package org.homs.gamba.validation;

import java.util.Map;

public interface IGambaValidator {


	Map<String,String> validate(SetTargetSyntax setTargetSyntax);

//	public void v(final Map<String,String[]> atributesMap) {
//
//		validate(ValidationDSL.getInstance(atributesMap));
//
////		final Map<String,String> errorMap =
////			ValidationDSL.getInstance(req.getRequest().getParameterMap())
////				.forParamName("name")
////					.rejectIfEmpty()
////					.rejectIfLengthIsOutOfRange(3, 10)
////				.validate("name field must have 3-10 characters")
////				.forParamName("age")
////					.rejectIfNotInteger()
////					.rejectIfLessThan(0.0)
////					.rejectIfGreaterThan(100.0)
////				.validate("age field must be an integer ranged by 0..100")
////			.getRejectionsMap();
////
////			if (!errorMap.isEmpty()) {
////
////			}
//
//	}
}
