package org.homs.gamba.scanner;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.homs.gamba.binding.BindingException;

public class AnnotatedActionsScanner {

	private static final String ACTIONS_BASE_PACKAGE_DEFAULT_VALUE = "org.homs.demo.actions";

	public Map<String, DeclaredAction> doScan(String actionBasePackage) {
		final Map<String, DeclaredAction> declaredActions = new HashMap<String, DeclaredAction>();

		if (actionBasePackage == null) {
			actionBasePackage = ACTIONS_BASE_PACKAGE_DEFAULT_VALUE;
			System.out.println("warning: front-controller init-param not specified, using the default value: "
					+ actionBasePackage);
		}

		/*
		 * llista les classes contingudes
		 */
		Class<?>[] actionClasses = null;
		try {
			actionClasses = getClasses(actionBasePackage);
		} catch (final Exception exc) {
			throw new BindingException("error en scanner d'actions", exc);
		}

		/*
		 * explora les classes en cerca d'anotacions
		 */
		// System.out.println("checking for actions...");
		for (final Class<?> c : actionClasses) {
			// System.out.println("checking for actions: " + c.getName());
			for (final Method m : c.getMethods()) {
				final Action actionAnnotation = m.getAnnotation(Action.class);
				if (actionAnnotation != null) {

					final DeclaredAction da = new DeclaredAction(actionAnnotation.name(), c, m,
							actionAnnotation.formBean());
					declaredActions.put(da.actionName, da);

					System.out.println("action found: " + da.toString());
				}
			}
		}
		// System.out.println("OKA");
		return declaredActions;
	}

	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages.
	 *
	 * @param packageName
	 *            The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private Class<?>[] getClasses(final String packageName) throws ClassNotFoundException, IOException {
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		final String path = packageName.replace('.', '/');
		final Enumeration<URL> resources = classLoader.getResources(path);
		final List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			final URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		for (final File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * Recursive method used to find all classes in a given directory and
	 * subdirs.
	 *
	 * @param directory
	 *            The base directory
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	private List<Class<?>> findClasses(final File directory, final String packageName)
			throws ClassNotFoundException {
		final List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!directory.exists()) {
			return classes;
		}
		final File[] files = directory.listFiles();
		for (final File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.'
						+ file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

}
