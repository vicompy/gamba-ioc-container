package gamba.container.parser;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class TokenizerTest {

	private String enListTokens(final String beanTextDef) {
		final Tokenizer t = new Tokenizer(beanTextDef);
		final List<String> tokenList = new ArrayList<String>();

		while (t.hasNextToken()) {
			final String token = t.nextToken();
			tokenList.add(token);
		}
		return tokenList.toString();
	}

	@Test
	public void basictest() {

		// simple singleton bean
		Assert.assertEquals(
			"[~, gamba.container.test.entities.B, ;]",
			enListTokens("~gamba.container.test.entities.B;")
		);

		// simple method injection
		Assert.assertEquals(
			"[gamba.container.test.entities.A, :, setB, <-, b, ;]",
			enListTokens("gamba.container.test.entities.A:setB<-b;")
		);


		// simple constructor injection
		Assert.assertEquals(
			"[gamba.container.test.entities.C, :, (, a, ,, b, ), ;]",
			enListTokens("gamba.container.test.entities.C:(a, b);")
		);

		// simple multiple injection
		Assert.assertEquals(
			"[gamba.container.test.entities.D, :, (, a, ,, b, ), :, setA2, <-, a, ;]",
			enListTokens("gamba.container.test.entities.D:(a, b):setA2<-a;")
		);

		// simple string constructor injection
		Assert.assertEquals(
			"[gamba.container.test.entities.E, (, \"15\", ), ;]",
			enListTokens("gamba.container.test.entities.E(\"15\");")
		);

		// simple string method injection
		Assert.assertEquals(
			"[gamba.container.test.entities.F, :, setI, <-, \"16\", ;]",
			enListTokens("gamba.container.test.entities.F:setI<-\"16\";")
		);

		// simple nested constructor injection
		Assert.assertEquals(
			"[gamba.A, (, {, gamba.B, (, {, gamba.C, }, ), }, ), ;]",
			enListTokens("gamba.A ({gamba.B ({gamba.C})});")
		);

		// simple nested method injection
		Assert.assertEquals(
			"[gamba.A, :, setB, <-, {, gamba.B, :, setC, <-, {, gamba.C, }, }, ;]",
			enListTokens("gamba.A :setB <- {gamba.B :setC <- {gamba.C}};")
		);

		// complex
		Assert.assertEquals(
			"[gamba.Person, (, a, ,, b, ,, \"hi\", ), :, setName, <-, \"27\", :, setAge, <-, {, java.lang.Integer, (, \"27\", ), }, ;]",
			enListTokens("gamba.Person(a,b,\"hi\"):setName <- \"27\":setAge <- {java.lang.Integer(\"27\") };")
		);

		// complex, amb separadors dins d'strings, per verificar que no es separa
		Assert.assertEquals(
			"[gamba.Person, (, a, ,, b, ,, \"h,i\", ), :, setName, <-, \"2  7\", :, setAge, <-, {, java.lang.Integer, (, \"2{}()7\", ), }, ;]",
			enListTokens("gamba.Person(a,b,\"h,i\"):setName <- \"2  7\":setAge <- {java.lang.Integer(\"2{}()7\") };")
		);

	}
}

