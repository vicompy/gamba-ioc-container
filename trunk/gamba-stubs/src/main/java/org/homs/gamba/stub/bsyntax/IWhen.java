package org.homs.gamba.stub.bsyntax;

public interface IWhen {
	public <T> T when(final T proxy, final ForAny...forAnies);
}