package org.homs.server;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.homs.binding.CatchedBeanBinder;
import org.homs.binding.IBinder;

class RequestDispatcher {

	private static final int ACTION_METHOD_NAME_INDEX = 1;
	private static final int ACTION_CLASS_NAME_INDEX = 0;
	private final Map<String, String> actionMapping;
	private final Map<String, String> formBeanMapping;

	private final IBinder bm;

	public RequestDispatcher() {
		this.actionMapping = new HashMap<String, String>();
		this.formBeanMapping = new HashMap<String, String>();
		this.bm = new CatchedBeanBinder();

		actionMapping.put("/inici", "org.homs.test1.actions.IniciAction#execute");
		actionMapping.put("/final", "org.homs.test1.actions.FinalAction#execute");

		formBeanMapping.put("/final", "org.homs.test1.actions.B");
	}

	/**
	 * despacha una petició segons els mappings d'Action i de FormBeans;
	 * instancia la Action correcta, i li passa el FormBean definit amb els
	 * paràmetres bindats.
	 *
	 * @param requestUri
	 * @param requestParametersExpression
	 * @return
	 * @throws Exception
	 */
	public String dispatch(final String requestUri, final String requestParametersExpression)
			throws Exception {

		/*
		 * determina la instància i mètode a executar com a Action
		 */
		final ResolvedAction ra = resolveAction(requestUri);

		/*
		 * si està declarat, instancia el FormBean a utilitzar, i li fa el
		 * binding amb els paràmetres passats de form HTML
		 */
		final Object resolvedFormBean = resolveFormBean(requestUri, requestParametersExpression);

		/*
		 * invoca la action
		 */
		if (resolvedFormBean != null) {
			return (String) ra.getActionMethod().invoke(ra.getActionInstance(),
					new Object[] { resolvedFormBean });
		} else {
			return (String) ra.getActionMethod().invoke(ra.getActionInstance(), new Object[] {});
		}
	}

	private Object resolveFormBean(final String requestUri, final String requestParametersExpression)
			throws ClassNotFoundException {
		Object resolvedFormBean = null;
		if (requestParametersExpression != null) {
			final Map<String, Object> atrs = parseRequestParametersExpression(requestParametersExpression);
			final String resolvedFormBeanClassName = formBeanMapping.get(requestUri);

			if (resolvedFormBeanClassName != null) {
				final Class<?> resolvedFormBeanClass = Class.forName(resolvedFormBeanClassName);
				resolvedFormBean = this.bm.doBind(resolvedFormBeanClass, atrs);
			}
		}
		return resolvedFormBean;
	}

	private ResolvedAction resolveAction(final String requestUri) throws Exception {
		final String[] parts = this.actionMapping.get(requestUri).split("#");

		final Class<?> actionClass = Class.forName(parts[ACTION_CLASS_NAME_INDEX]);
		final Object actionInstance = actionClass.newInstance();

		Method actionMethod = null;
		for (final Method m : actionClass.getMethods()) {
			if (m.getName().equals(parts[ACTION_METHOD_NAME_INDEX])) {
				actionMethod = m;
				break;
			}
		}

		return new ResolvedAction(actionInstance, actionMethod);
	}

	/**
	 * parseja una expressió <tt>name=hjk&age=fgk887</tt> ó
	 * <tt>name=asdf&age=4&members=2&members=3</tt>; ve igualment formatada de
	 * GET com de POST
	 *
	 * @param requestParametersExpression
	 * @return
	 */
	private Map<String, Object> parseRequestParametersExpression(final String requestParametersExpression) {
		final Map<String, Object> r = new HashMap<String, Object>();

		// TODO falta parsejar name=asdf&age=4&members=2&members=3

		final String[] parts = requestParametersExpression.split("&");
		for (final String part : parts) {
			final String[] pair = part.split("=");
			r.put(pair[0], pair[1]);
		}

		return r;
	}
}
