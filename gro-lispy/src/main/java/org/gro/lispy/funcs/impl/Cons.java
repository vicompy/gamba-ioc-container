package org.gro.lispy.funcs.impl;

import java.util.ArrayList;
import java.util.List;

import org.gro.lispy.funcs.Function;
import org.gro.lispy.funcs.Rare;
import org.gro.lispy.tokenizer.Node;
import org.gro.lispy.tokenizer.Node.NodeType;

public class Cons extends Rare {

	@Override
	protected Integer getRequiredNumArgs() {
		return 2;
	}

	// (cons 1 (quote 2 3))
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
			@SuppressWarnings("unchecked")
			public Node eval(final Node funNode, final List<Node> args) {

				if (args.get(0).nodeType != NodeType.LITERAL && args.get(0).nodeType != NodeType.NUMBER) {
					throw new RuntimeException("cons requires an atom as a first argument");
				}
				if (args.get(1).nodeType != NodeType.LIST) {
					throw new RuntimeException("cons requires a list as a second argument");
				}
				final List<Node> list = new ArrayList<Node>();
				list.add(args.get(0));
				list.addAll((List<Node>) args.get(1).value);
				return new Node(list);
			}
		};
	}

}
