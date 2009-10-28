package org.homs.gamba.stub;

import java.util.List;

import org.homs.gamba.stub.delegator.IDelegator;

/**
 * defineix mètodes addicionals al proxy de qualsevol stub, dedicats a la
 * configuració del mateix.
 *
 * @author mhoms
 */
public interface IStubable {

	void setDelegator(IDelegator delegator);

	/**
	 * deixa de registrar les crides, i passa a actuar com a stub que és;
	 * retornant els valors registrats
	 */
	void stopRecording();

	List<CalledRegister> obtainReport();

}
