package org.lechuga.mvc.binding;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.lechuga.mvc.binding.BeanInfo;
import org.lechuga.mvc.binding.BeanPropInfo;
import org.lechuga.mvc.binding.CachedBeanAnalizer;

public class CachedBeanAnalizerTest {

//	@Test
//	public void test1() {
//		final CachedBeanAnalizer cba1 = new CachedBeanAnalizer();
//		final CachedBeanAnalizer cba2 = new CachedBeanAnalizer();
//
//		Assert.assertEquals(cba1, cba2);
//		Assert.assertTrue(cba1 == cba2);
//	}

	@Test
	public void test2() {
		final BeanInfo bi = new CachedBeanAnalizer().analize(ExampleBean.class);

		final Map<String, BeanPropInfo> props = bi.getBeanProps();

		Assert.assertEquals(ExampleBean.class, bi.getBeanClass());

		final BeanPropInfo nameInfo = props.get("name");
		Assert.assertEquals(false, nameInfo.argIsArray);
		Assert.assertEquals(String.class, nameInfo.argType);
		Assert.assertEquals("setName", nameInfo.setterMethod.getName());
		Assert.assertEquals("setName", nameInfo.setterMethod.getName());

		final BeanPropInfo ageInfo = props.get("age");
		Assert.assertEquals(false, ageInfo.argIsArray);
		Assert.assertEquals(int.class, ageInfo.argType);
		Assert.assertEquals("setAge", ageInfo.setterMethod.getName());
		Assert.assertEquals("setAge", ageInfo.setterMethod.getName());

		final BeanPropInfo membersInfo = props.get("members");
		Assert.assertEquals(true, membersInfo.argIsArray);
		Assert.assertEquals(Float[].class, membersInfo.argType);
		Assert.assertEquals("setMembers", membersInfo.setterMethod.getName());
		Assert.assertEquals("setMembers", membersInfo.setterMethod.getName());
	}

}
