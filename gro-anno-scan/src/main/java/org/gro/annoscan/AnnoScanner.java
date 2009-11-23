package org.gro.annoscan;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.gro.annoscan.interf.IAnnotationsScanner;
import org.gro.annoscan.interf.IPackageExplorer;

public class AnnoScanner implements IAnnotationsScanner {

	private final IPackageExplorer packageExplorer;

	public AnnoScanner() {
		this.packageExplorer = new PackageExplorer();
	}

	/**
	 * @see org.gro.annoscan.interf.IAnnotationsScanner#doMethodScan(java.lang.String,
	 *      java.lang.Class)
	 */
	public List<AnnotatedMethod> doMethodScan(final String actionBasePackage,
			final Class<? extends Annotation> annotationClassFor) {

		final List<AnnotatedMethod> declaredActions = new ArrayList<AnnotatedMethod>();

		final Class<?>[] actionClasses = packageExplorer.getClasses(actionBasePackage);

		/*
		 * explora les classes en cerca d'anotacions en mètodes
		 */
		for (final Class<?> c : actionClasses) {
			for (final Method m : c.getMethods()) {
				final Annotation actionAnnotation = m.getAnnotation(annotationClassFor);
				if (actionAnnotation != null) {
					declaredActions.add(new AnnotatedMethod(actionAnnotation, c, m));
				}
			}
		}

		return declaredActions;
	}

	public List<AnnotatedClass> doClassScan(final String actionBasePackage,
			final Class<? extends Annotation> annotationClassFor) {

		final List<AnnotatedClass> declaredActions = new ArrayList<AnnotatedClass>();

		final Class<?>[] actionClasses = packageExplorer.getClasses(actionBasePackage);

		/*
		 * explora les classes en cerca d'anotacions en classes
		 */
		for (final Class<?> c : actionClasses) {
			final Annotation actionAnnotation = c.getAnnotation(annotationClassFor);
			if (actionAnnotation != null) {
				declaredActions.add(new AnnotatedClass(actionAnnotation, c));
			}
		}

		return declaredActions;
	}
}
