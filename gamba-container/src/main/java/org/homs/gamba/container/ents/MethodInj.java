package org.homs.gamba.container.ents;

import java.lang.reflect.Method;

/**
 * codifica la totalitat de la definició d'una injecció per mètode (setter)
 *
 * @author mhoms
 */
public class MethodInj {

	/**
	 * mètode a utilitzar per a la injecció, que encaixa segons el tipus a
	 * injectar.
	 */
	public final Method method;
	/**
	 * dependència a injectar
	 */
	public final InjectableElement injElem;

	public MethodInj(final Method method, final InjectableElement injElem) {
		super();
		this.method = method;
		this.injElem = injElem;
	}

	// /**
	// * retorna una representació de l'estat actual de l'objecte
	// *
	// * @see java.lang.Object#toString()
	// */
	// @Override
	// public String toString() {
	// return " :" + method.getName() + " <- " + injElem.toString();
	// }

}
