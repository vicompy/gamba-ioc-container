package org.gro.lispy.funcs.impl;

import org.gro.lispy.funcs.Evaluable;
import org.gro.lispy.funcs.NumericAggregates;
import org.gro.lispy.funcs.Rare;
import org.gro.lispy.tokenizer.Node;

public class Mul extends Rare {

	@Override
	protected boolean[] getEvalDefined() {
		return null;
	}

	@Override
	protected ArgEvalMode getEvaluateMode() {
		return Rare.ArgEvalMode.ALL;
	}

	@Override
	protected Evaluable getEvaluator() {
		return new NumericAggregates() {
			@Override
			protected Node getNeutre() {
				return new Node(1L, -1);
			}

			@Override
			protected Node evalPair(final Node current, final Node next) {
				if (current.value instanceof Double || next.value instanceof Double) {
					return new Node(((Number) current.value).doubleValue()
							* ((Number) next.value).doubleValue(), next.line);
				} else {
					return new Node(((Number) current.value).longValue() * ((Number) next.value).longValue(),
							next.line);
				}
			}
		};
	}
}
