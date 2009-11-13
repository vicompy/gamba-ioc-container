package org.homs.gamba.stub.bsyntax;

public interface IWhen {
	public <T> T when(final T proxy);
	public <T> T when(final T proxy, final Mask mask);
}