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
	<h4>Results for <u>${form.wordToSearch}</u>:</h4>
	<br />
	<br />

    <table>
    	<thead>
        <tr>
          <th>groupId</th>
          <th>artifactId</th>
          <th>version</th>
          <th>Jar file</th>
          <th>Url</th>
        </tr>
        </thead>
      <c:forEach var="artifact" items="${artifactlist}">
        <tr>
          <td>${artifact.groupId}</td>
          <td>${artifact.artifactId}</td>
          <td>${artifact.version}</td>
          <td><a href='${artifact.url}${artifact.jarName}'>${artifact.jarName}</a></td>
          <td><a href='${artifact.url}'>${artifact.url}</a></td>
        </tr>
      </c:forEach>
    </table>
    
	<br />
	<br />
    
	<form action="${contextName}/start.do" method="get">
		<input type='submit' value="return">
	</form>
	</center>

</body>
</html>