package org.gro.lispy.funcs.impl;

import java.util.List;

import org.gro.lispy.funcs.Function;
import org.gro.lispy.funcs.Rare;
import org.gro.lispy.tokenizer.Node;

public class NewLine extends Rare {

	@Override
	protected Integer getRequiredNumArgs() {
		return 0;
	}

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
			public Node eval(final Node funNode, final List<Node> args) {
				return new Node(-1, "\n");
			}
		};
	}

}
