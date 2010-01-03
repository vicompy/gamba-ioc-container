package org.lechuga.mvc.scan.method;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.lechuga.mvc.annoscan.sp.UserController;
import org.lechuga.mvc.annoscan.sp.sk.NightlyJobController;
import org.lechuga.mvc.scan.method.MethodScanner;

public class MethodScannerTest {

	@Test
	public void test1() {

		final Class<?>[] cl = new Class<?>[] {
				UserController.class, NightlyJobController.class
		};

		final Map<Class<?>, List<Method>> methodsMap = new MethodScanner().nonObjectableMethods(cl);

		assertEquals(
			"[public void org.lechuga.mvc.annoscan.sp.UserController.delete()]",
			methodsMap.get(UserController.class).toString());

		assertEquals(
			"[public void org.lechuga.mvc.annoscan.sp.sk.NightlyJobController.list(), " +
			"public void org.lechuga.mvc.annoscan.sp.sk.NightlyJobController.show()]",
			methodsMap.get(NightlyJobController.class).toString());
	}

}
