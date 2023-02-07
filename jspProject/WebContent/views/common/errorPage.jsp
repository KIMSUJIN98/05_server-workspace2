<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ include file = "menubar.jsp" %> <!-- index.jsp에서는 "views/common/menubar.jsp"처럼 썼는데 왜 다를까? 같은 경로에 있으므로 이렇게 써도 무방함 -->
	
	<br><br>
	
	<h1 align="center" style="color:red"><%= request.getAttribute("errorMsg") %></h1>

</body>
</html>