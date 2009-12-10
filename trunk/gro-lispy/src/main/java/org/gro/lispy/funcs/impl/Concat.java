package org.gro.lispy.funcs.impl;

import java.util.List;

import org.gro.lispy.funcs.AggregateFunction;
import org.gro.lispy.funcs.Function;
import org.gro.lispy.funcs.Rare;
import org.gro.lispy.tokenizer.Node;

public class Concat extends Rare { // TODO s'ha d'extendre de LiteralAggregates???

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
		return new AggregateFunction() {

			@Override
			protected Node getNeutre() {
				return new Node(-1, "");
			}

			@Override
			protected Node evalPair(final Node current, final Node next) {
				return new Node(current.value.toString() + next.value.toString(), next.line);
			}

			@Override
			protected void checkTypes(final Node funNode, final List<Node> args) {
			}
		};
	}

	@Override
	protected Integer getRequiredNumArgs() {
		return null;
	}
}