package org.homs.gamba.mock;

/**
 * defineix mètodes addicionals al proxy de qualsevol mock, dedicats a la
 * configuració del mateix.
 *
 * @author mhoms
 */
interface IMockeable {

	/**
	 * defineix el valor del retorn desitjat per al pròxim registre de crida.
	 * @param r valor esperat del retorn de la propera crida que es faci al mock
	 */
	public void setReturnValue(Object r);

	/**
	 * deixa de registrar les crides, i passa a actuar com a mock que és;
	 * retornant els valors registrats
	 */
	public void stopRecording();
}
