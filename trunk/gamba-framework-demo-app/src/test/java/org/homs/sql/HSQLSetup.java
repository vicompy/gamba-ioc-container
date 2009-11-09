package org.homs.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HSQLSetup {

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

		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (0,'jboss','javassist','2.5.1','javassist-2.5.1.jar','http://repo1.maven.org/maven2/jboss/javassist/2.5.1/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (1,'jboss','javassist','2.6','javassist-2.6.jar','http://repo1.maven.org/maven2/jboss/javassist/2.6/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (2,'jboss','javassist','3.0','javassist-3.0.jar','http://repo1.maven.org/maven2/jboss/javassist/3.0/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (3,'jboss','javassist','3.0-rc-1','javassist-3.0-rc-1.jar','http://repo1.maven.org/maven2/jboss/javassist/3.0-rc-1/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (4,'jboss','javassist','3.1','javassist-3.1.jar','http://repo1.maven.org/maven2/jboss/javassist/3.1/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (5,'jboss','javassist','3.3.ga','javassist-3.3.ga.jar','http://repo1.maven.org/maven2/jboss/javassist/3.3.ga/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (6,'jboss','javassist','3.4.ga','javassist-3.4.ga.jar','http://repo1.maven.org/maven2/jboss/javassist/3.4.ga/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (7,'jboss','javassist','3.6.ga','javassist-3.6.ga.jar','http://repo1.maven.org/maven2/jboss/javassist/3.6.ga/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (8,'jboss','javassist','3.7.ga','javassist-3.7.ga.jar','http://repo1.maven.org/maven2/jboss/javassist/3.7.ga/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (9,'jboss','jboss','3.2.1','jboss-3.2.1.jar','http://repo1.maven.org/maven2/jboss/jboss/3.2.1/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (10,'jboss','jboss','3.2.3','jboss-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jboss/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (11,'jboss','jboss-boot','3.2.1','jboss-boot-3.2.1.jar','http://repo1.maven.org/maven2/jboss/jboss-boot/3.2.1/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (12,'jboss','jboss-cache','1.2.2','jboss-cache-1.2.2.jar','http://repo1.maven.org/maven2/jboss/jboss-cache/1.2.2/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (13,'jboss','jboss-cache','1.4.1.GA','jboss-cache-1.4.1.GA.jar','http://repo1.maven.org/maven2/jboss/jboss-cache/1.4.1.GA/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (14,'jboss','jboss-client','3.2.1','jboss-client-3.2.1.jar','http://repo1.maven.org/maven2/jboss/jboss-client/3.2.1/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (15,'jboss','jboss-client','3.2.3','jboss-client-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jboss-client/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (16,'jboss','jboss-client','4.0.2','jboss-client-4.0.2.jar','http://repo1.maven.org/maven2/jboss/jboss-client/4.0.2/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (17,'jboss','jboss-common','1.0','jboss-common-1.0.jar','http://repo1.maven.org/maven2/jboss/jboss-common/1.0/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (18,'jboss','jboss-common','3.2.1','jboss-common-3.2.1.jar','http://repo1.maven.org/maven2/jboss/jboss-common/3.2.1/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (19,'jboss','jboss-common','3.2.3','jboss-common-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jboss-common/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (20,'jboss','jboss-common','4.0.0','jboss-common-4.0.0.jar','http://repo1.maven.org/maven2/jboss/jboss-common/4.0.0/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (21,'jboss','jboss-common','4.0.2','jboss-common-4.0.2.jar','http://repo1.maven.org/maven2/jboss/jboss-common/4.0.2/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (22,'jboss','jboss-common-client','3.2.3','jboss-common-client-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jboss-common-client/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (23,'jboss','jboss-common-core','2.0.4.GA','jboss-common-core-2.0.4.GA-jdk14.jar','http://repo1.maven.org/maven2/jboss/jboss-common-core/2.0.4.GA/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (24,'jboss','jboss-common-core','2.0.4.GA','jboss-common-core-2.0.4.GA.jar','http://repo1.maven.org/maven2/jboss/jboss-common-core/2.0.4.GA/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (25,'jboss','jboss-common-jdbc-wrapper','3.2.3','jboss-common-jdbc-wrapper-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jboss-common-jdbc-wrapper/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (26,'jboss','jboss-iiop','3.2.3','jboss-iiop-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jboss-iiop/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (27,'jboss','jboss-iiop-client','3.2.3','jboss-iiop-client-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jboss-iiop-client/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (28,'jboss','jboss-j2ee','3.2.1','jboss-j2ee-3.2.1.jar','http://repo1.maven.org/maven2/jboss/jboss-j2ee/3.2.1/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (29,'jboss','jboss-j2ee','3.2.3','jboss-j2ee-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jboss-j2ee/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (30,'jboss','jboss-j2ee','4.0.0','jboss-j2ee-4.0.0.jar','http://repo1.maven.org/maven2/jboss/jboss-j2ee/4.0.0/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (31,'jboss','jboss-j2ee','4.0.0DR4','jboss-j2ee-4.0.0DR4.jar','http://repo1.maven.org/maven2/jboss/jboss-j2ee/4.0.0DR4/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (32,'jboss','jboss-j2ee','4.0.2','jboss-j2ee-4.0.2.jar','http://repo1.maven.org/maven2/jboss/jboss-j2ee/4.0.2/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (33,'jboss','jboss-j2se','200504122039','jboss-j2se-200504122039.jar','http://repo1.maven.org/maven2/jboss/jboss-j2se/200504122039/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (34,'jboss','jboss-jaas','3.2.3','jboss-jaas-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jboss-jaas/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (35,'jboss','jboss-jmx','3.0.4','jboss-jmx-3.0.4.jar','http://repo1.maven.org/maven2/jboss/jboss-jmx/3.0.4/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (36,'jboss','jboss-jmx','3.2.1','jboss-jmx-3.2.1.jar','http://repo1.maven.org/maven2/jboss/jboss-jmx/3.2.1/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (37,'jboss','jboss-jmx','4.0.2','jboss-jmx-4.0.2.jar','http://repo1.maven.org/maven2/jboss/jboss-jmx/4.0.2/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (38,'jboss','jboss-jmx-rmi-connector-client','3.0.4','jboss-jmx-rmi-connector-client-3.0.4.jar','http://repo1.maven.org/maven2/jboss/jboss-jmx-rmi-connector-client/3.0.4/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (39,'jboss','jboss-jsr77','3.2.3','jboss-jsr77-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jboss-jsr77/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (40,'jboss','jboss-jsr77-client','3.2.3','jboss-jsr77-client-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jboss-jsr77-client/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (41,'jboss','jboss-management','3.2.3','jboss-management-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jboss-management/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (42,'jboss','jboss-minimal','4.0.2','jboss-minimal-4.0.2.jar','http://repo1.maven.org/maven2/jboss/jboss-minimal/4.0.2/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (43,'jboss','jboss-net-client','3.2.3','jboss-net-client-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jboss-net-client/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (44,'jboss','jboss-system','1.0','jboss-system-1.0.jar','http://repo1.maven.org/maven2/jboss/jboss-system/1.0/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (45,'jboss','jboss-system','3.2.1','jboss-system-3.2.1.jar','http://repo1.maven.org/maven2/jboss/jboss-system/3.2.1/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (46,'jboss','jboss-system','3.2.3','jboss-system-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jboss-system/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (47,'jboss','jboss-system','4.0.2','jboss-system-4.0.2.jar','http://repo1.maven.org/maven2/jboss/jboss-system/4.0.2/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (48,'jboss','jboss-system-client','3.2.3','jboss-system-client-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jboss-system-client/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (49,'jboss','jboss-transaction','3.2.3','jboss-transaction-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jboss-transaction/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (50,'jboss','jboss-transaction-client','3.2.3','jboss-transaction-client-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jboss-transaction-client/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (51,'jboss','jbossall-client','3.2.1','jbossall-client-3.2.1.jar','http://repo1.maven.org/maven2/jboss/jbossall-client/3.2.1/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (52,'jboss','jbossall-client','3.2.3','jbossall-client-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jbossall-client/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (53,'jboss','jbosscx-client','3.2.3','jbosscx-client-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jbosscx-client/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (54,'jboss','jbossha','3.2.3','jbossha-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jbossha/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (55,'jboss','jbossha','4.0.2','jbossha-4.0.2.jar','http://repo1.maven.org/maven2/jboss/jbossha/4.0.2/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (56,'jboss','jbossha-client','3.2.3','jbossha-client-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jbossha-client/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (57,'jboss','jbossjmx-ant','3.2.3','jbossjmx-ant-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jbossjmx-ant/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (58,'jboss','jbossmq','3.2.3','jbossmq-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jbossmq/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (59,'jboss','jbossmq-client','3.2.3','jbossmq-client-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jbossmq-client/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (60,'jboss','jbossmq-client','4.0.2','jbossmq-client-4.0.2.jar','http://repo1.maven.org/maven2/jboss/jbossmq-client/4.0.2/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (61,'jboss','jbosssx','3.2.3','jbosssx-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jbosssx/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (62,'jboss','jbosssx-client','3.2.3','jbosssx-client-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jbosssx-client/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (63,'jboss','jmx-adaptor-plugin','3.2.1','jmx-adaptor-plugin-3.2.1.jar','http://repo1.maven.org/maven2/jboss/jmx-adaptor-plugin/3.2.1/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (64,'jboss','jnet','3.2.1','jnet-3.2.1.jar','http://repo1.maven.org/maven2/jboss/jnet/3.2.1/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (65,'jboss','jnp-client','3.2.1','jnp-client-3.2.1.jar','http://repo1.maven.org/maven2/jboss/jnp-client/3.2.1/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (66,'jboss','jnp-client','4.0.2','jnp-client-4.0.2.jar','http://repo1.maven.org/maven2/jboss/jnp-client/4.0.2/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (67,'jboss','jnpserver','3.2.3','jnpserver-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jnpserver/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (68,'jboss','jpl-pattern','3.2.3','jpl-pattern-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jpl-pattern/3.2.3/',3);");
		st.executeUpdate("INSERT INTO ARTIFACTS (ID,GROUPID,ARTIFACTID,VERSION,JARNAME,URL,DEPT) VALUES (69,'jboss','jpl-util','3.2.3','jpl-util-3.2.3.jar','http://repo1.maven.org/maven2/jboss/jpl-util/3.2.3/',3);");


//		st.executeUpdate("UPDATE empleados SET departamento='Contabilidad' WHERE codEmpleado=100002");

		rst1 = st.executeQuery("SELECT * FROM ARTIFACTS WHERE JARNAME LIKE '%1%'");
		while (rst1.next()) {
			System.out.println(rst1.getString("JARNAME"));
		}

		rst1.close();
		st = conn.createStatement();
		st.executeUpdate("SHUTDOWN");
		st.close();
		conn.close();
	}

}