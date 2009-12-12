package org.gro.lispy.tokenizer;

import java.util.List;

/**
 * L'intèrpret, en primer lloc, ha de construir l'arbre del programa, on
 * cadascun dels nodes representa un sol token (àtom) o bé a una llista de
 * tokens.<br>
 * <ul>
 * <li>àtoms:<br>
 * <ul>
 * <li>strings ("hola")</li>
 * <li>numbers (1.3e-2)</li>
 * <li>syms (nil)</li>
 * </ul>
 * </li>
 * <li>sublist: list (+ 1 2 3) }</li>
 * </ul>
 */
public class Node {

	public final Integer line;
	/**
	 * indica la naturalesa del node.
	 *
	 * @see NodeType
	 */
	public final NodeType nodeType;
	/**
	 * contingut del node;
	 */
	public final Object value;

	public enum NodeType {
		/**
		 * un token que comença i acaba per '<tt>"</tt>' (double quote). <br>
		 * El valor del node serà un {@link java.lang.String}.
		 */
		LITERAL,
		/**
		 * un literal parsejable cap a <tt>java.lang.Long</tt> ó bé a
		 * <tt>java.lang.Double</tt>. <br>
		 * El valor del node serà un {@link java.lang.Long} ó bé un
		 * {@link java.lang.Double}.
		 */
		NUMBER,
		/**
		 * un literal que no �s {@link NodeType#LITERAL} ni
		 * {@link NodeType#NUMBER} ni {@link NodeType#LIST}, doncs ser� un
		 * s�mbol que referenciar� a un tipus predefinit, un identificador de
		 * variable � funci�, etc., o sigui, representa un tipus de literal que
		 * ser� evaluable i necessitar� de ser tradu�t. <br>
		 * El valor del node ser� un {@link java.lang.String}.
		 */
		SYMBOL,
		/**
		 * el contingut d'aquest node �s una subllista de nodes. <br>
		 * El valor del node ser� un {@link java.utils.List}&lt;{@link Node}
		 * &gt;.
		 */
		LIST,
		FUNC
	}

	/**
	 * constructor de node que cont� a una <b>subllista</b>
	 *
	 * @param list
	 */
	public Node(final List<Node> list) {
		this.nodeType = NodeType.LIST;
		this.value = list;
		this.line = null; //TODO
	}

	public Node(final int unusedIsAFunc, final List<Node> list) {
		this.nodeType = NodeType.FUNC;
		this.value = list;
		this.line = null; //TODO
	}

	public Node(final int unusedIsALiteral, final String literal) {
		this.nodeType = NodeType.LITERAL;
		this.value = literal;
		this.line = null; //TODO
	}

	/**
	 * constructor de node que cont� qualsevol tipus d'<b>�tom</b>.
	 *
	 * @param value
	 */
	public Node(final String value, final int line) {

		this.line = line;

		if (value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"') {
			this.nodeType = NodeType.LITERAL;
			this.value = value.substring(1, value.length() - 1);
		} else if (parseIfLongInteger(value) != null) {
			this.nodeType = NodeType.NUMBER;
			this.value = parseIfLongInteger(value);
		} else if (parseIfDouble(value) != null) {
			this.nodeType = NodeType.NUMBER;
			this.value = parseIfDouble(value);
		} else {
			this.nodeType = NodeType.SYMBOL;
			this.value = value;
		}
	}

	public Node(final Long value, final int line) {
		this.nodeType = NodeType.NUMBER;
		this.value = value;
		this.line = line;
	}
	public Node(final Double value, final int line) {
		this.nodeType = NodeType.NUMBER;
		this.value = value;
		this.line = line;
	}

	private static Long parseIfLongInteger(final String number) {
		try {
			return Long.valueOf(number);
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	private static Double parseIfDouble(final String number) {
		try {
			return Double.valueOf(number);
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return this.value.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nodeType == null) ? 0 : nodeType.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
//		System.out.println(getClass() +" vs. "+obj.getClass());
		if (getClass() != obj.getClass())
			return false;
		final Node other = (Node) obj;
		if (nodeType == null) {
			if (other.nodeType != null)
				return false;
		} else if (!nodeType.equals(other.nodeType))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}


}
