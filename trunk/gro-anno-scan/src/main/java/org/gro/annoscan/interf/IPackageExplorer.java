package org.gro.annoscan.interf;

import org.gro.annoscan.AnnoScannerException;

public interface IPackageExplorer {

	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages.
	 * 
	 * @param basePackageName
	 *            The base package
	 * @return <tt>null</tt> if no annotated classes found, or if
	 *         <tt>basePackageName</tt> path is invalid.
	 * @throws AnnoScannerException
	 */
	public abstract Class<?>[] getClasses(final String basePackageName) throws AnnoScannerException;

}