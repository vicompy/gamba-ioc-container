package org.gro.frontcontroller.actions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.gro.frontcontroller.forms.EmptyFormBean;
import org.gro.validation.IGroValidator;
import org.gro.validation.utils.VoidValidator;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Action {

	String name();
	Class<?> formBean() default EmptyFormBean.class;

	Class<? extends IGroValidator> validator() default VoidValidator.class;
	String onValidationError() default "";
}
