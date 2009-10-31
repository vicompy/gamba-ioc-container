package org.homs.gamba.stub.delegator;



public class ConstantValue extends AbstractDelegator {

	public ConstantValue(final Object single) {
		super(single);
	}

	public Object delegates(final Object... args) {
		return super.sequence[0];
	}

}
