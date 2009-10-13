package org.homs.gamba.container.parser;


import org.homs.gamba.container.exception.GambaException;
import org.homs.gamba.container.parser.GambaPropertiesParser;
import org.junit.Assert;
import org.junit.Test;

public class PropertiesParserTest extends GambaPropertiesParser {


	public PropertiesParserTest() throws GambaException {
		super("PropertiesParser-test-context.properties");
	}

	@Test
	public void test1() {

		Assert.assertEquals(
			"a = gamba.container.test.entities.A :setB <- b = ~gamba.container.test.entities.B :setMsg <- \"jou\";;",
			this.obtainParsedBeanDef("a").toString()
		);

		Assert.assertEquals(
			"ba = gamba.container.test.entities.A :setB <- { inner-anonymous = gamba.container.test.entities.B :setMsg <- \"jou\"; };",
			this.obtainParsedBeanDef("ba").toString()
		);

		Assert.assertEquals(
			"c = gamba.container.test.entities.C(a = gamba.container.test.entities.A :setB <- b = ~gamba.container.test.entities.B :setMsg <- \"jou\";;, b = ~gamba.container.test.entities.B :setMsg <- \"jou\";) ;",
			this.obtainParsedBeanDef("c").toString()
		);
	}
}
