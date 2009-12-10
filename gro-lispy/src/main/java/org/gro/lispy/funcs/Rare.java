package org.gro.lispy.funcs;

import java.util.List;

import org.gro.lispy.tokenizer.Node;

public abstract class Rare implements Evaluable {

	public enum ArgEvalMode {
		ALL, NONE, DEFINED
	}

	abstract protected ArgEvalMode getEvaluateMode();

	abstract protected boolean[] getEvalDefined();

	abstract protected Evaluable getEvaluator();

	public Node eval(final Node funNode, final List<Node> args) {
		return getEvaluator().eval(funNode, args);
	}
}