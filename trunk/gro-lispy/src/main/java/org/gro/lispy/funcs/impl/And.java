package org.gro.lispy.funcs.impl;

import org.gro.lispy.funcs.Function;
import org.gro.lispy.funcs.NumericAggregates;
import org.gro.lispy.funcs.Rare;
import org.gro.lispy.tokenizer.Node;

public class And extends Rare {

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
			@Override
			protected Node getNeutre() {
				return new Node(1L, -1);
			}

			@Override
			protected Node evalPair(final Node current, final Node next) {
				if (current.value instanceof Double || next.value instanceof Double) {
					final boolean result = ((Number) current.value).doubleValue() > 0 && ((Number) next.value).doubleValue()> 0;
					return new Node(result ? 1L : 0L, next.line) ;
				} else {
					final boolean result = ((Number) current.value).longValue() > 0 && ((Number) next.value).longValue() > 0;
					return new Node(result ? 1L : 0L,
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
