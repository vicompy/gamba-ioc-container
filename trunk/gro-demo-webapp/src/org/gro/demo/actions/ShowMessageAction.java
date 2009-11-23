package org.gro.demo.actions;

import org.gro.demo.forms.PersonForm;
import org.gro.frontcontroller.RequestContext;
import org.gro.frontcontroller.actions.Action;


public class ShowMessageAction {

	@Action(name="sayHello", formBean=PersonForm.class)
	public String a(final RequestContext req, final PersonForm form) {

		System.out.println("Hello, I'm Gro!");

		return "create-form";
	}
}
