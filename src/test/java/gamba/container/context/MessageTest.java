package gamba.container.context;

import gamba.container.test.entities.MessageProvider;
import junit.framework.Assert;

import org.homs.gamba.container.context.GambaContainer;
import org.homs.gamba.container.context.GambaContext;
import org.junit.BeforeClass;
import org.junit.Test;

public class MessageTest {

	private static GambaContext gc = null;

	@BeforeClass
	public static void init() {
		gc = GambaContainer.getContext("message-test-context.properties");
	}

	@Test
	public void test1() {

		final MessageProvider mp = (MessageProvider) gc.getBean("messageProvider1");

		Assert.assertEquals("hello world", mp.getMessage().getMessage());
	}

	@Test
	public void test2() {

		final MessageProvider mp = (MessageProvider) gc.getBean("messageProvider2");

		Assert.assertEquals("hello world", mp.getMessage().getMessage());
	}

	@Test
	public void test3() {

		final MessageProvider mp = (MessageProvider) gc.getBean("messageProvider3");

		Assert.assertEquals("hello world", mp.getMessage().getMessage());
	}

	@Test
	public void test4() {

		final MessageProvider mp = (MessageProvider) gc.getBean("messageProvider4");

		Assert.assertEquals("hello world", mp.getMessage().getMessage());
	}

}
