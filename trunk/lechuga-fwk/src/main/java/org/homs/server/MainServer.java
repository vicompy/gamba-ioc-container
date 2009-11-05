package org.homs.server;

import java.io.IOException;

public class MainServer {

	public static void main(final String[] args) throws IOException {
		new Server(new ServerConfig()).runServer();
	}

}
