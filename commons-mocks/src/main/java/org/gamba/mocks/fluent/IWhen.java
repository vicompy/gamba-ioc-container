package org.gamba.mocks.fluent;

public interface IWhen {
	<T> T when(final T proxy);
	<T> T when(final T proxy, final Mask mask);
}