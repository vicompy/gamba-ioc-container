package org.homs.gamba.dao;

import org.homs.binding.B;
import org.junit.Test;

public class InsertQueryTest {

	@Test
	public void test1() {

		final B b = new B();
		b.setName("msl");
		b.setAge(25);
		b.setMembers(new Float[]{1.0f, 2.0f});

		try {
		new InsertQuery().insert(b);
		} catch(final Exception e) {
			e.printStackTrace();
		}
	}


}
