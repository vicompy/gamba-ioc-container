package org.gro.annoscan;

import java.lang.annotation.Annotation;

public class AnnotatedClass implements Comparable<AnnotatedClass> {

	public final Annotation annotation;
	public final Class<?> annotatedClass;

	public AnnotatedClass(final Annotation annotation, final Class<?> annotatedClass) {
		super();
		this.annotation = annotation;
		this.annotatedClass = annotatedClass;
	}

	@Override
	public String toString() {
		return annotatedClass.getName() + ": " + annotation.toString();
	}

	public int compareTo(final AnnotatedClass o) {
		return toString().compareTo(o.toString());
	}

}
