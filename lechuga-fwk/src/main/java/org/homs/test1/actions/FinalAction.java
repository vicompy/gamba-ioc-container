package org.homs.test1.actions;

public class FinalAction {

	// @Action(formBean=B.class) TODO!!! evita mappings!!
	public String execute(final Object form) {
		try {

			System.out.println("==> FinalAction!");
			System.out.println("==> formBean: " + ((B) form).toString());

			final StringBuffer s = new StringBuffer();

			s.append("<html>");
			s.append("<head></head>");
			s.append("<body>");
			s.append("	<form action='http://localhost:8888/demo/inici' method='get'>");
			s.append("		<input type='submit'>");
			s.append("	</form>");
			s.append("</body>");

			return s.toString();

		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
