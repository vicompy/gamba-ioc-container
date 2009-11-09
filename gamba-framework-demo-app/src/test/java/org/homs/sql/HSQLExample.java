package org.homs.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HSQLExample {

	public static void main(final String[] args) throws SQLException {
		Connection conn = null;
		Statement st = null;
		String sql = null;
		ResultSet rst1 = null;

		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (final Exception ex) {
			ex.printStackTrace();
		}

		conn = DriverManager.getConnection("jdbc:hsqldb:file:/home/mhoms/usuarios", null, null);



		try {
			st = conn.createStatement();
			sql = "CREATE TABLE ARTIFACTS (" +
				"ID INTEGER PRIMARY KEY, " +
				"GROUPID VARCHAR(100), " +
				"ARTIFACTID VARCHAR(100), " +
				"VERSION VARCHAR(100), " +
				"JARNAME VARCHAR(100), " +
				"URL VARCHAR(100), " +
				"DEPT INTEGER" +
			")";
			st.executeUpdate(sql);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}

		conn.setAutoCommit(true);
		st.executeUpdate("DELETE FROM ARTIFACTS");

        st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (0,'HTTPClient','HTTPClient','0.3-3','HTTPClient-0.3-3.jar','http://repo1.maven.org/maven2/HTTPClient/HTTPClient/0.3-3/',3);");
        st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (1,'abbot','abbot','0.12.3','abbot-0.12.3.jar','http://repo1.maven.org/maven2/abbot/abbot/0.12.3/',3);");
        st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (2,'abbot','abbot','0.13.0','abbot-0.13.0.jar','http://repo1.maven.org/maven2/abbot/abbot/0.13.0/',3);");
        st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (3,'acegisecurity','acegi-security','0.5','acegi-security-0.5.jar','http://repo1.maven.org/maven2/acegisecurity/acegi-security/0.5/',3);");
        st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (4,'acegisecurity','acegi-security','0.51','acegi-security-0.51.jar','http://repo1.maven.org/maven2/acegisecurity/acegi-security/0.51/',3);");
        st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (5,'acegisecurity','acegi-security','0.6.1','acegi-security-0.6.1.jar','http://repo1.maven.org/maven2/acegisecurity/acegi-security/0.6.1/',3);");
        st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (6,'acegisecurity','acegi-security','0.7.0','acegi-security-0.7.0.jar','http://repo1.maven.org/maven2/acegisecurity/acegi-security/0.7.0/',3);");
        st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (7,'acegisecurity','acegi-security','0.7.1','acegi-security-0.7.1.jar','http://repo1.maven.org/maven2/acegisecurity/acegi-security/0.7.1/',3);");
        st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (8,'acegisecurity','acegi-security','0.8.0','acegi-security-0.8.0.jar','http://repo1.maven.org/maven2/acegisecurity/acegi-security/0.8.0/',3);");
        st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (9,'acegisecurity','acegi-security','0.8.1','acegi-security-0.8.1.jar','http://repo1.maven.org/maven2/acegisecurity/acegi-security/0.8.1/',3);");
        st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (10,'acegisecurity','acegi-security','0.8.1.1','acegi-security-0.8.1.1.jar','http://repo1.maven.org/maven2/acegisecurity/acegi-security/0.8.1.1/',3);");
        st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (11,'acegisecurity','acegi-security','0.8.2','acegi-security-0.8.2.jar','http://repo1.maven.org/maven2/acegisecurity/acegi-security/0.8.2/',3);");
        st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (12,'acegisecurity','acegi-security','0.8.3','acegi-security-0.8.3.jar','http://repo1.maven.org/maven2/acegisecurity/acegi-security/0.8.3/',3);");
        st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (13,'acegisecurity','acegi-security','0.9.0','acegi-security-0.9.0.jar','http://repo1.maven.org/maven2/acegisecurity/acegi-security/0.9.0/',3);");
        st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (14,'acegisecurity','acegi-security-cas','0.7.0','acegi-security-cas-0.7.0.jar','http://repo1.maven.org/maven2/acegisecurity/acegi-security-cas/0.7.0/',3);");
        st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (15,'acegisecurity','acegi-security-cas','0.7.1','acegi-security-cas-0.7.1.jar','http://repo1.maven.org/maven2/acegisecurity/acegi-security-cas/0.7.1/',3);");
        st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (16,'acegisecurity','acegi-security-cas','0.8.0','acegi-security-cas-0.8.0.jar','http://repo1.maven.org/maven2/acegisecurity/acegi-security-cas/0.8.0/',3);");

//		st.executeUpdate("UPDATE empleados SET departamento='Contabilidad' WHERE codEmpleado=100002");

		// Mostramos por pantalla todos los empleados de la tabla
		rst1 = st.executeQuery("SELECT * FROM ARTIFACTS WHERE JARNAME LIKE '%0.7%'");
		while (rst1.next()) {
			System.out.println(rst1.getString("JARNAME"));
		}

		// Liberamos recursos
		rst1.close();

		// Enviamos el comando para que salve todos los datos temporales de
		// forma permanente
		st = conn.createStatement();
		st.executeUpdate("SHUTDOWN");

		// Liberamos recursos y cerramos la conexión
		st.close();
		conn.close();
	}

}