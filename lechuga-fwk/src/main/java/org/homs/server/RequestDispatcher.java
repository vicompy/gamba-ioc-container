package org.homs.server;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.homs.binding.CatchedBeanBinder;
import org.homs.binding.IBinder;
import org.homs.test1.actions.B;

public class RequestDispatcher {

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
		// formBeanMapping.put("/final",
		// "org.homs.test1.actions.FinalAction#execute");
	}

	public String dispatch(final String requestUri, final String requestParametersExpression)
			throws Exception {
		final ResolvedAction ra = resolve(requestUri);

		Object resolvedFormBean = null;
		if (requestParametersExpression != null) {
			final Map<String, Object> atrs = parseRequestParametersExpression(requestParametersExpression);
			final String resolvedFormBeanClassName = formBeanMapping.get(requestUri);

			if (resolvedFormBeanClassName != null) {
				final Class<?> resolvedFormBeanClass = Class.forName(resolvedFormBeanClassName);
				resolvedFormBean = this.bm.doBind(resolvedFormBeanClass, atrs);
			}
		}
		System.out.println("invoke: " + ra.getActionInstance().getClass() + "#"
				+ ra.getActionMethod().getName());

		if (resolvedFormBean != null) {
			return (String) ra.getActionMethod().invoke(ra.getActionInstance(),
					new Object[] { resolvedFormBean });

		} else {
			return (String) ra.getActionMethod().invoke(ra.getActionInstance(), new Object[] {});
		}
	}

	private ResolvedAction resolve(final String requestUri) throws Exception {
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

	private Map<String, Object> parseRequestParametersExpression(final String requestParametersExpression) {
		final Map<String, Object> r = new HashMap<String, Object>();

		// name=hjk&age=fgk887

		final String[] parts = requestParametersExpression.split("&");
		for (final String part : parts) {
			final String[] pair = part.split("=");
			r.put(pair[0], pair[1]);
		}

		return r;
	}
}
