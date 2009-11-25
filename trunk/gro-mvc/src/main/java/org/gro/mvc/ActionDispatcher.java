package org.gro.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gro.binding.BeanBinder;
import org.gro.binding.BindingException;
import org.gro.binding.IBeanBinder;
import org.gro.logging.GroLog;
import org.gro.mvc.actions.DeclaredAction;
import org.gro.validation.IGroValidator;
import org.gro.validation.ValidationDSL;

public class ActionDispatcher implements IActionDispatcher {

	private static final GroLog log = GroLog.getGroLogger(ActionDispatcher.class);

	/**
	 * objecte mapejador de paràmetres HTTP a beans de formulari.
	 */
	private final IBeanBinder httpBinder;

	/**
	 * <tt>Map</tt> d'accions anotades, trobades per l'scaneig fet en package
	 * d'accions.
	 */
	private final Map<String, DeclaredAction> definedActions;

	public ActionDispatcher(final Map<String, DeclaredAction> definedActions) {
		this.definedActions = definedActions;
		httpBinder = new BeanBinder();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.gro.mvc.IActionDispatcher#dispatcher(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public String dispatcher(final HttpServletRequest request, final HttpServletResponse response,
			final String actionName) {

		// obté la configuració definida de la action demanada
		final DeclaredAction declaredAction = this.definedActions.get(actionName);
		if (declaredAction == null) {
			throw new BindingException("error d'action no trobada per la request: " + actionName);
		}

		IGroValidator validatorObject = null;
		try {
			validatorObject = (IGroValidator) declaredAction.validatorClass.newInstance();
		} catch (final InstantiationException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		} catch (final IllegalAccessException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}

		String redirectResource;

		final Map<String, String> validationErrorMap = validatorObject.validate(ValidationDSL
				.getInstance(request.getParameterMap()));
		final RequestContext requestContext = new RequestContext(request, response);

		if (validationErrorMap.isEmpty()) {

			log.fine("parameters are accepted by validator: ", declaredAction.validatorClass.getName());

			// obté el BeanForm i el binda amb els paràmetres HTTP
			final Object actionForm = this.httpBinder.doBind(declaredAction.actionForm, request
					.getParameterMap());

			request.setAttribute("form", actionForm);

			// invoca la Action
			log.fine("invoking action: ", declaredAction.actionClass.getName(), ".",
					declaredAction.actionMethod.getName(), "()");
			redirectResource = actionInvoker(declaredAction, actionForm, requestContext);

		} else {

			// System.out.println(">>>>>>>> invalid form");
			log.fine("parameters are rejected by validator: ", declaredAction.validatorClass.getName());

			request.setAttribute("validationErrorMap", validationErrorMap);
			putParamsAsAttributes(request);

			redirectResource = declaredAction.resourceIfInvalidForm;

		}

		// variables predefinides a accedir desde JSP en requestScope
		request.setAttribute("requestContext", requestContext);
		request.setAttribute("contextName", request.getContextPath());

		log.fine("redirecting to: ", redirectResource);
		return redirectResource;
	}

	/**
	 * invoca la Action especificada
	 *
	 * @param declaredAction
	 * @param actionForm
	 * @param action
	 * @param requestContext
	 * @return
	 */
	private String actionInvoker(final DeclaredAction declaredAction, final Object actionForm,
			final RequestContext requestContext) {
		String redirectResource = null;
		final Object action = obtainActionInstance(declaredAction);
		try {
			redirectResource = (String) declaredAction.actionMethod.invoke(action, new Object[] {
					requestContext, actionForm });
		} catch (final Exception exc) {
			throw new BindingException("error invocant action: " + declaredAction.actionClass.getName() + "."
					+ declaredAction.actionMethod.getName(), exc);
		}
		return redirectResource;
	}

	/**
	 * donada una <tt>DeclaredAction</tt> del <tt>Map</tt> d'accions detectades
	 * per scanner, n'obté la <tt>Class</tt> de l'objecte Action, i en retorna
	 * una instància.
	 *
	 * @param declaredAction
	 * @return
	 */
	private Object obtainActionInstance(final DeclaredAction declaredAction) {
		Object action = null;
		try {
			action = declaredAction.actionClass.newInstance();
		} catch (final Exception exc) {
			throw new BindingException("error instanciant action: " + declaredAction.actionClass.getName(),
					exc);
		}
		return action;
	}

	/**
	 * en rebutjar una validació i redireccionar de retorn a la mateixa vista,
	 * aquesta necessita mostrar els paràmetres HTTP que tenia entrats i han
	 * sigut rebutjats. Per satisfer això en aquest cas, els valors rebuts per
	 * HTTP es reenvien a la vista en forma d'atributs.
	 *
	 * @param request
	 */
	// TODO
	@SuppressWarnings("unchecked")
	private void putParamsAsAttributes(final HttpServletRequest request) {
		final Map<String, String[]> paramsMap = request.getParameterMap();
		for (final String paramName : paramsMap.keySet()) {
			final String[] value = paramsMap.get(paramName);
			if (value.length == 1) {
				request.setAttribute(paramName, paramsMap.get(paramName)[0]);
			} else {
				request.setAttribute(paramName, paramsMap.get(paramName));
			}
		}
	}

}
