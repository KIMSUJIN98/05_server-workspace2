<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- url 로는 http://localhost:8003/ea/views/2_standardAction/02_forward.jsp -->
	<h1>여기는 02_forward.jsp 이다.</h1>
	
	<jsp:forward page="footer.jsp"></jsp:forward>				<!-- url은 http://localhost:8003/ea/views/2_standardAction/02_forward.jsp로 동일하나 다른 페이지로 이동함 -->
	<!-- 나중에 if else 분기쳐서 나타내는 페이지 포워딩 가능 -->
	
	
	

</body>
</html>