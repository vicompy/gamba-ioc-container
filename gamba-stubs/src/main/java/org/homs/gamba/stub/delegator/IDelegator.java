package org.homs.gamba.stub.delegator;

public interface IDelegator {
	Object delegates(Object... args) throws Throwable;
}
