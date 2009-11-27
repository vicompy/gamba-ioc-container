package org.gro.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IActionDispatcher {

	String dispatcher(final HttpServletRequest request, final HttpServletResponse response,
			final String requestServletPath);

}