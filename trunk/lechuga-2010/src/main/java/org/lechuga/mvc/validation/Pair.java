package org.lechuga.mvc.validation;

public class Pair {

	public String paramName;
	public String errorMessage;

	public Pair(final String paramName, final String errorMessage) {
		super();
		this.paramName = paramName;
		this.errorMessage = errorMessage;
	}

}