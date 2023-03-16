<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .login-area>*{float: right;} /* float: right;은 띄워져서 오른쪽으로 위치하게됨 */
    .login-area a{
        text-decoration: none;
        color: black;
        font-size: 12px;
    }
    .nav-area{background-color: black;}
    .menu{
        display: table-cell; /* inline으로 해도 된다 */
        width: 150px;
        height: 50px;
    }
    .menu a{
        text-decoration: none;
        color: white;
        font-size: 20px;
        font-weight: bold;
        display: block;
        width: 100%;
        height: 100%;
        line-height: 50px; /* height랑 같게하면 정가운데 배치됨 */
    }
    .menu a:hover{
        background-color: darkgray;
    }
</style>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>

<!-- Popper JS -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>



</head>
<body>
	<c:if test="${ !empty alertMsg }">
		<script>
			alert("${alertMsg}");
		</script>
		<c:remove var="alertMsg"/> <!-- 이따 확인 -->
	</c:if>
	
	
    <h1 align="center">Welcome Sujin World</h1>

    <div class="login-area">

	<c:choose>
	
		<c:when test="${ empty loginUser }">
	        <!-- case1. 로그인 전 -->
	        <form action="login.me" method="post">
	            <table>
	                <tr>
	                    <th>아이디</th>
	                    <td><input type="text" name="userId"></td>
	                </tr>
	                <tr>
	                    <th>비밀번호</th>
	                    <td><input type="password" name="userPwd"></td>
	                </tr>
	                <tr>
	                    <th colspan="2">
	                        <button type="submit">로그인</button>
	                        <button type="button" onclick="enrollPage();">회원가입</button> <!-- button type 생략시 자동적으로 submit이 되므로 button임을 명시해야함. -->
	                    </th>
	                </tr>
	            </table>
	            <script>
	            	function enrollPage(){
	            		location.href = "enrollForm.me";
	            	}
	            </script>
	        </form>
        </c:when>
        
        <c:otherwise>
	

	        <!-- case2. 로그인 후 -->
	        <div>
	            <b>${ loginUser.userName } 님</b>의 방문을 환영합니다 <br><br>
	            <div>
	                <a href="myPage.me">마이페이지</a>
	                <a href="logout.me">로그아웃</a>
	            </div>
	        </div>
    	</c:otherwise>
    </c:choose>

    </div>

    <br clear="both"> <!-- 띄워져있는것도 자리를 차지하게 됨. -->
    <br>

    <div class="nav-area" align="center">
        <!-- (.menu>a)*4 -->
        <div class="menu"><a href="${ pageContext.request.contextPath }">HOME</a></div>
        <div class="menu"><a href="list.no">공지사항</a></div>
        <div class="menu"><a href="list.bo?cpage=1">일반게시판</a></div> <!-- 페이징 중에서 첫번째 페이지로 이동하기 위해 주소에 키-벨류(value) 값을 할당함 -->
        <div class="menu"><a href="list.boo">특별게시판</a></div>
        <div class="menu"><a href="list.th">사진게시판</a></div>
    </div>

</body>
</html>