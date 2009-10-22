package org.homs.gamba.mock.main;

import junit.framework.Assert;

import org.homs.gamba.mock.Mock;
import org.homs.gamba.mock.Mocker;
import org.homs.gamba.mock.ents.IAdder;
import org.homs.gamba.mock.exception.GambaMockException;
import org.junit.Test;

public class MockTest {

	@Test
	public void test1() {

		final Mock<IAdder> m = Mocker.createMock(IAdder.class);
		m.returning(3).add(1, 2);
		m.returning(5).add(2, 3);
		final IAdder ia = m.play();

		Assert.assertEquals(Integer.valueOf(3), ia.add(1, 2));
		Assert.assertEquals(Integer.valueOf(5), ia.add(2, 3));
	}

	@Test(expected = GambaMockException.class)
	public void test2() {

		final Mock<IAdder> m = Mocker.createMock(IAdder.class);
		m.returning(3).add(1, 2);
		m.returning(5).add(2, 3);
		final IAdder ia = m.play();

		ia.add(3, 4);
	}

	@Test(expected = GambaMockException.class)
	public void test3() {

		final Mock<IAdder> m = Mocker.createMock(IAdder.class);
		m.returning(3);
		final IAdder ia = m.play();

		Assert.assertEquals(Integer.valueOf(5), ia.add(2, 3));
	}


}
