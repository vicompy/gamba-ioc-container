package org.homs.gamba.stub;


/**
 * entitat que permet la declaració fluent de registre de crides.
 *
 * @author mhoms
 * @param <T>
 */
public class Stub<T> {

	private final T proxiedStub;

	public Stub(final T proxiedStub) {
		this.proxiedStub = proxiedStub;
	}

	/**
	 * especifica un valor de retorn
	 *
	 * @param r valor de retorn
	 * @return el proxy
	 */
	public T returning(final Object r) {
		((IStubable) proxiedStub).setReturnValue(r);
		return proxiedStub;
	}

	/**
	 * configura el proxy en mode simulació, deixant de registrar
	 *
	 * @return el proxy
	 */
	public T play() {
		((IStubable) proxiedStub).stopRecording();
		return proxiedStub;
	}

}
