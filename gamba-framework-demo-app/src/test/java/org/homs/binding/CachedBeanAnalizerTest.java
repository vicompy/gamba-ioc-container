package org.homs.binding;

import java.util.Map;

import junit.framework.Assert;

import org.homs.gamba.binding.BeanInfo;
import org.homs.gamba.binding.BeanPropInfo;
import org.homs.gamba.binding.CachedBeanAnalizer;
import org.junit.Test;

public class CachedBeanAnalizerTest {

	@Test
	public void test1() {
		final CachedBeanAnalizer cba1 = CachedBeanAnalizer.getInstance();
		final CachedBeanAnalizer cba2 = CachedBeanAnalizer.getInstance();

		Assert.assertEquals(cba1, cba2);
		Assert.assertTrue(cba1 == cba2);
	}

	@Test
	public void test2() {
		final BeanInfo bi = CachedBeanAnalizer.getInstance().analize(B.class);

		final Map<String, BeanPropInfo> props = bi.getBeanProps();

		Assert.assertEquals(B.class, bi.getBeanClass());

		final BeanPropInfo nameInfo = props.get("NAME");
		Assert.assertEquals(false, nameInfo.argIsArray);
		Assert.assertEquals(String.class, nameInfo.argType);
		Assert.assertEquals("setName", nameInfo.method.getName());
		Assert.assertEquals("setName", nameInfo.setterName);

		final BeanPropInfo ageInfo = props.get("AGE");
		Assert.assertEquals(false, ageInfo.argIsArray);
		Assert.assertEquals(int.class, ageInfo.argType);
		Assert.assertEquals("setAge", ageInfo.method.getName());
		Assert.assertEquals("setAge", ageInfo.setterName);

		final BeanPropInfo membersInfo = props.get("MEMBERS");
		Assert.assertEquals(true, membersInfo.argIsArray);
		Assert.assertEquals(Float[].class, membersInfo.argType);
		Assert.assertEquals("setMembers", membersInfo.method.getName());
		Assert.assertEquals("setMembers", membersInfo.setterName);
	}

}
