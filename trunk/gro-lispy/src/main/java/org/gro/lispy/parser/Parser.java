package org.gro.lispy.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.gro.lispy.funcs.Rare;
import org.gro.lispy.funcs.Rare.ArgEvalMode;
import org.gro.lispy.funcs.impl.Add;
import org.gro.lispy.funcs.impl.And;
import org.gro.lispy.funcs.impl.Assert;
import org.gro.lispy.funcs.impl.Car;
import org.gro.lispy.funcs.impl.Cdr;
import org.gro.lispy.funcs.impl.Concat;
import org.gro.lispy.funcs.impl.Cons;
import org.gro.lispy.funcs.impl.Def;
import org.gro.lispy.funcs.impl.Disp;
import org.gro.lispy.funcs.impl.Equals;
import org.gro.lispy.funcs.impl.If;
import org.gro.lispy.funcs.impl.LList;
import org.gro.lispy.funcs.impl.Lambda;
import org.gro.lispy.funcs.impl.Length;
import org.gro.lispy.funcs.impl.Let;
import org.gro.lispy.funcs.impl.Mul;
import org.gro.lispy.funcs.impl.Multi;
import org.gro.lispy.funcs.impl.NewLine;
import org.gro.lispy.funcs.impl.Quote;
import org.gro.lispy.funcs.impl.Sub;
import org.gro.lispy.scope.ScopedSymbolTable;
import org.gro.lispy.scope.ScopedSymbolTableException;
import org.gro.lispy.tokenizer.Node;
import org.gro.lispy.tokenizer.Tokenizer;
import org.gro.lispy.tokenizer.Node.NodeType;


public class Parser {

	private final List<Node> program;
	private final ScopedSymbolTable<Node> scope;

	public Parser(final String program) {
		this.program = new Tokenizer(program).tokenize();
		this.scope = new ScopedSymbolTable<Node>();
		// System.out.println(program);
	}

	private Node define(final String program) {
		final List<Node> p = new Parser(program).program;
		return parseExpression(new Node(p));
	}

