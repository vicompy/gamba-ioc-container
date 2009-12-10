package org.gro.lispy.funcs.impl;

import java.util.List;

import org.gro.lispy.funcs.Function;
import org.gro.lispy.funcs.Rare;
import org.gro.lispy.scope.ScopedSymbolTable;
import org.gro.lispy.tokenizer.Node;
import org.gro.lispy.tokenizer.Node.NodeType;

public class Def extends Rare {

	private final ScopedSymbolTable<Node> scope;


	public Def(final ScopedSymbolTable<Node> scope) {
		super();
		this.scope = scope;
	}

	@Override
	protected Integer getRequiredNumArgs() {
		return 2;
	}

	// (def sum (+ 2 3))
	@Override
	public boolean[] getEvalDefined() {
		return new boolean[]{false, true};
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

				scope.define((String) args.get(0).value, args.get(1));
				return args.get(1);
			}
		};
	}

}
