/*
http://java.sun.com/javase/6/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/package-summary.html
http://blogs.sun.com/michaelmcm/entry/http_server_api_in_java
http://www.java2s.com/Code/Java/Network-Protocol/MinimalHTTPServerbyusingcomsunnethttpserverHttpServer.htm
http://www.jguru.com/forums/view.jsp?EID=1333689
http://elliotth.blogspot.com/2009/03/using-comsunnethttpserver.html

http://java.sun.com/j2se/1.4.2/docs/api/java/net/URLDecoder.html
http://www.exampledepot.com/egs/java.net/FormEncode.html
http://www.coderanch.com/t/63524/Other-Open-Source-Projects/Best-practice-get-table-form
http://java.sun.com/j2se/1.4.2/docs/api/java/beans/package-summary.html
 */
package org.homs.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {

	// private static final String APPLICATION_CONTEXT_NAME = "/echo";
	// private static final int SERVER_PORT = 80;

	public static void main(final String[] args) throws IOException {
		final HttpServer server = HttpServer.create(new InetSocketAddress(ServerConfig.SERVER_PORT), 0);
		server.createContext(ServerConfig.APPLICATION_CONTEXT_NAME, new Handler(
				ServerConfig.APPLICATION_CONTEXT_NAME));
		server.start();
		System.out.println("server is running at port " + ServerConfig.SERVER_PORT);
	}
}

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
