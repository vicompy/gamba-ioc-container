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
	<h4>Maven2 Artifact Finder</h4>
	<br />
	<br />
	<form action="${contextName}/search.do" method="get">
		<input type="text" name="wordToSearch" size="50">
		<select size='1' name='dept'>
			<option value='100'>non-limited</option>
			<option value='5'>5</option>
			<option value='3'>3</option>
		</select>
		<input type='submit' value="search">
	</form>

	<br />
	<br />
	<small>currently registered ${artifactsCount} artifacts</small>

	</center>
	
</body>
</html>
