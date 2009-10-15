package org.berbis.gretl.ui;

import java.awt.Frame;

public class CFrame extends Frame {

	private static final long serialVersionUID = -1937619773722671380L;

	private final CTextArea textArea;

	public CFrame(final CTextArea textArea) {
		super();
		this.textArea = textArea;
		this.add(textArea);
		this.setTitle("getl");
		this.setLocation(200, 200);
		this.setSize(600, 300);
	}

	public void addTextLine(final String s) {
		this.textArea.addTextLine(s);
	}

}
