package org.gro.validation.utils;

import java.util.HashMap;
import java.util.Map;

import org.gro.validation.IGroValidator;
import org.gro.validation.SetTargetSyntax;

public class VoidValidator implements IGroValidator {

	public Map<String, String> validate(final SetTargetSyntax setTargetSyntax) {
		return new HashMap<String, String>();
	}

}
