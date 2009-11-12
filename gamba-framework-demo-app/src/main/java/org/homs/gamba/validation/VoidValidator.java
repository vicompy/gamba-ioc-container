package org.homs.gamba.validation;

import java.util.HashMap;
import java.util.Map;

public class VoidValidator implements IGambaValidator {

	public Map<String, String> validate(final SetTargetSyntax setTargetSyntax) {
		return new HashMap<String, String>();
	}

}
