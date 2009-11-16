package org.gamba.mocks;

import org.gamba.mocks.fluent.Mask;
import org.gamba.mocks.sequences.ISequence;

/**
 * defineix mètodes addicionals al proxy de qualsevol mock, dedicats a la
 * configuració del mateix.
 *
 * @author mhoms
 */
public interface IMockable {

	void setSequence(ISequence sequence, Mask mask);

	void stopRecording();

	void verify();

	String obtainCallConfig();

	CallingLog obtainCallingLog();
}
