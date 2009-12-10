package org.gro.lispy.funcs.impl;

import java.util.List;

import org.gro.lispy.funcs.Function;
import org.gro.lispy.funcs.Rare;
import org.gro.lispy.scope.ScopedSymbolTable;
import org.gro.lispy.tokenizer.Node;
import org.gro.lispy.tokenizer.Node.NodeType;

public class Let extends Rare {

	private final ScopedSymbolTable<Node> scope;

	public Let(final ScopedSymbolTable<Node> scope) {
		super();
		this.scope = scope;
	}

	@Override
	protected Integer getRequiredNumArgs() {
		return 2;
	}

	// (let sum (+ sum 1))
	@Override
	public boolean[] getEvalDefined() {
		return new boolean[] { false, true };
	}

	@Override
	public ArgEvalMode getEvaluateMode() {
		return Rare.ArgEvalMode.DEFINED;
	}

	@Override
	public Function getEvaluator() {
		return new Function() {
			public Node eval(final Node funNode, final List<Node> args) {
				if (args.get(0).nodeType != NodeType.SYMBOL) {
					throw new RuntimeException(""); // TODO
				}

				scope.let((String) args.get(0).value, args.get(1));
				return args.get(1);
			}
		};
	}

}
