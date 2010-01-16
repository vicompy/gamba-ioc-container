package org.gro.orm.dao;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.sql.DataSource;

import org.gro.orm.datasource.TestingDataSourceFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GroDaoFactoryTest {

	private static final DataSource ds = TestingDataSourceFactory.getDataSource();

	@Before
	public void beforeMethod() throws Exception {

		final Connection c = ds.getConnection();
		c.setAutoCommit(false);

		final Statement st = c.createStatement();

		try {


    		st.execute(
				  "CREATE TABLE COLOR ("
				+ 	"ID_COLOR INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) PRIMARY KEY, "
				+ 	"NAME VARCHAR(100) "
				+ ")");
    		st.execute(
  				  "CREATE TABLE DOG ("
  				+ 	"ID_DOG INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) PRIMARY KEY, "
  				+ 	"NAME VARCHAR(100), "
  				+ 	"AGE INTEGER,"
  				+ 	"ID_COLOR INTEGER,"
  				+	"FOREIGN KEY (ID_COLOR) REFERENCES COLOR (ID_COLOR)"
  				+ ")");


    		st.execute("INSERT INTO COLOR (ID_COLOR,NAME) VALUES (1, 'gris')");
    		st.execute("INSERT INTO COLOR (ID_COLOR,NAME) VALUES (2, 'marro')");
    		st.execute("INSERT INTO COLOR (ID_COLOR,NAME) VALUES (3, 'negre')");
    		st.execute("INSERT INTO DOG (ID_DOG,NAME,AGE,ID_COLOR) VALUES (1, 'chucho', 2, 2)");
    		st.execute("INSERT INTO DOG (ID_DOG,NAME,AGE,ID_COLOR) VALUES (2, 'negra', 8, 3)");



    		c.commit();

		} catch(final Exception e) {
			c.rollback();
			throw e;
		}

		st.close();
		c.close();
	}

	/**
	 * TESTA LA CÀRREGA D'UNA SIMPLE CLASSE, NO RELACIONADA
	 *
	 * @throws Exception
	 */
	@Test
	public void testPerson() throws Exception {

		final Connection c = ds.getConnection();
		c.setAutoCommit(false);

		try {

			final DogDao dogdao = (DogDao) GroDaoFactory.newInstance(DogDao.class, c);


			final Dog[] dogs1 = dogdao.loadAll();
			System.out.println(Arrays.asList(dogs1));

			int rowsAffected = dogdao.create(new Dog(3, "blanca", 7, null));
			System.out.println("created " + rowsAffected);

			final Dog blanca = dogdao.loadById(3);
			System.out.println(blanca);

			final Dog[] dogs2 = dogdao.loadAll();
			System.out.println(Arrays.asList(dogs2));

			blanca.setName("blanca rip");
			blanca.setAge(100);
			rowsAffected = dogdao.update(blanca);
			System.out.println("updated " + rowsAffected);

			final Dog[] dogs3 = dogdao.loadAll();
			System.out.println(Arrays.asList(dogs3));

			rowsAffected = dogdao.delete(blanca);
			System.out.println("deleted " + rowsAffected);

			final Dog[] dogs4 = dogdao.loadAll();
			System.out.println(Arrays.asList(dogs4));



			assertEquals("[" +
					"Dog [age=2, colors=null, idDog=1, name=chucho], " +
					"Dog [age=8, colors=null, idDog=2, name=negra]]", Arrays.asList(dogs1).toString());
			assertEquals("[" +
					"Dog [age=2, colors=null, idDog=1, name=chucho], " +
					"Dog [age=8, colors=null, idDog=2, name=negra], " +
					"Dog [age=7, colors=null, idDog=3, name=blanca]]", Arrays.asList(dogs2).toString());
			assertEquals("[" +
					"Dog [age=2, colors=null, idDog=1, name=chucho], " +
					"Dog [age=8, colors=null, idDog=2, name=negra], " +
					"Dog [age=100, colors=null, idDog=3, name=blanca rip]]", Arrays.asList(dogs3).toString());
			assertEquals("[" +
					"Dog [age=2, colors=null, idDog=1, name=chucho], " +
					"Dog [age=8, colors=null, idDog=2, name=negra]]", Arrays.asList(dogs4).toString());


			final Dog chucho = dogdao.loadById(1);
			chucho.setColors(dogdao.joinWithColor(chucho));
			System.out.println(chucho.toString2());


			c.commit();
		} catch (final Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
	}




	@After
	public void afterMethod() throws SQLException {
		final Connection c = ds.getConnection();
		c.setAutoCommit(false);

		final Statement st = c.createStatement();

		st.execute("DROP TABLE DOG");
		st.execute("DROP TABLE COLOR");
		c.commit();

		st.execute("SHUTDOWN");

		st.close();
		c.close();
	}



}
