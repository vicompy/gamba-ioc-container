package org.gro.lispy.funcs.impl;

import java.util.ArrayList;
import java.util.List;

import org.gro.lispy.funcs.Function;
import org.gro.lispy.funcs.Rare;
import org.gro.lispy.tokenizer.Node;
import org.gro.lispy.tokenizer.Node.NodeType;

public class Car extends Rare {

	@Override
	protected Integer getRequiredNumArgs() {
		return 1;
	}

	// (car (quote 1 2 3))
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

				if (args.get(0).nodeType != NodeType.LIST) {
					throw new RuntimeException("car requires a list as a unique argument");
				}

				final List<Node> list = ((List<Node>) args.get(0).value);
				if (list.size() < 1) {
					return new Node(new ArrayList<Node>());
				}
				return list.get(0);
			}
		};
	}

}
