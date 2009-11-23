package org.gro.validation;

import java.util.Map;

import org.gro.validation.exception.RejectionException;

public interface SetTargetSyntax {
	RejectSyntax forParamName(String target);

	// RejectSyntax forParamName(final String targetParamName, final String
	// paramAlias);

	void throwErrors() throws RejectionException;

	Map<String, String> getRejectionsMap();
}