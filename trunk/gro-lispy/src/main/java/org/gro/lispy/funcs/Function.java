package org.gro.lispy.funcs;

import java.util.List;

import org.gro.lispy.tokenizer.Node;

public interface Function {
	Node eval(Node funNode, List<Node> args);
}