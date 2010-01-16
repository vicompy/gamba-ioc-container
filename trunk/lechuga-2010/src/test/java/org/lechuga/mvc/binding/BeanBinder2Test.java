package org.lechuga.mvc.binding;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class BeanBinder2Test {

	/**
	 * prova que reclamant el bindind d'una mateixa class repetides vegades,
	 * s'obtenen instàncies diferents però amb el mateix estat, amb un sol
	 * anàlisi.
	 */
	@Test
	public void test1() {
		final Map<String, String[]> attr = new HashMap<String, String[]>();
		attr.put("s", new String[] { "1" });
		attr.put("i", new String[] { "2" });
		attr.put("l", new String[] { "3" });
		attr.put("f", new String[] { "4" });
		attr.put("d", new String[] { "5" });
		attr.put("b", new String[] { "true" });

		attr.put("ss", new String[] { "1" });
		attr.put("ii", new String[] { "2" });
		attr.put("ll", new String[] { "3" });
		attr.put("ff", new String[] { "4" });
		attr.put("dd", new String[] { "5" });
		attr.put("bb", new String[] { "non-true" });

		final IBeanBinder bm = new BeanBinder();
		final ExampleBean2 eb = (ExampleBean2) bm.doBind(ExampleBean2.class, attr);

		assertEquals("ExampleBean2 [b=true, bb=false, d=5.0, dd=5.0, f=4.0, ff=4.0, i=2, ii=2, l=3, ll=3, s=1, ss=1]", eb.toString());
	}

}
