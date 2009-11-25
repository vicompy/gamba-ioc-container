package org.gro.demo.actions;

import org.gro.demo.forms.PersonForm;
import org.gro.demo.validators.PersonValidator;
import org.gro.mvc.RequestContext;
import org.gro.mvc.actions.Action;
import org.gro.mvc.forms.EmptyFormBean;

public class SS {

	@Action(name = "S0")
	public String s0(final RequestContext req, final EmptyFormBean form) {
		return "V1";
	}

	@Action(name = "S1", formBean = PersonForm.class, validator = PersonValidator.class, onValidationError = "V1")
	public String s1(final RequestContext req, final PersonForm form) {

		System.out.println("Hello, I'm " + form.getName() + " and my age is " + form.getAge());

		return "/S2.do";
	}

	@Action(name = "S2", formBean = PersonForm.class, validator = PersonValidator.class, onValidationError = "/S0.do")
	public String s2(final RequestContext req, final PersonForm form) {

		System.out.println("Hello, I'm " + form.getName() + " and my age is " + form.getAge());

		return "salute";
	}

}
