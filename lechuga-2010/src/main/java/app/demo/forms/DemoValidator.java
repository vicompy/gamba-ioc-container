package app.demo.forms;

import java.util.Map;

import org.lechuga.mvc.validation.Validator;

public class DemoValidator extends Validator {

	@SuppressWarnings("unchecked")
	@Override
	protected Map<String, String> validate(final Map<String, String[]> params) {
		return predicatesOr(
			predicate(
				notEmpty("name", "name is required"),
				isLengthGreaterThan("name", 2, "name field must have 3 characters at least"),
				notEmpty("age", "age is required"),
				isInteger("age", "age must be a numeric value")
			)
		);
	}

}
