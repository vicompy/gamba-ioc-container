package org.gro.frontcontroller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gro.binding.BeanBinder;
import org.gro.binding.BindingException;
import org.gro.binding.IBeanBinder;
import org.gro.frontcontroller.actions.ActionScanner;
import org.gro.frontcontroller.actions.DeclaredAction;
import org.gro.validation.IGroValidator;
import org.gro.validation.ValidationDSL;

// TODO incloure gamba-logging i ficar logs arreu

/**
 * Servlet implementation class GambaFrontController
 */
public class GambaFrontController extends HttpServlet {

	private static final long serialVersionUID = 8918658103855642986L;

	private static final String ACTIONS_BASE_PACKAGE = "actions-base-package";
	private static final String ACTION_EXTENSION = ".do";

	private final IBeanBinder httpBinder;
	private Map<String, DeclaredAction> definedActions;

	private ViewResolver viewResolver;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GambaFrontController() {
		super();
		httpBinder = new BeanBinder();
	}

	@Override
	public void init() {
		/*
		 * obté el package de actions i executa l'scanner de mètodes anotats
		 */
		final String actionBasePackage = getInitParameter(ACTIONS_BASE_PACKAGE);
		definedActions = new ActionScanner().doScan(actionBasePackage);
		viewResolver = new ViewResolver(this);
	}

	@Override
	public void destroy() {
//		try {
//			GambaPooling.getInstance().getConnection().createStatement().executeUpdate("SHUTDOWN");
//		} catch (final SQLException exc) {
//		}
//		GambaPooling.getInstance().destroyAllConnections();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void doAction(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		// determina el nom identificador d'action demanada
		// /jou.do ==> jou
		final String requestServletPath = request.getServletPath();
		final String actionName = requestServletPath.substring(1, requestServletPath.length()
				- ACTION_EXTENSION.length());

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

		System.out.println(">>>>>>>> " + declaredAction.validatorClass);
		System.out.println(">>>>>>>> " + validationErrorMap.toString());

		if (!validationErrorMap.isEmpty()) {
			System.out.println(">>>>>>>> invalid form");
			redirectResource = declaredAction.resourceIfInvalidForm;
			request.setAttribute("validationErrorMap", validationErrorMap);
			// TODO cal retornar els paràmetres, però la vista necessitarà
			// mostrar els membres del bean, al que no es pot bindar, ja que els
			// valors són invàlids. Si això no es pot fer, en fallar una
			// validació el form queda tot en blanc!!
		} else {
			System.out.println(">>>>>>>> valid form");

			// obté el BeanForm i el binda amb els paràmetres HTTP
			final Object actionForm = this.httpBinder.doBind(declaredAction.actionForm, request
					.getParameterMap());
			final Object action = obtainActionInstance(declaredAction);

			// invoca la ACtion
			redirectResource = actionInvoker(declaredAction, actionForm, action, requestContext);

		}

		// variables predefinides a accedir desde JSP en requestScope
		request.setAttribute("requestContext", requestContext);
		request.setAttribute("contextName", request.getContextPath());

		// resol la vista a la que redireccionar
		redirectResource = this.viewResolver.resolve(redirectResource);

		// redirecciona a vista
		getServletConfig().getServletContext().getRequestDispatcher(redirectResource).forward(request,
				response);

	}

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

	private String actionInvoker(final DeclaredAction declaredAction, final Object actionForm,
			final Object action, final RequestContext requestContext) {
		String redirectResource = null;
		try {
			redirectResource = (String) declaredAction.actionMethod.invoke(action, new Object[] {
					requestContext, actionForm });
		} catch (final Exception exc) {
			throw new BindingException("error invocant action: " + declaredAction.actionClass.getName() + "."
					+ declaredAction.actionMethod.getName(), exc);
		}
		return redirectResource;
	}

}
