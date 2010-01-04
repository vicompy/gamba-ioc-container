package app.demo.controllers;

import org.lechuga.mvc.dispatcher.RequestContext;
import org.lechuga.mvc.validation.Validated;

import app.demo.forms.DemoForm;
import app.demo.forms.DemoValidator;

public class DemoController {

	// private final static GroLog LOG =
	// GroLog.getGroLogger(ControllerScanFacade.class);

	public String start(final RequestContext ctx) {

		return "question-form";
	}

	@Validated(by = DemoValidator.class, onError = "question-form")
	public String sayHello(final RequestContext ctx, final DemoForm form) {

		return "/demo/say-hello-second-part.do";
	}

	@Validated(by = DemoValidator.class, onError = "question-form")
	public String sayHelloSecondPart(final RequestContext ctx, final DemoForm form) {

		form.setAge(form.getAge() * 2);

		return "salute";
	}

}

/**
 * notepad <br>
 * recordatoris i avisos <br>
 * gestor d'urls d'interés (delicious bookmarks) <br>
 * lineproject - facturació de hores <br>
 */
