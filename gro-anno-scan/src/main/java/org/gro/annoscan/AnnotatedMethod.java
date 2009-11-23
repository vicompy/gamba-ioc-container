package org.gro.annoscan;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotatedMethod implements Comparable<AnnotatedMethod> {

	public final Annotation annotation;
	public final Class<?> annotatedClass;
	public final Method annotatedMethod;

	public AnnotatedMethod(final Annotation annotation, final Class<?> annotatedClass,
			final Method annotatedMethod) {
		super();
		this.annotation = annotation;
		this.annotatedClass = annotatedClass;
		this.annotatedMethod = annotatedMethod;
	}

	@Override
	public String toString() {
		return annotatedClass.getName() + "." + annotatedMethod.getName() + "(...): " + annotation.toString();
	}

	public int compareTo(final AnnotatedMethod o) {
		return toString().compareTo(o.toString());
	}

}
