package org.lechuga.mvc.dispatcher;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gro.logging.GroLog;
import org.lechuga.mvc.binding.BeanBinder;
import org.lechuga.mvc.binding.IBeanBinder;
import org.lechuga.mvc.scan.ClassMethod;
import org.lechuga.mvc.validation.Validated;
import org.lechuga.mvc.validation.Validator;


public class Dispatcher {

	private final static GroLog LOG = GroLog.getGroLogger(Dispatcher.class);

	private final Map<String, ClassMethod> controllerMappingsMap;

	/**
	 * objecte mapejador de paràmetres HTTP a beans de formulari.
	 */
	private final IBeanBinder httpBinder;

	public Dispatcher(final Map<String, ClassMethod> mappingsMap) {
		super();
		this.controllerMappingsMap = mappingsMap;
		httpBinder = new BeanBinder();
	}

	@SuppressWarnings("unchecked")
	protected String generalAction(final HttpServletRequest request, final HttpServletResponse response) {

		//
		// obté la classe i mètode a executar, de mappings i a partir de
		// <tt>request.getServletPath()</tt>
		//
		final ClassMethod classMethod = controllerMappingsMap.get(request.getServletPath());

		if (classMethod == null) {
			throw new RuntimeException("requested controller not found in mappings list: "
					+ request.getServletPath());
		}

		LOG.fine("requested controller: ", classMethod);

		//
		// instancia el Controller
		//
		final Object controller;
		try {
			controller = classMethod.controller.newInstance();
		} catch (final Exception e) {
			throw new RuntimeException("controller object not instantiable: "
					+ classMethod.controller.getName(), e);
		}

		//
		// obté la classe del FormBean, de mappings i a partir del nom curt del
		// Controller (UserController ==> UserForm)
		// <tt>request.getServletPath()</tt>
		//
		/**
		 * els mètodes action tenen per arguments request,response i
		 * opcionalment, un beanform. Declarar un beanform (com a tercer
		 * argument opcional) dóna lloc a un binding abans d'invocar aquest
		 * mètode, passant el beanform bindat com a argument.
		 */
//		final Object formBean;
//		final Bind bindAnnotation = classMethod.method.getAnnotation(Bind.class);
//		final Class<?> formBeanClass = bindAnnotation.formBean();
//		if (bindAnnotation == null) {
//			formBean = null;
//			LOG.fine("requested formbean: none");
//		} else {
//			//
//			// construeix el bean de formulari i el rellena dels paràmetres HTTP
//			//
//			LOG.fine("requested formbean: ", classMethod.controllerShortName, " => ", formBeanClass);
//
//			//
//			// etapa de validació de paràmetres Http, abans del binding
//			//
//			final Validated validated = classMethod.method.getAnnotation(Validated.class);
//			if (validated != null) {
//				// es demana validació
//				final Class<? extends Validator> validatorClass = validated.by();
//				Validator validator;
//				try {
//					validator = validatorClass.newInstance();
//				} catch (final Exception exc) {
//					throw new RuntimeException("error instanciant el validador: " + validatorClass.getName(), exc);
//				}
//				final Map<String,String> errorMessages = validator.publicValidate(request.getParameterMap());
//				if (errorMessages != null) {
//					request.setAttribute("validationErrorMap", errorMessages);
//					return validated.onError();
//				}
//			}
//
//			formBean = this.httpBinder.doBind(formBeanClass, request.getParameterMap());
//
//		}

		final Class<?>[] argClasses = classMethod.method.getParameterTypes();
		final Object formBean;
		if (argClasses.length != 2) {
			formBean = null;
			LOG.fine("requested formbean: none");
		} else {
			//
			// construeix el bean de formulari i el rellena dels paràmetres HTTP
			//
			final Class<?> formBeanClass = argClasses[1];
			LOG.fine("requested formbean: ", classMethod.controllerShortName, " => ", formBeanClass);

			//
			// etapa de validació de paràmetres Http, abans del binding
			//
			final Validated validated = classMethod.method.getAnnotation(Validated.class);
			if (validated != null) {
				// es demana validació
				final Class<? extends Validator> validatorClass = validated.by();
				Validator validator;
				try {
					validator = validatorClass.newInstance();
				} catch (final Exception exc) {
					throw new RuntimeException("error instanciant el validador: " + validatorClass.getName(), exc);
				}
				final Map<String,String> errorMessages = validator.publicValidate(request.getParameterMap());
				if (errorMessages != null) {
					request.setAttribute("validationErrorMap", errorMessages);
					return validated.onError();
				}
			}

			formBean = this.httpBinder.doBind(formBeanClass, request.getParameterMap());
		}

		//
		// invoca el mètode
		//
		String forwardResourceName;
		try {
			if (formBean == null) {
				forwardResourceName = (String) classMethod.method.invoke(controller, new RequestContext(request, response));
			} else {
				forwardResourceName = (String) classMethod.method.invoke(controller, new RequestContext(request, response),
						formBean);
			}
		} catch (final Exception e) {
			throw new RuntimeException(
					"error invoking: " + classMethod.controller + "." + classMethod.method, e);
		}

		request.setAttribute(ConfigConstants.FORMBEAN_ATTRIBUTE_NAME, formBean);

		return forwardResourceName;
	}

}
