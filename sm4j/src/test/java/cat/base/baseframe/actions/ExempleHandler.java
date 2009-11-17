package cat.base.baseframe.actions;

import org.springframework.webflow.execution.RequestContext;



/**
 * Handler d'exemple per al test unitari
 *
 * @author mhoms
 */
public class ExempleHandler extends AbstractHandler {

	private static final long serialVersionUID = 5229166835015768393L;

	/**
	 * fa visible el mètode a testar, ja que és de visibilitat protegida.
	 *
	 * @see cat.base.baseframe.actions.AbstractHandler#loadBackBean(org.springframework.webflow.execution.RequestContext, java.lang.String, java.lang.Class)
	 */
	@Override
	public Object loadBackBean(final RequestContext context, final String scopeId, final Class<?> backBeanClass)
			throws RuntimeException {
		return super.loadBackBean(context, scopeId, backBeanClass);
	}
}
