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
	<h4>${titleForm}</h4>
	<br />
	<br />
	<form action="${contextName}/bookmark/save.do" method="get">
		<input type='submit' value="save"><br />
		<br />
		id: 		${id}
		<br />
		title: 		<input type="text" name="title" size="60" value="${title}" />
					${validationErrorMap['title']}<br />
		URL:  		<input type="text" name="url"  size="60"  value="${url}" />
					${validationErrorMap['url']}<br />
		<br />
		comments:  	<textarea name="comments" cols="60" rows="10">${comments}</textarea>
					${validationErrorMap['comments']}<br />
		tags:		<input type="text" name="spaceSeparatedTags"  size="60"  value="${spaceSeparatedTags }" />
					${validationErrorMap['spaceSeparatedTags ']}<br />
		<br />
		<br />
		<input type='submit' value="save"><br />
	</form>

	<br />
	<br />

	</center>
	
</body>
</html>
