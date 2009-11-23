package org.gro.annoscan.interf;

import java.lang.annotation.Annotation;
import java.util.List;

import org.gro.annoscan.AnnotatedClass;
import org.gro.annoscan.AnnotatedMethod;

public interface IAnnotationsScanner {

	/**
	 * retorna els objectes {@link AnnotatedMethod} corresponents a la repassada
	 * del package especificat. Veure que es retorna un objecte
	 * {@link AnnotatedMethod} per a cada mètode anotat de cadascuna de les
	 * classes trobades.
	 *
	 * @param actionBasePackage
	 * @param annotationClassFor
	 * @return
	 */
	public abstract List<AnnotatedMethod> doMethodScan(final String actionBasePackage,
			final Class<? extends Annotation> annotationClassFor);

	/**
	 * retorna un objecte {@link AnnotatedClass} per a cadascuna de les classes
	 * anotades per l'anotació especificada; i dins del package especificat.
	 *
	 * @param actionBasePackage
	 * @param annotationClassFor
	 * @return
	 */
	public List<AnnotatedClass> doClassScan(final String actionBasePackage,
			final Class<? extends Annotation> annotationClassFor);
}