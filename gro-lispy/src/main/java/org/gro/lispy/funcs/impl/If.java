package org.gro.lispy.funcs.impl;

import java.util.List;

import org.gro.lispy.funcs.Function;
import org.gro.lispy.funcs.Rare;
import org.gro.lispy.tokenizer.Node;
import org.gro.lispy.tokenizer.Node.NodeType;

public class If extends Rare {

	@Override
	protected Integer getRequiredNumArgs() {
		return 3;
	}

	@Override
	public boolean[] getEvalDefined() {
		return new boolean[] { true, false, false };
	}

	@Override
	public ArgEvalMode getEvaluateMode() {
		return Rare.ArgEvalMode.DEFINED;
	}

	@Override
	public Function getEvaluator() {
		return new Function() {
			@SuppressWarnings("unchecked")
			public Node eval(final Node funNode, final List<Node> args) {

				if (args.get(0).nodeType != NodeType.NUMBER) {
					throw new RuntimeException("if requires a boolean result (NUMERIC) as a first argument");
				}

				if (((Number) args.get(0).value).doubleValue() != 0) {
					if (args.get(1).nodeType == NodeType.LIST) {
						return new Node((List<Node>) args.get(1).value);
					} else {
						return args.get(1);
					}
				} else {
					if (args.get(2).nodeType == NodeType.LIST) {
						return new Node((List<Node>) args.get(2).value);
					} else {
						return args.get(2);
					}
				}
			}
		};
	}

}
