package org.homs.sql;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ArtifactInspector {

	private BufferedOutputStream os;
	private int jarCounter = 0;

	public ArtifactInspector() {
		os = null;
		try {
			os = new BufferedOutputStream(new FileOutputStream(new File("./artifacts2.sql")));
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(final String[] args) throws Exception {
		new ArtifactInspector().connectTo("http://repo1.maven.org/maven2/", "http://repo1.maven.org/maven2/jboss/");
	}

	private static String parseUrl(final String baseURL, final String httpsURL) {

		String artifact = httpsURL.substring(baseURL.length()).replace('/', '$');

		int c = 0;
		for (int i = 0; i < artifact.length(); i++) {
			if (artifact.charAt(i) == '$')
				c++;
		}
		c -= 3;
		for (int i = 0; i < artifact.length(); i++) {
			if (artifact.charAt(i) == '$' && c > 0) {
				artifact = artifact.subSequence(0, i) + "." + artifact.substring(i + 1);
				c--;
			}
		}

		return artifact;
	}

	private void connectTo(final String baseURL, final String httpsURL) throws MalformedURLException,
			IOException {
		final URL myurl = new URL(httpsURL);
		final URLConnection con = myurl.openConnection();
		final InputStream ins = con.getInputStream();
		final InputStreamReader isr = new InputStreamReader(ins);
		final BufferedReader in = new BufferedReader(isr);

		String inputLine;
		String received = "";
		while ((inputLine = in.readLine()) != null) {
			received += inputLine.replaceAll("<.*?>", "").replaceAll("\\s+", " ").replaceAll(
					"(.+?) (.+?) (.+?)$", "$1")
					+ "\n";
		}
		in.close();

		final String[] filesArray = received.split("\n");
		for (int i = 0; i < filesArray.length; i++) {
			final String file = filesArray[i];
			if (file.endsWith(".jar")) {
				if (!file.endsWith("-javadoc.jar") && !file.endsWith("-sources.jar")) {
					final String parsedUrl = parseUrl(baseURL, httpsURL + file);

					final String[] parts = parsedUrl.split("\\$");
					if (parts.length != 4) {
						continue;
					}

					joinPoint(httpsURL, parsedUrl, parts);
					this.jarCounter++;
				}
			} else if (file.endsWith("/")) {
				connectTo(baseURL, httpsURL + file);
			}
		}
		os.flush();
	}

	private void joinPoint(final String httpsURL, final String parsedUrl, final String[] parts)
			throws IOException {

		String q = "INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES ("
				+ this.jarCounter + ",'" + parts[0] + "','" + parts[1] + "','" + parts[2] + "','" + parts[3]
				+ "','" + httpsURL + "'," + (parts[0].split("\\.").length +parts[1].split("\\.").length+1)  +");";

		q = "st.executeUpdate(\"" + q + "\");\n";
		os.write(q.getBytes());
		System.out.print(q);
	}

}