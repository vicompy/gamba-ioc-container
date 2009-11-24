package org.gro.demo.actions;

import org.gro.demo.forms.PersonForm;
import org.gro.demo.validators.PersonValidator;
import org.gro.mvc.RequestContext;
import org.gro.mvc.actions.Action;
import org.gro.mvc.forms.EmptyFormBean;


public class ShowMessageAction {

	@Action(name = "start")
	public String start(final RequestContext req, final EmptyFormBean form) {
		return "question-form";
	}

	@Action(name = "sayHello", formBean = PersonForm.class,
			validator = PersonValidator.class, onValidationError = "question-form")
	public String salute(final RequestContext req, final PersonForm form) {

		System.out.println("Hello, I'm " + form.getName() + " and my age is " + form.getAge());

		req.getRequest().setAttribute("form", form);

		return "salute";
	}

}
