<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<center>
	<br />
	<br />
	<br />
	<h4>Create Person</h4>
	<br />
	<br />
	<form action="${contextName}/update.do" method="get">
		<%@ include file="generic-person-form.jsp" %>
		<input type='submit' value="update"><br />
	</form>

	<br />
	<br />

	</center>
	
</body>
</html>
