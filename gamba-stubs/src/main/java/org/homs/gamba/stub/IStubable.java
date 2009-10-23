package org.homs.gamba.stub;

/**
 * defineix mètodes addicionals al proxy de qualsevol stub, dedicats a la
 * configuració del mateix.
 *
 * @author mhoms
 */
interface IStubable {

	/**
	 * defineix el valor del retorn desitjat per al pròxim registre de crida.
	 *
	 * @param r valor esperat del retorn de la propera crida que es faci al stub
	 */
	public void setReturnValue(Object r);

	public void setThrowing(Throwable t);

	public void setDelegator(IDelegator delegator);

	/**
	 * deixa de registrar les crides, i passa a actuar com a stub que és;
	 * retornant els valors registrats
	 */
	public void stopRecording();

}
