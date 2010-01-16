package org.gro.orm.service;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.sql.DataSource;

import org.gro.orm.datasource.TestingDataSourceFactory;
import org.gro.orm.service.ents.Tag;
import org.gro.orm.service.ents.User;
import org.gro.orm.service.ents.UserUrl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TransactionalServiceTest {

	private static final DataSource ds = TestingDataSourceFactory.getDataSource();

	@Before
	public void beforeMethod() throws Exception {

		final Connection c = ds.getConnection();
		c.setAutoCommit(false);

		final Statement st = c.createStatement();

		try {

    		st.execute(
  				  "CREATE TABLE USER ("
  				+ 	"ID_USER INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) PRIMARY KEY, "
  				+ 	"NAME VARCHAR(20), "
  				+ 	"PASS VARCHAR(10) "
  				+ ")");

    		st.execute(
    				  "CREATE TABLE URL ("
    				+ 	"ID_URL INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) PRIMARY KEY, "
    				+ 	"URL VARCHAR(200) "
    				+ ")");

    		st.execute(
    				  "CREATE TABLE TAG ("
    				+ 	"ID_TAG INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) PRIMARY KEY, "
    				+ 	"NAME VARCHAR(20) "
    				+ ")");

    		st.execute(
				  "CREATE TABLE USER_URL ("
				+ 	"ID_USER_URL INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) PRIMARY KEY, "
				+ 	"ID_USER INTEGER, "
				+ 	"ID_URL INTEGER, "
				+ 	"TITLE VARCHAR(100), "
				+ 	"DESCRIPTION VARCHAR(300), "
				+ 	"REGISTERED TIMESTAMP, "
  				+	"FOREIGN KEY (ID_USER) REFERENCES USER (ID_USER),"
  				+	"FOREIGN KEY (ID_URL) REFERENCES URL (ID_URL)"
				+ ")");

    		st.execute(
  				  "CREATE TABLE URL_TAG ("
  				+ 	"ID_URL_TAG INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) PRIMARY KEY, "
  				+ 	"ID_URL INTEGER, "
  				+ 	"ID_TAG INTEGER, "
   				+	"FOREIGN KEY (ID_TAG) REFERENCES TAG (ID_TAG),"
   				+	"FOREIGN KEY (ID_URL) REFERENCES URL (ID_URL)"
  				+ ")");


    		st.execute("INSERT INTO USER (ID_USER,NAME,PASS) VALUES (1, 'mhoms', 'cacadelavaca3')");
    		st.execute("INSERT INTO USER (ID_USER,NAME,PASS) VALUES (2, 'msantos', 'qwerty')");


    		st.execute("INSERT INTO URL (ID_URL,URL) VALUES (1, 'http://google.com')");
    		st.execute("INSERT INTO URL (ID_URL,URL) VALUES (2, 'http://gmail.com')");
    		st.execute("INSERT INTO URL (ID_URL,URL) VALUES (3, 'http://hotmail.com')");

    		st.execute("INSERT INTO TAG (ID_TAG,NAME) VALUES (1, 'www utility')");
    		st.execute("INSERT INTO TAG (ID_TAG,NAME) VALUES (2, 'mail service')");
    		st.execute("INSERT INTO TAG (ID_TAG,NAME) VALUES (3, 'finder')");

    		st.execute("INSERT INTO USER_URL (ID_USER,ID_URL,REGISTERED,TITLE,DESCRIPTION) " +
			   "VALUES (1, 1, '2001-12-01 17:00:00', 'GOOGLE', '-')");
    		st.execute("INSERT INTO USER_URL (ID_USER,ID_URL,REGISTERED,TITLE,DESCRIPTION) " +
			   "VALUES (1, 2, '2001-12-01 17:00:00', 'gmailillo', '-')");
    		st.execute("INSERT INTO USER_URL (ID_USER,ID_URL,REGISTERED,TITLE,DESCRIPTION) " +
			   "VALUES (2, 2, '2001-12-01 17:00:00', 'GOOGLE GMAIL', '-')");

    		st.execute("INSERT INTO URL_TAG (ID_URL,ID_TAG) VALUES (1, 1)");
    		st.execute("INSERT INTO URL_TAG (ID_URL,ID_TAG) VALUES (1, 3)");
    		st.execute("INSERT INTO URL_TAG (ID_URL,ID_TAG) VALUES (2, 2)");
    		st.execute("INSERT INTO URL_TAG (ID_URL,ID_TAG) VALUES (3, 2)");

    		c.commit();

		} catch(final Exception e) {
			c.rollback();
			throw e;
		}

		st.close();
		c.close();
	}

	@Test
	public void testPerson() throws Exception {


		final IBookmarkService service = (IBookmarkService) TransactionalServiceFactory.newInstance(BookmarkService.class, ds);
		final User user = service.loadUser("mhoms");
		System.out.println(user.toString());

		assertEquals(
				"User [idUser=1, name=mhoms, pass=cacadelavaca3, urls=[" +
					"Url [idUrl=1, tags=[" +
						"Tag [idTag=1, name=www utility, urls=null], " +
						"Tag [idTag=3, name=finder, urls=null]], url=http://google.com, users=null], " +
					"Url [idUrl=2, tags=[" +
						"Tag [idTag=2, name=mail service, urls=null]], url=http://gmail.com, users=null]]]",
				user.toString());

		final UserUrl[] userUrls = service.loadAllUserUrls();
		System.out.println(Arrays.toString(userUrls));
	}

	@Test
	public void testTransaction() throws Exception {

		final IBookmarkService service = (IBookmarkService) TransactionalServiceFactory.newInstance(BookmarkService.class, ds);

		final Tag[] tags1 = service.loadAllTags();
		System.out.println(Arrays.toString(tags1));
		assertEquals("[" +
				"Tag [idTag=1, name=www utility, urls=null], " +
				"Tag [idTag=2, name=mail service, urls=null], " +
				"Tag [idTag=3, name=finder, urls=null]]", Arrays.toString(tags1));

		Tag tag = new Tag(null, "tag-de-test");
		tag = service.createTag(tag);
		System.out.println(tag.toString());
		assertEquals("Tag [idTag=100, name=null, urls=null]", tag.toString());

		final Tag[] tags2 = service.loadAllTags();
		System.out.println(Arrays.toString(tags2));
		assertEquals("[" +
				"Tag [idTag=1, name=www utility, urls=null], " +
				"Tag [idTag=2, name=mail service, urls=null], " +
				"Tag [idTag=3, name=finder, urls=null], " +
				"Tag [idTag=100, name=tag-de-test, urls=null]]", Arrays.toString(tags2));

	}




	@After
	public void afterMethod() throws SQLException {
		final Connection c = ds.getConnection();
		c.setAutoCommit(false);

		final Statement st = c.createStatement();

		st.execute("DROP TABLE USER_URL");
		st.execute("DROP TABLE URL_TAG");
		st.execute("DROP TABLE USER");
		st.execute("DROP TABLE TAG");
		st.execute("DROP TABLE URL");

		c.commit();

		st.execute("SHUTDOWN");

		st.close();
		c.close();
	}



}
