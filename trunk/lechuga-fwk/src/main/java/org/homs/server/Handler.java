package org.homs.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

class Handler implements HttpHandler {

	private final String APPLICATION_CONTEXT_NAME;
	private final RequestDispatcher requestDispatcher;

	public Handler(final String application_context_name) {
		super();
		this.APPLICATION_CONTEXT_NAME = application_context_name;
		this.requestDispatcher = new RequestDispatcher();
	}

	public void handle(final HttpExchange xchg) throws IOException {
		try {
			System.out.println("request method: " + xchg.getRequestMethod());

			/*
			 * captura paràmetres en un sol String, siguin GET o POST
			 */
			String requestParametersExpression = null;
			if (xchg.getRequestMethod().equals("POST")) {
				if (xchg.getRequestBody() != null) {
					final BufferedReader br = new BufferedReader(new InputStreamReader(xchg.getRequestBody()));
					requestParametersExpression = URLDecoder.decode(br.readLine(), "UTF-8");
				}
			} else if (xchg.getRequestMethod().equals("GET")) {
				if (xchg.getRequestURI().getQuery() != null) {
					requestParametersExpression = URLDecoder.decode(xchg.getRequestURI().getQuery(), "UTF-8");
				}
			} else {
				throw new Exception("http method must be POST, not " + xchg.getRequestMethod());
			}
			System.out.println("** request params: " + requestParametersExpression);

			// ** request uri:
			/*
			 * captura la URI de petició (nom del servlet), p.ex. /echo/jou ==> /jou
			 */
			final String requestUri = getRequestRelativeUri(xchg.getRequestURI().getPath());
			System.out.println("** request uri: " + requestUri);

			/*
			 * resol i invoca al servlet
			 */
			final String responseContent = requestDispatcher.dispatch(requestUri, requestParametersExpression);

			/*
			 * envia la resposta en forma de vista
			 */
			xchg.sendResponseHeaders(200, responseContent.length());
			final OutputStream os = xchg.getResponseBody();
			os.write(responseContent.getBytes());
			os.close();

		} catch (final Exception exc) {
			exc.printStackTrace();
		}

	}

	/**
	 * /echo/jou ==> /jou
	 *
	 * @param uriPath
	 * @return
	 */
	private String getRequestRelativeUri(final String uriPath) {

		return uriPath.substring(APPLICATION_CONTEXT_NAME.length());
	}
}
