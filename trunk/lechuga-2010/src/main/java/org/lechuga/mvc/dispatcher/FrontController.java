package org.lechuga.mvc.dispatcher;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gro.logging.GroLog;
import org.lechuga.mvc.scan.ClassMethod;
import org.lechuga.mvc.scan.ControllerScanFacade;

/**
 * Servlet implementation class FrontController
 */
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Dispatcher dispatcher;
	private ViewResolver viewResolver;

	final static GroLog LOG = GroLog.getGroLogger(FrontController.class).config();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FrontController() {
		super();
	}

	@Override
	public void init() {

		LOG.info("Starting front-controller");

		try {
			String controllerBasePackage = getInitParameter(ConfigConstants.CONTROLLERS_BASE_PACKAGE_SERVLET_PARAMETER);
			if (controllerBasePackage == null) {
				controllerBasePackage = ConfigConstants.CONTROLLERS_BASE_PACKAGE_DEFAULT_VALUE;
			}
			LOG.info(ConfigConstants.CONTROLLERS_BASE_PACKAGE_SERVLET_PARAMETER, " = ",
							controllerBasePackage);
			final Map<String, ClassMethod> controllerMappingsMap = ControllerScanFacade
					.getControllerMappingsMap(controllerBasePackage);

			this.dispatcher = new Dispatcher(controllerMappingsMap);
		} catch (final Exception e) {
			LOG.severe("error during starting front-controller");
			LOG.severe(e);
		}

		String viewResourcePrefix = getInitParameter(ConfigConstants.VIEW_RESOURCE_PREFIX_SERVLET_PARAMETER);
		String viewResourcePostfix = getInitParameter(ConfigConstants.VIEW_RESOURCE_POSTFIX_SERVLET_PARAMETER);
		if (viewResourcePrefix == null) {
			viewResourcePrefix = ConfigConstants.VIEW_RESOURCE_PREFIX_DEFAULT;
		}
		if (viewResourcePostfix == null) {
			viewResourcePostfix = ConfigConstants.VIEW_RESOURCE_POSTFIX_DEFAULT;
		}
		viewResolver = new ViewResolver(viewResourcePrefix, viewResourcePostfix);

		LOG.info("front-controller started OK");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		generalAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		generalAction(request, response);
	}

	protected void generalAction(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final String forwardResourceName = this.dispatcher.generalAction(request, response);
		final String resolvedForwardResourceName = viewResolver.resolve(forwardResourceName);

		// setteja en requestScope algunes variables per a la vista
		request.setAttribute(ConfigConstants.CONTEXT_NAME_ATTRIBUTE_NAME, request.getContextPath());

		// desa els requestParameters com a atributs
		putParamsAsAttributes(request);

		// redirecciona al recurs resolt
		getServletConfig().getServletContext().getRequestDispatcher(resolvedForwardResourceName).forward(
				request, response);

	}

	/**
	 * en rebutjar una validació i redireccionar de retorn a la mateixa vista,
	 * aquesta necessita mostrar els paràmetres HTTP que tenia entrats i han
	 * sigut rebutjats. Per satisfer això en aquest cas, els valors rebuts per
	 * HTTP es reenvien a la vista en forma d'atributs.
	 *
	 * @param request
	 */
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
