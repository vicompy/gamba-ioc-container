package org.gro.lispy.funcs;

import java.util.List;

import org.gro.lispy.tokenizer.Node;

public abstract class Rare implements Function {

	public enum ArgEvalMode {
		ALL, NONE, DEFINED
	}

	abstract public ArgEvalMode getEvaluateMode();

	/**
	 * un <tt>true</tt> fa que s'evalui l'argument
	 * 
	 * @return
	 */
	abstract public boolean[] getEvalDefined();

	abstract public Function getEvaluator();

	abstract protected Integer getRequiredNumArgs();

	public Node eval(final Node funNode, final List<Node> args) {
		if (getRequiredNumArgs() != null && getRequiredNumArgs() != args.size()) {
			throw new RuntimeException("function " + funNode.value + " requires " + getRequiredNumArgs()
					+ ", but there are " + args.size() + ", at line " + funNode.line);
		}
		return getEvaluator().eval(funNode, args);
	}
}