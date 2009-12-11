package org.gro.lispy.funcs.impl;

import java.util.List;

import org.gro.lispy.funcs.Function;
import org.gro.lispy.funcs.Rare;
import org.gro.lispy.tokenizer.Node;

public class Assert extends Rare {

	@Override
	protected Integer getRequiredNumArgs() {
		return 2;
	}

	// (cdr (quote 1 2 3))
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
		return new Function() {
			public Node eval(final Node funNode, final List<Node> args) {

				if (args.get(0).nodeType != args.get(1).nodeType || !args.get(0).equals(args.get(1))) {
					throw new RuntimeException("assertion failed, at line " + funNode.line);
				}
				return new Node(1L, funNode.line);
			}
		};
	}

}
