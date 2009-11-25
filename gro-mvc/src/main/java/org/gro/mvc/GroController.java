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

	private static final GroLog log = GroLog.getGroLogger(GroController.class).config();

	/**
	 * nom del paràmetre de servlet definit en <tt>web.xml</tt>, que servirà per
	 * scannejar el package que conté les accions de l'aplicació.
	 */
	private static final String ACTIONS_BASE_PACKAGE = "actions-base-package";

	/**
	 * la llargada en caràcters de l'extensió de les crides a Action, ha de
	 * concordar en longitud amb el mapping de servlet fet en <tt>web.xml</tt>.
	 */
	private static final int ACTION_EXTENSION_LENGTH = ".do".length();

	/**
	 * resolutor de vistes TODO
	 */
	private IViewResolver viewResolver;

	// TODO
	private IActionDispatcher actionDispatcher;

	// /**
	// * @see HttpServlet#HttpServlet()
	// */
	// public GroController() {
	// super();
	// }

	/**
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() {

		/*
		 * obté el package de actions i executa l'scanner de mètodes anotats
		 */
		final String actionBasePackage = getInitParameter(ACTIONS_BASE_PACKAGE);
		final Map<String, DeclaredAction> definedActions = new ActionScanner().doScan(actionBasePackage);

		log.info("scanning actions package: " + actionBasePackage);
		for (final String key : definedActions.keySet()) {
			log.fine("  /", key, ".do as ", definedActions.get(key).actionClass, ".",
					definedActions.get(key).actionMethod.getName(), "(...)");
		}
		log.info("found ", definedActions.size(), " defined actions.");

		this.actionDispatcher = new ActionDispatcher(definedActions);

		/*
		 * carrega el resolutor de vistes
		 */
		viewResolver = new ViewResolver(this);

		log.info("GroController servlet dispatcher initialized OK");
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
		log.fine("dispatching action request: ", requestServletPath);

		final String actionName = requestServletPath.substring(1, requestServletPath.length()
				- ACTION_EXTENSION_LENGTH);

		String redirectResource = actionDispatcher.dispatcher(request, response, actionName);

		// resol la vista a la que redireccionar
		redirectResource = this.viewResolver.resolve(redirectResource);

		// redirecciona a vista
		getServletConfig().getServletContext().getRequestDispatcher(redirectResource).forward(request,
				response);
	}

}
