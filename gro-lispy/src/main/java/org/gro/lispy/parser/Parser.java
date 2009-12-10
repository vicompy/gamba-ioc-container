package org.gro.lispy.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.gro.lispy.funcs.Rare;
import org.gro.lispy.funcs.Rare.ArgEvalMode;
import org.gro.lispy.funcs.impl.Add;
import org.gro.lispy.funcs.impl.Concat;
import org.gro.lispy.funcs.impl.Lambda;
import org.gro.lispy.funcs.impl.Mul;
import org.gro.lispy.funcs.impl.Quote;
import org.gro.lispy.scope.ScopedSymbolTable;
import org.gro.lispy.tokenizer.Node;
import org.gro.lispy.tokenizer.Tokenizer;
import org.gro.lispy.tokenizer.Node.NodeType;

public class Parser {

	private final List<Node> program;
	private final ScopedSymbolTable<Node> scope;

	public Parser(final String program) {
		this.program = new Tokenizer(program).tokenize();
		this.scope = new ScopedSymbolTable<Node>();
		System.out.println(program);
	}

	public List<Object> parseProgram() {
		scope.createLevel();
		scope.define("ZERO", new Node("0", -1));
		scope.define("T", new Node("1", -1));
		scope.define("version", new Node("1.0", -1));
		scope.define("=>", new Node(-1, "=>"));

		final List<Object> returning = new ArrayList<Object>();
		for (final Node expression : program) {
			returning.add(parseExpression(expression).value);
		}
		scope.removeLevel();

		return returning;
	}

	private Node parseExpression(final Node expression) {

		if (expression.nodeType == Node.NodeType.LIST) {

			final Node result = parseFunc(expression);
			System.out.println("** func result: " + expression.toString() + " => " + result.toString());
			return result;
		} else {

			final Node result;
			if (expression.nodeType == NodeType.SYMBOL) {
				result = scope.obtain((String) expression.value);
			} else {
				result = expression;
			}

			System.out.println("** expression: " + expression.toString() + " => " + result.toString());
			return result;
		}

	}

	// <program> ::= ( { <expression> } )
	// <expression> ::= <func> | IDENT
	// <func> ::= (IDENT {<expression>})

	@SuppressWarnings("unchecked")
	private Node parseFunc(final Node funcList) {

		final List<Node> exp = (List<Node>) funcList.value;

		final Iterator<Node> iter = exp.iterator();

		// obté el node de nom de funció
		final Node funNode = iter.next();
		if (funNode.nodeType != Node.NodeType.SYMBOL) {
			throw new RuntimeException("expected a valid symbol, but obtained: " + funNode.value
					+ " at line " + funNode.line);
		}
		final String funName = (String) funNode.value;

		Rare evaluator = null;

		// identifica la funció
		if ("quote".equals(funName) ||
			"'".equals(funName)) {
			evaluator = new Quote();
		}
		if ("lambda".equals(funName)) {
			evaluator = new Lambda();
		}
		if ("+".equals(funName)) {
			evaluator = new Add();
		}
		if ("*".equals(funName)) {
			evaluator = new Mul();
		}
		if ("concat".equals(funName)) {
			evaluator = new Concat();
		}

		if (evaluator == null) {
			throw new RuntimeException("undefined function: " + ((String) funNode.value) + " at line "
					+ funNode.line);
		}

		// evalua els arguments segons indica l'evaluator
		final List<Node> args = new LinkedList<Node>();

		final ArgEvalMode argEvalMode = evaluator.getEvaluateMode();
		if (argEvalMode == ArgEvalMode.ALL) {
			while(iter.hasNext()) {
				args.add(parseExpression(iter.next()));
			}
		} else if (argEvalMode == ArgEvalMode.NONE) {
			while(iter.hasNext()) {
				args.add(iter.next());
			}
		} else if (argEvalMode == ArgEvalMode.DEFINED) {
			for (int i = 0; iter.hasNext(); i++) {
				if (evaluator.getEvalDefined()[i]) {
					args.add(parseExpression(iter.next()));
				} else {
					args.add(iter.next());
				}
			}

		}

		return evaluator.eval(funNode, args);
	}

	// private Node and(final Node funNode, final List<Node> args) {
	//
	// for (final Node arg : args) {
	// if (arg.nodeType != NodeType.NUMBER) {
	// throw new
	// RuntimeException("argument is not a NUMERIC for function 'and'");
	// }
	// }
	//
	// boolean condition = true;
	// for (final Node arg : args) {
	// condition = condition && (((Number) arg.value).longValue() != 0);
	// }
	// if (condition) {
	// return new Node("1", funNode.line);
	// } else {
	// return new Node("0", funNode.line);
	// }
	//
	// }
	//
	// private Node disp(final Node funNode, final List<Node> args) {
	//
	// // for (final Node arg : args) {
	// // if (arg.nodeType != NodeType.NUMBER) {
	// // throw new
	// // RuntimeException("argument is not a NUMERIC for function 'and'");
	// // }
	// // }
	//
	// String r = "";
	// for (final Node arg : args) {
	// r += arg.value.toString();
	// }
	// System.out.println(r);
	// return new Node(r, funNode.line);
	// }

}
