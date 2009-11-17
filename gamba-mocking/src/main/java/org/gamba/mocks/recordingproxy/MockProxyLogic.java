package org.gamba.mocks.recordingproxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.gamba.mocks.exception.GambaMockException;
import org.gamba.mocks.sequences.ISequence;

/**
 * gestiona les transaccions de configuració de crides
 *
 * @author mhoms
 */
class MockProxyLogic implements IMockProxyLogic {

	private final List<MethodConfig> methodList;

	// info abans de commitejar
	private ISequence sequence;
	private final List<Boolean> argMask;

	public MockProxyLogic() {
		this.methodList = new ArrayList<MethodConfig>();
		this.argMask = new ArrayList<Boolean>();
	}

	public void setSeq(final ISequence sequence) {
		this.sequence = sequence;
		this.argMask.clear();
	}

	public void addMaskValue(final Boolean value) {
		this.argMask.add(value);
	}

	public void commit(final Method method, final Object[] arguments) {
		final MethodConfig mc = new MethodConfig(sequence, argMask.toArray(new Boolean[argMask.size()]), method,
				arguments);
		this.methodList.add(mc);
	}

	public List<MethodConfig> getCallConfig() {
		return this.methodList;
	}

	public void replay() {
		for (final MethodConfig mc : methodList) {
			mc.getSequence().reset();
		}
	}

	public void verify() {
		for (final MethodConfig mc :  methodList) {
			if (!mc.getSequence().checkIfFinished()) {
				throw new GambaMockException("unsatisfied expectation: " + mc.getSequence().toString());
			}
		}
	}

}