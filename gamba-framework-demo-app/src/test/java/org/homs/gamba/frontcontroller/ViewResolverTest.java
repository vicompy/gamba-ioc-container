package org.homs.gamba.frontcontroller;

import static org.homs.gamba.stub.bsyntax.Stubber.createStub;
import static org.homs.gamba.stub.bsyntax.Stubber.play;
import static org.homs.gamba.stub.bsyntax.Stubber.thenReturn;

import javax.servlet.ServletConfig;

import junit.framework.Assert;

import org.junit.Test;

public class ViewResolverTest {

	// <init-param>
	// <param-name>view-resource-prefix</param-name>
	// <param-value>/jsp/</param-value>
	// </init-param>
	// <init-param>
	// <param-name>view-resource-postfix</param-name>
	// <param-value>.jsp</param-value>
	// </init-param>
	@Test
	public void test1() {

		final ServletConfig servletConfig = (ServletConfig) createStub(ServletConfig.class);
		thenReturn("/jsp/").when(servletConfig).getInitParameter("view-resource-prefix");
		thenReturn(".jsp").when(servletConfig).getInitParameter("view-resource-postfix");
		play(servletConfig);

		final ViewResolver vr = new ViewResolver(servletConfig);
		Assert.assertEquals("/jsp/jou.jsp", vr.resolve("jou"));

	}

}
