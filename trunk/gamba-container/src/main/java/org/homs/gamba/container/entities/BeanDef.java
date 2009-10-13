package org.homs.gamba.container.entities;

/**
 * codifica la totalitat de la definició d'un bean
 *
 * @author mhoms
 */
public class BeanDef {

	public final String beanId;
	public final boolean isSingleton;
	public final Class<?> beanClass;
	public final ConstructorInj constructorInj;
	public final MethodInj[] methodInj;

	private Object singletonInstance = null;

	/**
	 * @param beanId si <tt>null</tt> és que és una definició anònima, i
	 *        s'assigna a <tt>"inner-anonymous"</tt>.
	 * @param isSingleton indica si aquest objecte s'ha d'instanciar de forma
	 *        única
	 * @param beanClass Class resolta del bean
	 * @param constructorInj estructura que codifica les injeccions per
	 *        constructor
	 * @param methodInj estructura que codifica les injeccions per mètode
	 */
	public BeanDef(final String beanId, final boolean isSingleton, final Class<?> beanClass,
			final ConstructorInj constructorInj, final MethodInj[] methodInj) {
		super();
		this.beanId = beanId == null ? "inner-anonymous" : beanId;
		this.isSingleton = isSingleton;
		this.beanClass = beanClass;
		this.constructorInj = constructorInj;
		this.methodInj = methodInj == null ? null : methodInj.clone();
	}

	public Object getSingletonInstance() {
		return singletonInstance;
	}

	public void setSingletonInstance(final Object singletonInstance) {
		this.singletonInstance = singletonInstance;
	}

	/**
	 * retorna la representació de l'estat d'aquest objecte
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuffer strb = new StringBuffer();
		strb.append(beanId);
		strb.append(" = ");
		if (isSingleton) {
			strb.append('~');
		}
		strb.append(beanClass.getName().toString());
		if (constructorInj != null) {
			strb.append(constructorInj.toString());
		}
		if (methodInj != null) {
			for (int i = 0; i < methodInj.length; i++) {
				strb.append(methodInj[i].toString());
			}
		}
		strb.append(';');
		return strb.toString();
	}

}
