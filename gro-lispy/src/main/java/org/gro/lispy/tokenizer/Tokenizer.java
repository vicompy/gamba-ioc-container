package org.gro.lispy.tokenizer;

import java.util.LinkedList;
import java.util.List;

public class Tokenizer {

	private final String program;
	private int programIndex;
	private int lineNum;

	public Tokenizer(final String program) {
		super();
		this.program = program;
		this.programIndex = 0;
	}

	public List<Node> tokenize() {

		lineNum = chupaWhiteSpaces(lineNum);

		if (program.charAt(programIndex) != '(') {
			throw new RuntimeException("expected (, but obtained: " + program.charAt(programIndex));
		}
		programIndex++; // chupa (

		final List<Node> tokens = new LinkedList<Node>();

		while (true) {

			lineNum = chupaWhiteSpaces(lineNum);

			// if (programIndex >= program.length()) {
			// break;
			// }

			// si s'acaba la llista, acabar de chupar tokens!
			if (program.charAt(programIndex) == ')') {
				break;
			}

			// si s'obre una nova llista, llançar un nou nivell de recursió
			if (program.charAt(programIndex) == '(') {
				tokens.add(new Node(tokenize()));
				continue;
			}

			if (program.charAt(programIndex) == '"') {

				// captura un String, delimitat per '"'
				int j = programIndex + 1;

				while (program.charAt(j) != '"') {
					j++;
				}
				j++;
				tokens.add(new Node(program.substring(programIndex, j), lineNum));
				programIndex = j;
			} else {

				// captura un símbol, de longitud qualsevol
				int j = programIndex + 1;
				while (!Character.isWhitespace(program.charAt(j)) && program.charAt(j) != '('
						&& program.charAt(j) != ')') {
					j++;
				}
				tokens.add(new Node(program.substring(programIndex, j), lineNum));
				programIndex = j;
			}

		}

		programIndex++; // chupa )

		return tokens;
	}

	private int chupaWhiteSpaces(int line) {
		while (Character.isWhitespace(program.charAt(programIndex))) {
			// captura un \n com a una nova línia de programa
			if (program.charAt(programIndex) == '\n') {
				line++;
			}
			programIndex++;
			// if (programIndex >= program.length()) {
			// break;
			// }
		}
		return line;
	}

}
