package org.gro.lispy.funcs.impl;

import org.gro.lispy.funcs.Function;
import org.gro.lispy.funcs.NumericAggregates;
import org.gro.lispy.funcs.Rare;
import org.gro.lispy.tokenizer.Node;

public class Sub extends Rare {

	@Override
	public boolean[] getEvalDefined() {
		return null;
	}

	@Override
	public ArgEvalMode getEvaluateMode() {
		return Rare.ArgEvalMode.ALL;
	}

	@Override
	public Function getEvaluator() {
		return new NumericAggregates() {

			private int c = 0;

			@Override
			protected Node getNeutre() {
				return new Node(0L, -1);
			}

			@Override
			protected Node evalPair(final Node current, final Node next) {
				if (c == 0) {
					c++;
					return next;
				}

				if (current.value instanceof Double || next.value instanceof Double) {
					return new Node(((Number) current.value).doubleValue()
							- ((Number) next.value).doubleValue(), next.line);
				} else {
					return new Node(((Number) current.value).longValue() - ((Number) next.value).longValue(),
							next.line);
				}
			}
		};
	}

	@Override
	protected Integer getRequiredNumArgs() {
		return null;
	}

}