	public List<Object> parseProgram() {
		scope.createLevel();

		scope.define("=>", new Node(-1, "=>"));
		scope.define("true", define("(quote 1)"));
		scope.define("false", define("(quote 0)"));
		scope.define("inc", define("(lambda (x => (+ x 1)))"));
		scope.define("dec", define("(lambda (x => (- x 1)))"));
		scope.define("nil", define("(quote ())"));

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
			// System.out.println("** func result: " + expression.toString() +
			// " => " + result.toString());
			return result;
		} else {

			final Node result;
			if (expression.nodeType == NodeType.SYMBOL) {
				try {
					result = scope.obtain((String) expression.value);
				} catch (final ScopedSymbolTableException e) {
					return expression;
				}
			} else {
				result = expression;
			}

			// System.out.println("** expression: " + expression.toString() +
			// " => " + result.toString());
			return result;
		}

	}

	// <program> ::= ( { <expression> } )
	// <expression> ::= <func> | IDENT
	// deprecated:::: <func> ::= (IDENT {<expression>})
	//
	// correcció!!
	// <func> ::= (expression {<expression>})
	// aleshores lambda queda com a:
	// ((LAMBDA <expression-def>) {<expression-args>})

	@SuppressWarnings("unchecked")
	private Node parseFunc(final Node funcList) {

		final List<Node> exp = (List<Node>) funcList.value;

		final Iterator<Node> iter = exp.iterator();

		// obté el node de nom de funció
		final Node funNode = parseExpression(iter.next());

		if (funNode.nodeType == Node.NodeType.FUNC) {

			// és una funció definida per lambda!!!
			// cal aplicar la substitució

			// per aplicar, falta obtenir la llista d'arguments que seràn
			// aplicats contra la lambda, evaluats com a
			// expressió
			final List<Node> args = new LinkedList<Node>();
			while (iter.hasNext()) {
				args.add(parseExpression(iter.next()));
			}

			scope.createLevel();

			// captura, de la definició de lambda, els noms dels arguments, i
			// els defineix en l'scope amb el valor extret de la llista
			// d'arguments ja evaluats a aplicar contra la lambda
			final Iterator<Node> iterLambdaDef = ((List<Node>) funNode.value).iterator();
			int i = 0;
			do {
				final Node n = iterLambdaDef.next();
				if (n.value.equals("=>")) {
					break;
				}
				scope.define((String) n.value, args.get(i));
				// System.out.println("defined "+((String) n.value)+":="+
				// args.get(i).toString()+" in level "+scope.getCurrentLevel());
				i++;
			} while (true);

			// extreu aquí l'expressió del cos de la definició de lambda, tal
			// qual; en ser evaluada ja s'aniràn substituint els arguments per
			// els corresponents valors.
			final Node value = iterLambdaDef.next();
			final Node r;
			if (value.value instanceof List) {
				final List<Node> lambdaExpTokens = new ArrayList<Node>();
				for (final Node n : (List<Node>) value.value) {
					lambdaExpTokens.add(n);
				}
				r = parseExpression(new Node(lambdaExpTokens));
			} else {
				// lambdaExpTokens.add(value);
				r = parseExpression(value);
			}
			// System.out.println(lambdaExpTokens.toString());

			// finalment, evalua el cos de la lambda; els arguments seràn
			// substituits per els seus valors, ja que aquests han sigut
			// definits en scope com a variables.
			// final Node
			// r = parseExpression(new Node(lambdaExpTokens));

			// tanca l'scope de lambda i així esborra els arguments-valors.
			scope.removeLevel();

			return r;

		}

//		 System.out.println(funNode.value + "-->"+funNode.line);
		final String funName = (String) funNode.value;

		Rare evaluator = null;

		// identifica la funció
		if ("if".equals(funName)) {
			evaluator = new If();
		} else if ("eval".equals(funName)) {
			final List<Node> args = new LinkedList<Node>();
			while (iter.hasNext()) {
				args.add(parseExpression(iter.next()));
			}
			if (args.size() != 1) {
				throw new RuntimeException("missing unique argument for eval");
			}
			return parseExpression(args.get(0));
		} else if ("quote".equals(funName) || "'".equals(funName)) {
			evaluator = new Quote();
		} else if ("lambda".equals(funName)) {
			evaluator = new Lambda();
		} else if ("+".equals(funName)) {
			evaluator = new Add();
		} else if ("-".equals(funName)) {
			evaluator = new Sub();
		} else if ("*".equals(funName)) {
			evaluator = new Mul();
		} else if ("concat".equals(funName)) {
			evaluator = new Concat();
		} else if ("def".equals(funName)) {
			evaluator = new Def(scope);
		} else if ("let".equals(funName)) {
			evaluator = new Let(scope);
		} else if ("car".equals(funName)) {
			evaluator = new Car();
		} else if ("cdr".equals(funName)) {
			evaluator = new Cdr();
		} else if ("cons".equals(funName)) {
			evaluator = new Cons();
		} else if ("list".equals(funName)) {
			evaluator = new LList();
		} else if ("length".equals(funName)) {
			evaluator = new Length();
		} else if ("assert".equals(funName)) {
			evaluator = new Assert();
		} else if ("multi".equals(funName)) {
			scope.createLevel();
			evaluator = new Multi();
			scope.removeLevel();
		} else if ("disp".equals(funName)) {
			evaluator = new Disp();
		} else if ("newline".equals(funName)) {
			evaluator = new NewLine();
		} else if ("=".equals(funName)) {
			evaluator = new Equals();
		} else if ("and".equals(funName)) {
			evaluator = new And();
		}

		if (evaluator == null) {
			throw new RuntimeException("undefined function: " + ((String) funNode.value) + " at line "
					+ funNode.line);
		}

		// evalua els arguments segons indica l'evaluator
		final List<Node> args = new LinkedList<Node>();

		final ArgEvalMode argEvalMode = evaluator.getEvaluateMode();
		if (argEvalMode == ArgEvalMode.ALL) {
			while (iter.hasNext()) {
				args.add(parseExpression(iter.next()));
			}
		} else if (argEvalMode == ArgEvalMode.NONE) {
			while (iter.hasNext()) {
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

		if ("if".equals(funName)) {
			// if necessita d'evaluar el seu resultat, així s'evita evaluar
			// l'expressió falsa
			return parseExpression(evaluator.eval(funNode, args));
		} else {
			return evaluator.eval(funNode, args);
		}
	}

}
