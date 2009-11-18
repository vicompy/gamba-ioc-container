package org.gro.sm4j.mockproxy;

import java.lang.reflect.Method;
import java.util.List;

import org.gro.sm4j.sequences.ISequence;

/**
 * agrupa els mètodes propis de la lògica del proxy; configuració de crides, i
 * de les operacions a fer sobre les registrades. degut a la definició fluent de
 * les expectacions, la configuració de les crides es fa a trossos, i la
 * implementació d'aquesta interfície haurà d'implementar un control
 * transaccional de cada operació. Naturalment no cal que sigui thread-safe.
 *
 * @author mhoms
 */
public interface IMockProxyLogic {

	/**
	 * obre transacció i setteja la sequencia
	 *
	 * @param sequence
	 */
	void setSeq(ISequence sequence);

	/**
	 * opcional i a repetir: afegeix valors de màscara d'arguments
	 *
	 * @param value
	 */
	void addMaskValue(Boolean value);

	/**
	 * tanca la transacció, passant els paràmetres de la crida
	 *
	 * @param method
	 * @param arguments
	 */
	void commit(final Method method, final Object[] arguments);

	/**
	 * retorna les configuracions commitejades fins al moment
	 *
	 * @return
	 */
	List<MethodConfig> getCallConfig();

	/**
	 * reinicialitza l'estat del Mock i de les seqüències
	 */
	void replay();

	/**
	 * verifica que totes les seqüencies estiguin en el seu estat final
	 */
	void verify();
}
