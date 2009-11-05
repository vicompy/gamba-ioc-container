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

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

class Server {

	private final ServerConfig serverConfig;

	public Server(final ServerConfig serverConfig) {
		super();
		this.serverConfig = serverConfig;
	}

	public void runServer() throws IOException {
		final HttpServer server = HttpServer.create(new InetSocketAddress(serverConfig.SERVER_PORT), 0);
		server.createContext(serverConfig.APPLICATION_CONTEXT_NAME, new Handler(
				serverConfig.APPLICATION_CONTEXT_NAME));
		server.start();
		System.out.println("server is running at port " + serverConfig.SERVER_PORT);
	}
}

