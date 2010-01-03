package org.lechuga.mvc.scan.clas;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * donat un package especificat, en llista totes les classes contingudes (també
 * dins de subdirectoris).
 *
 * @author mhoms
 */
public class ClassScanner {

	public Class<?>[] getClasses(final String basePackageName)
			throws ClassScannerException{

		try {
			final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			final String path = basePackageName.replace('.', '/');
			final Enumeration<URL> resources = classLoader.getResources(path);
			final List<File> dirs = new ArrayList<File>();

			while (resources.hasMoreElements()) {
				final URL resource = resources.nextElement();
				dirs.add(new File(resource.getFile()));
			}

			final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
			for (final File directory : dirs) {
				classes.addAll(findClasses(directory, basePackageName));
			}

			if (classes.isEmpty()) {
				throw new ClassScannerException("no controller classes found");
			}
			return classes.toArray(new Class[classes.size()]);

		} catch (final IOException e) {
			throw new ClassScannerException("exception thrown during controllers scan", e);
		} catch (final ClassNotFoundException e) {
			throw new ClassScannerException("exception thrown during controllers scan", e);
		}
	}

	/**
	 * Recursive method used to find all classes in a given directory and
	 * subdirs.
	 *
	 * @param directory The base directory
	 * @param packageName The package name for classes found inside the base
	 *            directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	protected List<Class<?>> findClasses(final File directory, final String packageName) throws ClassNotFoundException {

		final List<Class<?>> classes = new ArrayList<Class<?>>();

		if (!directory.exists()) {
			return classes;
		}

		final File[] files = directory.listFiles();
		for (final File file : files) {

			if (file.isDirectory()) {

				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {

				final Class<?> claz = Class.forName(packageName + '.'
						+ file.getName().substring(0, file.getName().length() - 6));

				classes.add(claz);
			}
		}

		return classes;
	}

}
