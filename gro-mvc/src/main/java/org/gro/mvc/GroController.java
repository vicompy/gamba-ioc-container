package org.gro.mvc;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gro.logging.GroLog;
import org.gro.mvc.actions.ActionScanner;
import org.gro.mvc.actions.DeclaredAction;

// TODO incloure gamba-logging i ficar logs arreu

/**
 * Servlet implementation class GambaFrontController
 */
public class GroController extends HttpServlet {

	private static final long serialVersionUID = 8918658103855642986L;

	private static final GroLog LOG = GroLog.getGroLogger(GroController.class).config();

	/**
	 * resolutor de vistes TODO
	 */
	private IViewResolver viewResolver;

	// TODO
	private IActionDispatcher actionDispatcher;

	/**
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() {

		/*
		 * obté el package de actions i executa l'scanner de mètodes anotats
		 */
		final String actionBasePackage = getInitParameter(ConfigConstants.ACTIONS_BASE_PACKAGE_SERVLET_PARAMETER);
		final Map<String, DeclaredAction> definedActions = new ActionScanner().doScan(actionBasePackage);

		LOG.info("scanning actions package: " + actionBasePackage);
		for (final String key : definedActions.keySet()) {
			LOG.finest("  /", key, ".do ==> ", definedActions.get(key).actionClass, ".",
					definedActions.get(key).actionMethod.getName(), "(...)");
		}
		LOG.info("found ", definedActions.size(), " defined actions.");

		this.actionDispatcher = new ActionDispatcher(definedActions);

		/*
		 * carrega el resolutor de vistes
		 */
		viewResolver = new ViewResolver(this);

		LOG.info("GroController servlet dispatcher initialized OK");
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

	protected void doAction(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		// determina el nom identificador d'action demanada
		// /jou.do ==> jou
		final String requestServletPath = request.getServletPath();
		LOG.finest("dispatching action request: ", requestServletPath);

		String redirectResource = actionDispatcher.dispatcher(request, response, requestServletPath);

		// resol la vista a la que redireccionar
		redirectResource = this.viewResolver.resolve(redirectResource);

		// redirecciona a vista
		getServletConfig().getServletContext().getRequestDispatcher(redirectResource).forward(request,
				response);
		// response.sendRedirect(request.getContextPath() + redirectResource);
	}

}
