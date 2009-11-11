package org.homs.demo.models;

import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.Assert;

import org.homs.gamba.bo.GambaBOLoader;
import org.homs.gamba.connectionpool.GambaPooling;
import org.junit.Test;

public class BusinessTest {


	@Test
	public void test1() throws SQLException {
		final IPersonBO personBO = (IPersonBO) GambaBOLoader
				.newInstance(PersonBO.class);

		personBO.deleteAll();
		Assert.assertEquals("[]", personBO.findAll().toString());

		final Person person1 = new Person(1L, "mhc", 27);
		final Person person2 = new Person(2L, "sob", 25);
		final Person person3 = new Person(3L, "dgc", 32);
		final Person person4 = new Person(4L, "jos", 26);

		personBO.insert(person1);
		personBO.insert(person2);
		personBO.insert(person3);
		personBO.insert(person4);
		Assert.assertEquals("[1-mhc-27, 2-sob-25, 3-dgc-32, 4-jos-26]", personBO.findAll().toString());

		Person sob = personBO.findById(2L);
		Assert.assertEquals("sob", sob.getName());

		personBO.delete(sob);

		sob = personBO.findById(2L);
		Assert.assertEquals(null, sob);



		final Person dgc = personBO.findById(3L);
		dgc.setAge(1000);
		personBO.update(dgc);
		Assert.assertEquals("3-dgc-1000", personBO.findById(dgc.getId()).toString());


		GambaPooling.getInstance().getConnection().createStatement().executeUpdate("SHUTDOWN");
		GambaPooling.getInstance().clearConnections();

	}

}
