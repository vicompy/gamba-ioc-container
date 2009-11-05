package org.homs.test1.actions;

public class IniciAction {

	public String execute() {
		System.out.println("==> IniciAction!");

		final StringBuffer s = new StringBuffer();

		s.append("<HTML>");
		s.append("<HEAD>");
		s.append("</HEAD>");
		s.append("<BODY>");
		s.append("	<form action='http://localhost:8888/demo/final' method='get'>");
		s.append("		<input type='text' name='name'>");
		s.append("		<input type='text' name='age'>");
		s.append("		<select size='5' multiple='multiple' name='members'>");
		s.append("			<OPTION VALUE='1'>One</OPTION>");
		s.append("			<OPTION VALUE='2'>Two</OPTION>");
		s.append("			<OPTION VALUE='3'>Three</OPTION>");
		s.append("		</select>");
		s.append("		<input type='submit'>");
		s.append("	</form>");
		s.append("	<br>");
		s.append("</BODY>");
		s.append("</HTML>");

		return s.toString();
	}
}
