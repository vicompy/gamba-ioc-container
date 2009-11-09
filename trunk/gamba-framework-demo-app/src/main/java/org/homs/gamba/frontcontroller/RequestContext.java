package org.homs.gamba.frontcontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestContext {

	private final HttpServletRequest request;
	private final HttpServletResponse response;

	public RequestContext(final HttpServletRequest request, final HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

}
