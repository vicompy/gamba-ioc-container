package gamba.container.answer42;

import junit.framework.Assert;

import org.homs.gamba.container.context.GambaContainer;
import org.homs.gamba.container.context.GambaContext;
import org.junit.BeforeClass;
import org.junit.Test;

public class AnswerTest {

	private static GambaContext gc = null;

	@BeforeClass
	public static void init() {
		gc = GambaContainer.getContext("answer-test-context.properties");
	}

	@Test
	public void test1() {

		final AnswerBean ab = (AnswerBean) gc.getBean("answerBean1");

		Assert.assertEquals(7500000, (int) ab.getYears());
		Assert.assertEquals("42", ab.getUltimateAnswer());
	}

	@Test
	public void test2() {

		final AnswerBean ab = (AnswerBean) gc.getBean("answerBean2");

		Assert.assertEquals(7500000, (int) ab.getYears());
		Assert.assertEquals("42", ab.getUltimateAnswer());
	}

}
