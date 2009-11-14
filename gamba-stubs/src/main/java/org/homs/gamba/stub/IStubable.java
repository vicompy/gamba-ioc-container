package org.homs.gamba.stub;

import org.homs.gamba.stub.bsyntax.Mask;
import org.homs.gamba.stub.delegator.IDelegator;

/**
 * defineix mètodes addicionals al proxy de qualsevol stub, dedicats a la
 * configuració del mateix.
 *
 * @author mhoms
 */
public interface IStubable {

	void setDelegator(IDelegator delegator, Mask mask);

	String obtainCallConfig();

	CallingLog obtainCallingLog();

	/**
	 * deixa de registrar les crides, i passa a actuar com a stub que és;
	 * retornant els valors registrats
	 */
	void stopRecording();

}
