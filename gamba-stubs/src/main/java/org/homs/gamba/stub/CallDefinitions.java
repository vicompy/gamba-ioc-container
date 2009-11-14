package org.homs.gamba.stub;

import java.util.ArrayList;
import java.util.List;

import org.homs.gamba.stub.exception.GambaStubsException;

class CallDefinitions {

	private final List<CallActionConfig> callsConfig;

	public CallDefinitions() {
		callsConfig = new ArrayList<CallActionConfig>();
	}

	public void add(final CallActionConfig cac) {
		callsConfig.add(cac);
	}

	public CallActionConfig getLast() {
		return callsConfig.get(callsConfig.size() - 1);
	}

	public List<CallActionConfig> getCallsConfig() {
		return callsConfig;
	}

	/**
	 * verifica que no s'hagi registrat un delegator de retorn sense haver
	 * registrat la crida
	 */
	public void checkRegisteredCalls() {
		for (final CallActionConfig ce : callsConfig) {
			if (ce.getMethod() == null) {
				throw new GambaStubsException("method call partially defined found, with returning value: "
						+ ce.getDelegator());
			}
		}

		// TODO comprovar també que no hi hagin definicions de crides repetides
	}
}
