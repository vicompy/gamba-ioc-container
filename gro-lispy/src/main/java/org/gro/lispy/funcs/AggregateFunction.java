package org.gro.lispy.funcs;

import java.util.List;

import org.gro.lispy.tokenizer.Node;

public abstract class AggregateFunction implements Function {

	abstract protected Node getNeutre();

	abstract protected void checkTypes(Node funNode, List<Node> args);

	abstract protected Node evalPair(final Node current, final Node next);

	public Node eval(final Node funNode, final List<Node> args) {
		checkTypes(funNode, args);
		Node current = getNeutre();
		for (final Node n : args) {
			current = evalPair(current, n);
		}

		return current;
	}
}