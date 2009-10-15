package org.homs.gamba.container.context;

import java.lang.reflect.Constructor;
import java.util.Map;

import org.homs.gamba.container.ents.BeanDef;
import org.homs.gamba.container.ents.InjectableElement;
import org.homs.gamba.container.ents.MethodInj;
import org.homs.gamba.container.ents.InjectableElement.EInjType;
import org.homs.gamba.container.exception.GambaConfigurationException;
import org.homs.gamba.container.exception.GambaException;
import org.homs.gamba.container.processor.ContextProcessor;
import org.homs.gamba.container.xmlparser.GambaSaxParser;

/**
 * Crea, manté i gestiona el context de definicions de beans, extretes d'un
 * fitxer de properties, amb capacitat per a construir els beans demanats.
 *
 * @author mhoms
 */
public class GambaContext {

	/**
	 * Hash que associa cada identificador de bean amb l'estructura que codifica
	 * la seva definició de construcció i resolució de dependències.
	 */
	private final Map<String, BeanDef> hashBeanDefs;

	/**
	 * Construeix l'arbre de configuracions, parsejant de fitxer properties, i
	 * mantenint-lo en una hash. Delega en {@see PropertiesParser}.
	 *
	 * @param xmlFileName properties file location path
	 * @throws GambaException si surt algun error de parsing
	 */
	public GambaContext(final String xmlFileName) throws GambaException {
		final GambaSaxParser sp = new GambaSaxParser(xmlFileName);
		this.hashBeanDefs = new ContextProcessor(sp.getDefs()).translate();
	}

	/**
	 * obté la corresponent definició de bean de hash, i en resol la
	 * construcció, tot injectant-li les dependències declarades.
	 *
	 * @param beanId identifier to a bean definition
	 * @return the requested object instance
	 * @throws GambaException si l'identificador del bean no es troba en hash
	 */
	public Object getBean(final String beanId) throws GambaException {
		final BeanDef beanDef = hashBeanDefs.get(beanId);
		if (beanDef == null) {
			throw new GambaConfigurationException("bean identifier not found: " + beanId);
		}
		return obtainBean(beanDef);
	}

	/**
	 * construeix el bean segons la seva definició, amb les dependències
	 * declarades injectades.
	 *
	 * @param beanDef definició del bean a construir
	 * @return la instància de bean demanada
	 * @throws GambaException si error reflexiu instanciant el bean, o bé
	 *         injectant, ja sigui per mètode o per construtor.
	 */
	private Object obtainBean(final BeanDef beanDef) throws GambaException {

		// el bean demanat és singleton, i ja està construit
		if (beanDef.isSingleton && beanDef.getSingletonInstance() != null) {
			return beanDef.getSingletonInstance();
		}

		final Object beanInstance = getBeanInstance(beanDef);
		dependencyInjection(beanDef, beanInstance);

		return beanInstance;
	}

	/**
	 * construeix un bean, ja sigui per construcció per defecte, com per
	 * injecció.
	 *
	 * @param beanDef la definició de bean demanada
	 * @return la instpància de bean demanat, faltant la injecció de
	 *         dependències de mètode (setter...)
	 * @throws GambaException si error de reflexió construint la instància
	 */
	private Object getBeanInstance(final BeanDef beanDef) throws GambaException {

		Object beanInstance = null;

		if (beanDef.constructorInj == null) {
			// construcció per defecte
			try {
				beanInstance = beanDef.beanClass.newInstance();
			} catch (final InstantiationException e) {
				throw new GambaException("error instancing a bean: ", beanDef, e);
			} catch (final IllegalAccessException e) {
				throw new GambaException("error instancing a bean: ", beanDef, e);
			}
		} else {
			// construcció per dependències
			beanInstance = constructWithInjection(beanDef);
		}

		// si és singleton, al haver sigut construït, en guarda l'instància per
		// a un ús posterior
		if (beanDef.isSingleton) {
			beanDef.setSingletonInstance(beanInstance);
		}

		return beanInstance;
	}

	/**
	 * construeix un bean amb injecció per constructor
	 *
	 * @param beanDef la definició del bean demanada
	 * @return l'instància del bean, construit per dependències
	 * @throws GambaException si error cridant reflectivament al constructor
	 */
	private Object constructWithInjection(final BeanDef beanDef) throws GambaException {
		Object beanInstance = null;

		/*
		 * enllista els valors dels arguments
		 */
		final int nArgs = beanDef.constructorInj.injElems.length;
		final Object[] parameterValues = new Object[nArgs];
		for (int i = 0; i < nArgs; i++) {
			final InjectableElement injElem = beanDef.constructorInj.injElems[i];
			if (injElem.eInjType == EInjType.STRING_VALUE) {
				// l'argument és un String
				// parameterValues[i] = injElem.stringValue;
				if (injElem.type == null) {
					parameterValues[i] = injElem.stringValue;
				} else {
					parameterValues[i] = injElem.typeInstance;
				}
			} else {
				parameterValues[i] = obtainBean(injElem.beanRef);
			}

		}

		final Constructor<?> constructor = beanDef.constructorInj.constructor;

		// instancia utilitzant el constructor trobat
		try {
			beanInstance = constructor.newInstance(parameterValues);
		} catch (final Exception e) {
			throw new GambaException("error calling constructor: " + constructor, beanDef, e);
		}

		return beanInstance;
	}

	/**
	 * realitza la injecció per mètode (típicament mètodes setter)
	 *
	 * @param beanDef la definició de bean demanada
	 * @param beanInstance la instància del bean ja construïda
	 * @throws GambaException si error en crida a mètode (setter) reflectiva
	 */
	private void dependencyInjection(final BeanDef beanDef, final Object beanInstance) throws GambaException {
		// en el bean instanciat, se li fa les injeccions de dependències
		if (beanDef.methodInj == null) {
			return;
		}

		final Object[] constrArgs = new Object[1];
		for (final MethodInj methodInj : beanDef.methodInj) {

			Object refBean = null;
			if (methodInj.injElem.eInjType == EInjType.STRING_VALUE) {
				if (methodInj.injElem.type == null) {
					refBean = methodInj.injElem.stringValue;
				} else {
					refBean = methodInj.injElem.typeInstance;
				}
			} else {
				refBean = obtainBean(methodInj.injElem.beanRef);
			}

			constrArgs[0] = refBean;
			try {
				methodInj.method.invoke(beanInstance, constrArgs);
			} catch (final Exception e) {
				throw new GambaException("error injecting dependency: ", beanDef, e);
			}

		}

	}

	// /**
	// * retorna una representació en <tt>java.lang.String</tt> de l'estat
	// * d'aquest objecte.
	// *
	// * @see java.lang.Object#toString()
	// */
	// @Override
	// public String toString() {
	// final StringBuffer strb = new StringBuffer();
	// for (final String key : hashBeanDefs.keySet()) {
	// strb.append(hashBeanDefs.get(key).toString());
	// strb.append('\n');
	// }
	// return strb.toString();
	// }
}
