package org.homs.gamba.mock;

import org.homs.gamba.mock.proxy.IMockeable;

public class Mock<T> {

	private final T proxiedMock;

	public Mock(final T proxiedMock) {
		this.proxiedMock = proxiedMock;
	}

	public T returning(final Object r) {
		((IMockeable) proxiedMock).setReturnValue(r);
		return proxiedMock;
	}

	public T play() {
		((IMockeable) proxiedMock).stopRecording();
		return proxiedMock;
	}

}
