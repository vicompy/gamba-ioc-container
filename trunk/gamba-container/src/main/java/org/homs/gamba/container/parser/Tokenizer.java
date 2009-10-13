package org.homs.gamba.container.parser;

import org.homs.gamba.container.exception.GambaException;

/**
 * Objecte tokenitzador d'una definició de bean llegida de fitxer de propietats.
 *
 * @author mhoms
 */
class Tokenizer {

	private final String beanTextDef;
	private int pointer;

	public Tokenizer(final String beanTextDef) {
		this.beanTextDef = format(beanTextDef);
		pointer = 0;
	}

	private void readSeparators() {
		while (hasNextToken() && beanTextDef.charAt(pointer) == ' ') {
			pointer++;
		}
	}

	public boolean isSymbol() {
		readSeparators();
		return getSymbol() != null;
	}

	public void gambaAssert(final String expected) throws GambaException {
		final String obtained = nextToken();
		if (!expected.equals(obtained)) {
			gambaThrowExpected(expected, obtained);
		}
	}

	public void gambaAssert(final String expected, final String obtained) throws GambaException {
		if (!expected.equals(obtained)) {
			gambaThrowExpected(expected, obtained);
		}
	}

	public void gambaThrowExpected(final String expected, final String obtained) throws GambaException {
		throw new GambaException("expected: " + expected + ", but obtained: " + obtained);
	}

	private String getSymbol() {
		if (!hasNextToken()) {
			return null;
		}

		// si el token a obtenir és un símbol especial...
		final char sym = beanTextDef.charAt(pointer);
		switch (sym) {
			case '~':
			case ':':
			case '{':
			case '}':
			case '(':
			case ')':
			case ';':
			case ',':
				return String.valueOf(sym);
			case '<':
				if (beanTextDef.charAt(pointer + 1) == '-') {
					return "<-";
				}
			default:
				return null;
		}
	}

	public String nextToken() {
		readSeparators();

		final String sym = getSymbol();
		if (sym != null) {
			pointer += sym.length();
			return sym;
		}

		// fijo el token és una cadena
		final int begin = pointer;
		boolean insideString = false;
		char currentChar;
		while (hasNextToken() && (beanTextDef.charAt(pointer) != ' ' && getSymbol() == null) || insideString) {
			currentChar = beanTextDef.charAt(pointer);
			if (currentChar == '"') {
				// equival a: insideString = !insideString;
				insideString ^= true;
			}
			pointer++;
		}
		return beanTextDef.substring(begin, pointer);
	}

	public String nextTokenQuiet() {
		final int tempPointer = pointer;
		final String nextToken = nextToken();
		pointer = tempPointer;
		return nextToken;
	}

	private String format(final String beanTextDef) {
		return beanTextDef.replaceAll("\\s", " ");
	}

	public boolean hasNextToken() {
		return pointer < beanTextDef.length();
	}

	@Override
	public String toString() {
		return beanTextDef;
	}

}
