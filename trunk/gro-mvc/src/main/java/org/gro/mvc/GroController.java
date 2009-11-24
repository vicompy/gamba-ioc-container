package org.gro.mvc;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gro.binding.BeanBinder;
import org.gro.binding.BindingException;
import org.gro.binding.IBeanBinder;
import org.gro.mvc.actions.ActionScanner;
import org.gro.mvc.actions.DeclaredAction;
import org.gro.validation.IGroValidator;
import org.gro.validation.ValidationDSL;

// TODO incloure gamba-logging i ficar logs arreu

/**
 * Servlet implementation class GambaFrontController
 */
public class GroController extends HttpServlet {

	private static final long serialVersionUID = 8918658103855642986L;

	/**
	 * nom del paràmetre de servlet definit en <tt>web.xml</tt>, que servirà per
	 * scannejar el package que conté les accions de l'aplicació.
	 */
	private static final String ACTIONS_BASE_PACKAGE = "actions-base-package";

	/**
	 * extensió de les crides a Action, ha de concordar en longitud amb el
	 * mapping de servlet fet en <tt>web.xml</tt>. Preval l'extensió mapejada en
	 * <tt>web.xml</tt>.
	 */
	private static final String ACTION_EXTENSION = ".do";

	/**
	 * objecte mapejador de paràmetres HTTP a beans de formulari.
	 */
	private final IBeanBinder httpBinder;

	/**
	 * <tt>Map</tt> d'accions anotades, trobades per l'scaneig fet en package
	 * d'accions.
	 */
	private Map<String, DeclaredAction> definedActions;

	/**
	 * resolutor de vistes TODO
	 */
	private IViewResolver viewResolver;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GroController() {
		super();
		httpBinder = new BeanBinder();
	}

	/**
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() {
		/*
		 * obté el package de actions i executa l'scanner de mètodes anotats
		 */
		final String actionBasePackage = getInitParameter(ACTIONS_BASE_PACKAGE);
		definedActions = new ActionScanner().doScan(actionBasePackage);

		/*
		 * carrega el resolutor de vistes
		 */
		viewResolver = new ViewResolver(this);
	}

	/**
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy() {
		// try {
		// GambaPooling.getInstance().getConnection().createStatement().executeUpdate("SHUTDOWN");
		// } catch (final SQLException exc) {
		// }
		// GambaPooling.getInstance().destroyAllConnections();
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

			request.setAttribute("validationErrorMap", validationErrorMap);
			putParamsAsAttributes(request);

			redirectResource = declaredAction.resourceIfInvalidForm;

		} else {

			System.out.println(">>>>>>>> valid form");

			// obté el BeanForm i el binda amb els paràmetres HTTP
			final Object actionForm = this.httpBinder.doBind(declaredAction.actionForm, request
					.getParameterMap());

			// invoca la Action
			redirectResource = actionInvoker(declaredAction, actionForm, requestContext);

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
	 * invoca la Action especificada
	 *
	 * @param declaredAction
	 * @param actionForm
	 * @param action
	 * @param requestContext
	 * @return
	 */
	private String actionInvoker(final DeclaredAction declaredAction, final Object actionForm,
			/*final Object action,*/ final RequestContext requestContext) {
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
