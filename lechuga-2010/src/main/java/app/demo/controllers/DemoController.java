package app.demo.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lechuga.mvc.validation.Validated;

import app.demo.forms.DemoForm;
import app.demo.forms.DemoValidator;

public class DemoController {

	// private final static GroLog LOG =
	// GroLog.getGroLogger(ControllerScanFacade.class);

	public void setValidators(final Map<Class<?>, Class<?>> map) {
		map.put(DemoForm.class, DemoValidator.class);
	}

	public String start(final HttpServletRequest request, final HttpServletResponse response) {

		return "question-form";
	}

	@Validated(by = DemoValidator.class, onError = "question-form")
	public String sayHello(final HttpServletRequest request, final HttpServletResponse response,
			final DemoForm form) {

		return "/demo/say-hello-second-part.do";
	}

	@Validated(by = DemoValidator.class, onError = "question-form")
	public String sayHelloSecondPart(final HttpServletRequest request, final HttpServletResponse response,
			final DemoForm form) {

		form.setAge(form.getAge() * 2);

		return "salute";
	}

}

/**
 * notepad <br>
 * recordatoris i avisos <br>
 * gestor d'urls d'interés (delicious) <br>
 */
