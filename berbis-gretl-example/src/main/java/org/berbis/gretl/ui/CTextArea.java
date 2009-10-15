package org.berbis.gretl.ui;

import java.awt.TextArea;

public class CTextArea extends TextArea {

	private static final long serialVersionUID = -452849069292285689L;

	public CTextArea() {
		super(120, 25);
		this.setEditable(false);
	}

	public void addTextLine(final String s) {
		this.append(s + "\n");
	}
}
