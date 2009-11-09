package org.homs.gamba.scanner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.homs.gamba.extras.EmptyFormBean;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Action {

	String name();
	Class<?> formBean() default EmptyFormBean.class;
}
