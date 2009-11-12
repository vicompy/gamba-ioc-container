<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
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
	<h4>Persons currently stored:</h4>
	<br />
	<br />

	<small>
    <table>
		<thead>
			<tr>
			    <th>Id</th>
			    <th>name</th>
			    <th>age</th>
		   	</tr>
		</thead>
		<tbody>
		<c:forEach var="person" items="${personList}">
			<tr>
			 	<td>${person.id}</td>
			 	<td>${person.name}</td>
				<td>${person.age}</td>
			</tr>
		</c:forEach>
		</tbody>
    </table>
    </small>
    
	<br />
	<br />
    
	<form action="${contextName}/create-form.do" method="get">
		<input type='submit' value="create new">
	</form>
	</center>

</body>
</html>