package org.gamba.mocks.recordingproxy;

import java.lang.reflect.Method;
import java.util.List;

import org.gamba.mocks.sequences.ISequence;


/**
 * defineix mètodes addicionals al proxy de qualsevol mock, dedicats a la
 * configuració del mateix.
 *
 * @author mhoms
 */
public interface IMockProxyLogic {
	// thenReturn(1,2,3).when(p).mul(anyInt());

	void setSeq(ISequence sequence); // obre transacció i setteja la sequencia
	void addMaskValue(Boolean value); // opcional; valors de màscara d'arguments
	void commit(final Method method, final Object[] arguments);	// la crida a mètode proxejat tanca la transacció
	List<MethodConfig> getCallConfig(); // retorna les configuracions commitejades

	void replay();
	void verify();

}
