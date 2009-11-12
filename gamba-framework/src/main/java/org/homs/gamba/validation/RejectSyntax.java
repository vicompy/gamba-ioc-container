package org.homs.gamba.validation;

public interface RejectSyntax {

	SetTargetSyntax validate();
	SetTargetSyntax validate(String errorMessage);

	// absolute reject
	RejectSyntax reject();
	RejectSyntax reject(RejectStatement rejectStatement);

	// field emptyness
	RejectSyntax rejectIfNull();
	RejectSyntax rejectIfNotNull();
	RejectSyntax rejectIfEmpty();

	// number format
	RejectSyntax rejectIfNotInteger();
	RejectSyntax rejectIfNotDouble();

	// number value
	RejectSyntax rejectIfGreaterThan(Double value);
	RejectSyntax rejectIfLessThan(Double value);
	RejectSyntax rejectIfEqualsThan(Double value);
	RejectSyntax rejectIfNotEqualsThan(Double value);

	// string value
	RejectSyntax rejectIfEquals(String str);
	RejectSyntax rejectIfNotEquals(String str);

	// string lengths and ranges
//	RejectSyntax rejectIfLengthIsGreaterThan(int length);
//	RejectSyntax rejectIfLengthIsLessThan(int length);
//	RejectSyntax rejectIfLengthIsGreaterOrEqualsThan(int length);
//	RejectSyntax rejectIfLengthIsLessOrEqualsThan(int length);
	RejectSyntax rejectIfLengthIsOutOfRange(Integer min, Integer max);

// TODO rejectIfContainsSpaces
}