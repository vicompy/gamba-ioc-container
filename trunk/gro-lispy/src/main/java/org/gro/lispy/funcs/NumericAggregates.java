package org.gro.lispy.funcs;

import java.util.List;

import org.gro.lispy.tokenizer.Node;
import org.gro.lispy.tokenizer.Node.NodeType;

public abstract class NumericAggregates extends AggregateFunction {
	@Override
	protected void checkTypes(final List<Node> args) {
		for (final Node arg : args) {
			if (arg.nodeType != NodeType.NUMBER) {
				throw new RuntimeException("all atoms must be NUMERIC. invalid atom: "
						+ arg.value.toString() + " at line " + arg.line);
			}
		}
	}
}