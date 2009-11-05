package org.homs.binding;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.homs.test1.actions.B;
import org.junit.Test;

public class BindingTest {

	/**
	 * <b>CatchedBeanBinder:</b><br>
	 * prova que reclamant el bindind d'una mateixa class repetides vegades,
	 * s'obtenen instàncies diferents però amb el mateix estat, amb un sol
	 * anàlisi.
	 */
	@Test
	public void test1() {
		final Map<String, Object> attr = new HashMap<String, Object>();
		attr.put("name", "mhc");
		attr.put("age", "27");
		attr.put("members", new String[] { "27", "28" });

		final IBinder bm = new CatchedBeanBinder();

		final B[] bl = new B[3];
		for (int i = 0; i < 3; i++) {
			bl[i] = (B) bm.doBind(B.class, attr);
			System.out.println(bl[i].toString());
			System.out.println(bl[i].hashCode());
		}

		Assert.assertTrue(bl[0].toString().equals(bl[1].toString()));
		Assert.assertTrue(bl[1].toString().equals(bl[2].toString()));

		Assert.assertFalse(bl[0].hashCode() == bl[1].hashCode());
		Assert.assertFalse(bl[1].hashCode() == bl[2].hashCode());
		Assert.assertFalse(bl[2].hashCode() == bl[0].hashCode());
	}

	/**
	 * <b>BeanBinder:</b><br>
	 * prova que reclamant el bindind d'una mateixa class repetides vegades,
	 * s'obtenen instàncies diferents però amb el mateix estat, requerint un
	 * anàlisi cada vegada (costós).
	 */
	@Test
	public void test2() {
		final Map<String, Object> attr = new HashMap<String, Object>();
		attr.put("name", "mhc");
		attr.put("age", "27");
		attr.put("members", new String[] { "27", "28" });

		final IBinder bm = new BeanBinder();

		final B[] bl = new B[3];
		for (int i = 0; i < 3; i++) {
			bl[i] = (B) bm.doBind(B.class, attr);
			System.out.println(bl[i].toString());
			System.out.println(bl[i].hashCode());
		}

		Assert.assertTrue(bl[0].toString().equals(bl[1].toString()));
		Assert.assertTrue(bl[1].toString().equals(bl[2].toString()));

		Assert.assertFalse(bl[0].hashCode() == bl[1].hashCode());
		Assert.assertFalse(bl[1].hashCode() == bl[2].hashCode());
		Assert.assertFalse(bl[2].hashCode() == bl[0].hashCode());
	}
}