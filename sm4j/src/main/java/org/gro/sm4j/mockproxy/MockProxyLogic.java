package org.gro.sm4j.mockproxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.gro.sm4j.exception.SimpleMockingException;
import org.gro.sm4j.sequences.ISequence;

/**
 * construeix la configuració de crides {@link MethodConfig} de forma
 * transaccional, degut a la naturalesa fluent de les operacions, que segueix el contracte:
 *
 * <ul>
 * 		<li>1.- es defineix la seqüència, implementació de <tt>ISequence</tt>.</li>
 * 		<li>2.- opcionalment, s'afegeixen booleans que enmascaren els arguments de la futura crida.</li>
 * 		<li>3.- es commiteja tot, amb la informació del mètode i del valors dels arguments.</li>
 * </ul>
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
		final MethodConfig methodConf = new MethodConfig(sequence, argMask.toArray(new Boolean[argMask.size()]),
				method, arguments);
		this.methodList.add(methodConf);
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
		for (final MethodConfig mc : methodList) {
			if (!mc.getSequence().checkIfFinished()) {
				throw new SimpleMockingException("unsatisfied expectation: " + mc.getSequence().toString());
			}
		}
	}

}
