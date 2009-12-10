package org.gro.lispy.funcs.impl;

import java.util.ArrayList;
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
				if (args.get(1).nodeType != NodeType.LIST) {
					throw new RuntimeException("if requires a list as a second argument");
				}
				if (args.get(2).nodeType != NodeType.LIST) {
					throw new RuntimeException("if requires a list as a third argument");
				}

				final List<Node> list = new ArrayList<Node>();

				//TODO cal copiar la llista? no es pot retornar direcatment?
				if (((Number) args.get(0).value).doubleValue() != 0) {
					list.addAll((List<Node>) args.get(1).value);
				} else {
					list.addAll((List<Node>) args.get(2).value);
				}
				return new Node(list);
			}
		};
	}

}
