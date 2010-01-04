package org.lechuga.mvc.dispatcher;

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

	public String getParam(final String parameterName) {
		return request.getParameter(parameterName);
	}

	public void setAttr(final String parameterName, final Object value) {
		request.setAttribute(parameterName, value);
	}

}
