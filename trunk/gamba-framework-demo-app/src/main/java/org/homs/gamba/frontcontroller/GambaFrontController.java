package org.homs.gamba.frontcontroller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.homs.gamba.binding.BeanBinder;
import org.homs.gamba.binding.BindingException;
import org.homs.gamba.binding.IBeanBinder;
import org.homs.gamba.connectionpool.GambaPooling;
import org.homs.gamba.scanner.AnnotatedActionsScanner;
import org.homs.gamba.scanner.DeclaredAction;

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
		definedActions = new AnnotatedActionsScanner().doScan(actionBasePackage);
		viewResolver = new ViewResolver(this);
	}

	@Override
	public void destroy() {
		GambaPooling.getInstance().destroyAllConnections();
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

		final String requestServletPath = request.getServletPath();
		// /jou.do ==> jou
		final String actionName = requestServletPath.substring(1, requestServletPath.length()
				- ACTION_EXTENSION.length());

		final DeclaredAction declaredAction = this.definedActions.get(actionName);
		if (declaredAction == null) {
			throw new BindingException("error d'action no trobada per la request: " + actionName);
		}

		final Object actionForm = this.httpBinder
				.doBind(declaredAction.actionForm, request.getParameterMap());
		final Object action = obtainActionInstance(declaredAction);

		final RequestContext requestContext = new RequestContext(request, response);
		String redirectResource = actionInvoker(declaredAction, actionForm, action, requestContext);

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
