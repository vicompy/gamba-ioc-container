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
	<h4>V1</h4>
	<br />
	<br />
	<form action="${contextName}/S1.do" method="get">
		name: <input type="text" name="name" size="30" value="${name}" />${validationErrorMap['name']}<br />
		age:  <input type="text" name="age"  size="5"  value="${age}" />${validationErrorMap['age']}<br />
		<br />
		<br />
		<input type='submit' value="create"><br />
	</form>

	<br />
	<br />

	</center>
	
</body>
</html>
