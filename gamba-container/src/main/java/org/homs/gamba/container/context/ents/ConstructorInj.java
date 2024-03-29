package org.homs.gamba.container.context.ents;

import java.lang.reflect.Constructor;

/**
 * codifica la totalitat de la definició d'una injecció per constructor
 *
 * @author mhoms
 */
public class ConstructorInj {

	/**
	 * llista de dependències
	 */
	public final InjectableElement[] injElems;

	/**
	 * depenent dels tipus (Class) de les dependències, es resol el constructor
	 * que encaixi per tipus.
	 */
	public final Constructor<?> constructor;

	public ConstructorInj(final InjectableElement[] injElems, final Constructor<?> constructor) {
		super();
		this.injElems = injElems.clone();
		this.constructor = constructor;
	}

//	/**
//	 * retorna una representació de l'estat de l'objecte
//	 *
//	 * @see java.lang.Object#toString()
//	 */
//	@Override
//	public String toString() {
//		final StringBuffer strb = new StringBuffer();
//		for (int i = 0; i < injElems.length; i++) {
//			strb.append("   <constr-arg ");
//			strb.append(injElems[i].toString());
//			strb.append(" />\n");
//		}
//		return strb.toString();
//	} TODO mostrar en XML!

}
