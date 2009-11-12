package org.homs.binding;

import java.util.HashMap;
import java.util.Map;

import org.homs.gamba.binding.BeanBinder;
import org.homs.gamba.binding.BindingException;
import org.homs.gamba.binding.IBeanBinder;
import org.junit.Assert;
import org.junit.Test;

public class BeanBinderTest {

	/**
	 * prova que reclamant el bindind d'una mateixa class repetides vegades,
	 * s'obtenen instàncies diferents però amb el mateix estat, amb un sol
	 * anàlisi.
	 */
	@Test
	public void test1() {
		final Map<String, String[]> attr = new HashMap<String, String[]>();
		attr.put("name", new String[] { "mhc" });
		attr.put("age", new String[] { "27" });
		attr.put("members", new String[] { "27", "28" });

		final IBeanBinder bm = new BeanBinder();

		final ExampleBean[] bl = new ExampleBean[3];
		for (int i = 0; i < 3; i++) {
			bl[i] = (ExampleBean) bm.doBind(ExampleBean.class, attr);
		}

		Assert.assertTrue(bl[0].toString().equals(bl[1].toString()));
		Assert.assertTrue(bl[1].toString().equals(bl[2].toString()));

		Assert.assertFalse(bl[0].hashCode() == bl[1].hashCode());
		Assert.assertFalse(bl[1].hashCode() == bl[2].hashCode());
		Assert.assertFalse(bl[2].hashCode() == bl[0].hashCode());

		final ExampleBean b = bl[0];
		Assert.assertEquals("mhc", b.getName());
		Assert.assertEquals(Integer.valueOf(27), Integer.valueOf(b.getAge()));
		Assert.assertEquals(Float.valueOf(27), b.getMembers()[0]);
		Assert.assertEquals(Float.valueOf(28), b.getMembers()[1]);
	}

	/**
	 * prova que els noms dels atributs són case-insensitive
	 */
	@Test
	public void test2() {
		final Map<String, String[]> attr = new HashMap<String, String[]>();
		attr.put("NAME", new String[] { "mhc" });
		attr.put("AGE", new String[] { "27" });
		attr.put("MEMBERS", new String[] { "27", "28" });

		final IBeanBinder bm = new BeanBinder();

		final ExampleBean[] bl = new ExampleBean[3];
		for (int i = 0; i < 3; i++) {
			bl[i] = (ExampleBean) bm.doBind(ExampleBean.class, attr);
		}

		Assert.assertTrue(bl[0].toString().equals(bl[1].toString()));
		Assert.assertTrue(bl[1].toString().equals(bl[2].toString()));

		Assert.assertFalse(bl[0].hashCode() == bl[1].hashCode());
		Assert.assertFalse(bl[1].hashCode() == bl[2].hashCode());
		Assert.assertFalse(bl[2].hashCode() == bl[0].hashCode());

		final ExampleBean b = bl[0];
		Assert.assertEquals("mhc", b.getName());
		Assert.assertEquals(Integer.valueOf(27), Integer.valueOf(b.getAge()));
		Assert.assertEquals(Float.valueOf(27), b.getMembers()[0]);
		Assert.assertEquals(Float.valueOf(28), b.getMembers()[1]);
	}

	/**
	 * prova que si falta per especificar un atribut que té una propietat
	 * coincident, simplement l'ignora i no injecta.
	 */
	@Test
	public void test3() {
		final Map<String, String[]> attr = new HashMap<String, String[]>();
		attr.put("name", new String[] { "mhc" });
		// attr.put("age", new String[] { "27" });
		// attr.put("members", new String[] { "27", "28" });

		final IBeanBinder bm = new BeanBinder();

		final ExampleBean[] bl = new ExampleBean[3];
		for (int i = 0; i < 3; i++) {
			bl[i] = (ExampleBean) bm.doBind(ExampleBean.class, attr);
		}

		Assert.assertTrue(bl[0].toString().equals(bl[1].toString()));
		Assert.assertTrue(bl[1].toString().equals(bl[2].toString()));

		Assert.assertFalse(bl[0].hashCode() == bl[1].hashCode());
		Assert.assertFalse(bl[1].hashCode() == bl[2].hashCode());
		Assert.assertFalse(bl[2].hashCode() == bl[0].hashCode());

		final ExampleBean b = bl[0];
		Assert.assertEquals("mhc", b.getName());
		Assert.assertEquals(0, b.getAge());
		Assert.assertArrayEquals(null, b.getMembers());
	}

	/**
	 * prova que si hi ha un atribut que no té propietat, tira excepció
	 */
	@Test(expected = BindingException.class)
	public void test4() {
		final Map<String, String[]> attr = new HashMap<String, String[]>();
		attr.put("name", new String[] { "mhc" });
		attr.put("age", new String[] { "27" });
		attr.put("atributInexistent", new String[] { "27" });
		attr.put("members", new String[] { "27", "28" });

		final IBeanBinder bm = new BeanBinder();

		@SuppressWarnings("unused")
		final ExampleBean b = (ExampleBean) bm.doBind(ExampleBean.class, attr);
	}

}
