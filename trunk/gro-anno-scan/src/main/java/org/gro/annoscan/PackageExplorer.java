package org.gro.annoscan;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.gro.annoscan.interf.IPackageExplorer;

class PackageExplorer implements IPackageExplorer {

	/**
	 * @see org.gro.annoscan.interf.IPackageExplorer#getClasses(java.lang.String)
	 */
	public Class<?>[] getClasses(final String basePackageName) throws AnnoScannerException {

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
				throw new AnnoScannerException("no annotated classes found");
			}
			return classes.toArray(new Class[classes.size()]);

		} catch (final Exception e) {
			throw new AnnoScannerException("", e);
		}
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
	protected List<Class<?>> findClasses(final File directory, final String packageName)
			throws ClassNotFoundException {

		final List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!directory.exists()) {
			return classes;
		}

		final File[] files = directory.listFiles();
		for (final File file : files) {
			if (file.isDirectory()) {
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.'
						+ file.getName().substring(0, file.getName().length() - 6)));
			}
		}

		return classes;
	}

}
