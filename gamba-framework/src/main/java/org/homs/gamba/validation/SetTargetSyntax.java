package org.homs.gamba.validation;

import java.util.Map;

public interface SetTargetSyntax {
	RejectSyntax forParamName(String target);
	RejectSyntax forParamName(final String targetParamName, final String paramAlias);

	void throwErrors() throws ValidationException;

	Map<String, String> getRejectionsMap();
}