package org.gro.lispy.funcs.impl;

import java.util.List;

import org.gro.lispy.funcs.Function;
import org.gro.lispy.funcs.Rare;
import org.gro.lispy.tokenizer.Node;

public class Lambda extends Rare {

	@Override
	protected Integer getRequiredNumArgs() {
		return 1;
	}

	// (lambda (x => + x x))
	@Override
	public boolean[] getEvalDefined() {
		return null;
	}

	@Override
	public ArgEvalMode getEvaluateMode() {
		return Rare.ArgEvalMode.NONE;
	}

	@Override
	public Function getEvaluator() {
		return new Function() {
			@SuppressWarnings("unchecked")
			public Node eval(final Node funNode, final List<Node> args) {
				if (!(args.get(0).value instanceof List<?>)) {
					throw new RuntimeException(""); // TODO
				}
				return new Node(-1, (List<Node>) args.get(0).value);
			}
		};
	}

}
