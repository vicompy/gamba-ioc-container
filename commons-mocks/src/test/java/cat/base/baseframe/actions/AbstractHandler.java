package cat.base.baseframe.actions;

import java.io.Serializable;

import org.springframework.webflow.execution.RequestContext;



/**
 * encapsulat abstracte, per a la càrrega inicial i manteniment d'instàncies que
 * es deixaràn en flowScope. És utilitzat en tots els Handlers, per a mantenir
 * en flowScope els backingBeans, i instàncies de desplegables. <br>
 * <br>
 * Tots els handlers de vista poden extendre d'aquesta classe, convertint-se en
 * Serialitzables.
 *
 * @author mhoms
 */
public abstract class AbstractHandler implements Serializable {

	private static final long serialVersionUID = -8764652088361661845L;

	/**
	 * aquest mètode retorna una mateixa instància del tipus demanat, assegurant
	 * que es troba desada en flowScope.
	 * <ul>
	 * <li>si la instància no es troba en flowScope, la crea, la desa i la
	 * retorna.</li>
	 * <li>si la instància ja es troba en flowScope, l'obté i la retorna.</li>
	 * </ul>
	 * Realitza una instància i la carrega en <b>flowScope</b>, si no hi és ja
	 * present. De totes formes, en retorna una referència després d'assegurar
	 * que també està present en flowScope.
	 *
	 * @param context context de SWF
	 * @param scopeId nom d'entrada de flowScope sota el que es guardarà la
	 *        instància
	 * @param backBeanClass classe de l'objecte
	 * @return la classe realitzada
	 */
	protected Object loadBackBean(final RequestContext context, final String scopeId, final Class<?> backBeanClass)
			throws RuntimeException {

		if (context.getFlowScope().get(scopeId) == null) {
			Object backBean = null;
			try {
				backBean = backBeanClass.newInstance();
			} catch (final InstantiationException e) {
				throw new RuntimeException("no es pot instanciar la classe: " + backBeanClass.getName(), e);
			} catch (final IllegalAccessException e) {
				throw new RuntimeException("no es pot instanciar la classe: " + backBeanClass.getName()
						+ "\ndegut a falta de visibilitat. És la classe pública?", e);
			}
			context.getFlowScope().put(scopeId, backBean);
			return backBean;
		} else {
			return context.getFlowScope().get(scopeId);
		}
	}

}
