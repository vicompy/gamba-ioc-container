package org.gamba.mocks.test;

import static org.gamba.mocks.fluent.Mocker.*;
import junit.framework.Assert;

import org.junit.Test;

public class VoidTest {

	@Test
	public void test1() {

		final G g = createMock(G.class);

		thenReturn(null).when(g).getVoid();
		thenReturn(null).when(g).getObject();
		replay(g);

		g.getVoid();
		Assert.assertEquals(null, g.getObject());

		verify(g);
	}

	@Test
	public void test2() {

		final G g = createMock(G.class);

		thenReturnVoid().when(g).getVoid();
		thenReturnNull().when(g).getObject();
		replay(g);

		g.getVoid();
		Assert.assertEquals(null, g.getObject());

		verify(g);
	}

}

interface G {
	Object getObject();

	void getVoid();
}