package org.gro.lispy.funcs;

import java.util.List;

import org.gro.lispy.tokenizer.Node;
import org.gro.lispy.tokenizer.Node.NodeType;

public abstract class LiteralAggregates extends AggregateFunction {
	@Override
	protected void checkTypes(final Node funNode, final List<Node> args) {
		for (final Node arg : args) {
			if (arg.nodeType != NodeType.LITERAL) {
				throw new RuntimeException("all atoms must be LITERAL. in operation " + funNode.value
						+ ", invalid atom: " + arg.value.toString() + " at line " + arg.line);
			}
		}
	}
}