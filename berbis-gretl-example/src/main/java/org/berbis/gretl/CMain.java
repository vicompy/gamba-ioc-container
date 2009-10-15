package org.berbis.gretl;

import org.berbis.gretl.ui.CFrame;
import org.homs.gamba.container.context.GambaContainer;

public class CMain {

	public static void main(final String[] args) {

		final CFrame f = (CFrame) GambaContainer.getContext("gretl-context.xml").getBean("frame");
		f.setVisible(true);

	}

}
