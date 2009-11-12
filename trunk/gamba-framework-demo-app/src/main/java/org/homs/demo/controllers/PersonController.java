package org.homs.demo.controllers;

import java.util.List;

import org.homs.demo.formbeans.PersonForm;
import org.homs.demo.models.IPersonBO;
import org.homs.demo.models.Person;
import org.homs.demo.models.PersonBO;
import org.homs.demo.validators.PersonFormValidator;
import org.homs.gamba.bo.GambaBOLoader;
import org.homs.gamba.extras.EmptyFormBean;
import org.homs.gamba.frontcontroller.RequestContext;
import org.homs.gamba.scanner.Action;

public class PersonController {


	@Action(name = "list")
	public String toListView(final RequestContext req, final Object form) {

		final IPersonBO personBO = (IPersonBO) GambaBOLoader.newInstance(PersonBO.class);

		final List<Person> personList = personBO.findAll();

		req.getRequest().setAttribute("personList", personList);

		return "person-list";
	}

	@Action(name = "create-form")
	public String toCreateForm(final RequestContext req, final EmptyFormBean form) {

		return "create-form";
	}

	@Action(name = "create", formBean=PersonForm.class, validator=PersonFormValidator.class, onValidationError="create-form")
	public String createPerson(final RequestContext req, final PersonForm form) {

		final IPersonBO personBO = (IPersonBO) GambaBOLoader.newInstance(PersonBO.class);

		final Person person = new Person();
		person.setId(null);
		person.setName(form.getName());
		person.setAge(form.getAge());

		personBO.insert(person);

		return toListView(req, form);
	}


}
