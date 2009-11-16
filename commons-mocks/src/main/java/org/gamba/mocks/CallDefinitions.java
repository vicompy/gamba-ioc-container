package org.gamba.mocks;

import java.util.ArrayList;
import java.util.List;

import org.gamba.mocks.exception.GambaMockException;

/**
 * entitat que encapsula i gestiona la llista d'objectes {@see CallActionConfig}
 * que configuren les expectacions d'un mock
 *
 * @author mhoms
 */
class CallDefinitions {

	private final List<CallActionConfig> callsConfig;

	public CallDefinitions() {
		callsConfig = new ArrayList<CallActionConfig>();
	}

	/**
	 * afegeix la definició del comportament d'una crida
	 *
	 * @param cac
	 */
	public void add(final CallActionConfig cac) {
		callsConfig.add(cac);
	}

	/**
	 * agafa l'últim element definit, per tal de settejar-li les propietats que
	 * falten per tal de completar-lo.
	 *
	 * @return
	 */
	public CallActionConfig getLast() {
		return callsConfig.get(callsConfig.size() - 1);
	}

	public List<CallActionConfig> getCallsConfig() {
		return callsConfig;
	}

	/**
	 * verifica que no s'hagi registrat una sequence de retorn sense haver
	 * registrat la crida
	 */
	public void checkRegisteredCalls() {
		for (final CallActionConfig ce : callsConfig) {
			if (ce.getMethod() == null) {
				throw new GambaMockException("method call partially defined found, with returning value: "
						+ ce.getDelegator().toString());
			}
		}

		// TODO comprovar també que no hi hagin definicions de crides repetides
	}
}
