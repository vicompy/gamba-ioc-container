package org.gro.lispy.tokenizer;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.gro.lispy.scope.ScopedSymbolTableException;
import org.junit.Test;

public class NodeTest {

	/**
	 * test d'�toms
	 *
	 * @throws ScopedSymbolTableException
	 */
	@Test
	public void testAtoms() throws ScopedSymbolTableException {
		final Node nLiteral = new Node("\"hola\"", 0);
		final Node nNumericLongInt = new Node("65000", 0);
		final Node nNumericDouble = new Node("3.14e+0", 0);
		final Node nSymbol = new Node("nil", 0);

		assertEquals(Node.NodeType.LITERAL, nLiteral.nodeType);
		assertEquals("hola", nLiteral.value);

		assertEquals(Node.NodeType.NUMBER, nNumericLongInt.nodeType);
		assertEquals(Long.valueOf(65000), nNumericLongInt.value);

		assertEquals(Node.NodeType.NUMBER, nNumericDouble.nodeType);
		assertEquals(Double.valueOf(3.14e+0), nNumericDouble.value);

		assertEquals(Node.NodeType.SYMBOL, nSymbol.nodeType);
		assertEquals("nil", nSymbol.value);
	}

	/**
	 * test de subllista
	 *
	 * @throws ScopedSymbolTableException
	 */
	@Test
	public void testSubList() throws ScopedSymbolTableException {

		final List<Node> nodeList = new LinkedList<Node>();
		nodeList.add(new Node("1", 0));
		nodeList.add(new Node("2", 0));
		nodeList.add(new Node("3", 0));
		final Node nSublist = new Node(nodeList);

		assertEquals(Node.NodeType.LIST, nSublist.nodeType);
		assertEquals(nodeList, nSublist.value);
		assertEquals("[1, 2, 3]", nSublist.value.toString());
	}

	// TODO falta FUNC
	@Test
	public void testLambda() throws ScopedSymbolTableException {

		final List<Node> nodeList = new LinkedList<Node>();
		nodeList.add(new Node("x", 0));
		nodeList.add(new Node("\"=>\"", 0));
		nodeList.add(new Node("+", 0));
		nodeList.add(new Node("x", 0));
		nodeList.add(new Node("x", 0));
		final Node nFunc = new Node(1, nodeList);

		assertEquals(Node.NodeType.FUNC, nFunc.nodeType);
		assertEquals(nodeList, nFunc.value);
		assertEquals("[x, =>, +, x, x]", nFunc.value.toString());
	}


}
