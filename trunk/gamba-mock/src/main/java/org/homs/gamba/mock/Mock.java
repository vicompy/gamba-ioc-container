package org.homs.gamba.mock;


/**
 * entitat que permet la declaració fluent de registre de crides.
 *
 * @author mhoms
 * @param <T>
 */
public class Mock<T> {

	private final T proxiedMock;

	public Mock(final T proxiedMock) {
		this.proxiedMock = proxiedMock;
	}

	/**
	 * especifica un valor de retorn
	 *
	 * @param r valor de retorn
	 * @return el proxy
	 */
	public T returning(final Object r) {
		((IMockeable) proxiedMock).setReturnValue(r);
		return proxiedMock;
	}

	/**
	 * configura el proxy en mode simulació, deixant de registrar
	 *
	 * @return el proxy
	 */
	public T play() {
		((IMockeable) proxiedMock).stopRecording();
		return proxiedMock;
	}

}
