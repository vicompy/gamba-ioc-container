package org.gamba.mocks.recordingproxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.gamba.mocks.sequences.AbstractSequence;

/**
 * gestiona les transaccions de configuració de crides
 *
 * @author mhoms
 */
class RecordingControl implements IRecordingControl {

	private final List<MethodConfig> methodList;

	// info abans de commitejar
	private AbstractSequence sequence;
	private final List<Boolean> argMask;

	public RecordingControl() {
		this.methodList = new ArrayList<MethodConfig>();
		this.argMask = new ArrayList<Boolean>();
	}

	public void setSeq(final AbstractSequence sequence) {
		this.sequence = sequence;
		this.argMask.clear();
	}

	public void addMaskValue(final Boolean value) {
		this.argMask.add(value);
	}

	public void commit(final Method method, final Object[] arguments) {
		final MethodConfig mc = new MethodConfig(sequence, argMask.toArray(new Boolean[argMask.size()]), method,
				arguments);

		System.out.print("committed: " + mc);
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

}
