<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String alertMsg = (String)session.getAttribute("alertMsg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<% if(alertMsg != null){ %>
		<script>
			alert("<%=alertMsg %>");
		</script>
		<% session.removeAttribute("alertMsg"); %>
	<% } %>
	
	<form action="<%=contextPath %>/update.wt" method="post" align="center">
        <h1>[ Test Project ]</h1>
        <h2>Water 수정하기</h2>
        
        <table border="1" align="center">
        	<tr>
                <th>수정할 번호</th>
                <td><input type="number" name="wno"></td>
            </tr>
            <tr>
                <th>브랜드명</th>
                <td><input type="text" name="brand"></td>
            </tr>
            <tr>
                <th>가격</th>
                <td><input type="number" name="price"></td>
            </tr>
        </table>

        <br>
        <button type="submit">확인</button>
        <button type="button" onclick="insertWater();">추가하기</button>
    </form>
    
    <script>
        function insertWater(){
            location.href="<%=contextPath %>";
        }
    </script>
</body>
</html>