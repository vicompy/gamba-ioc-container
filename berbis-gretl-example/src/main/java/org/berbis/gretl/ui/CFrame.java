package org.berbis.gretl.ui;

import java.awt.Frame;

public class CFrame extends Frame {

	private static final long serialVersionUID = -5038630318724347602L;

	private final LogicThread logicThread;
	public CFrame(final LogicThread logicThread) {
		super();
		this.logicThread = logicThread;
	}

	public void addTextLine(final String s) {
		((CTextArea) this.getComponent(0)).addTextLine(s);
	}

	public void run() {
		this.logicThread.chooseFileAndDownload(this);
	}

}
