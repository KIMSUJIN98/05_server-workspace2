<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .outer{
        background-color: black;
        color: white;
        width: 1000px;
        height: 800px;
        margin: auto;
        margin-top: 50px;
    }
    .list-area{
        width: 760px;
        margin: auto;
    }
    .thumbnail{
        border: 1px solid white;
        width: 220px;
        display: inline-block;
        margin: 14px;
    }
    .thumbnail:hover{
    	cursor: pointer;
    	opacity: 0.7;
    }
</style>
</head>
<body>
	<jsp:include page="../common/menubar.jsp"/>

    <div class="outer">
        <br>
        <h2 align="center">사진게시판</h2>
        <br>

		<c:if test="${ not empty loginUser }">
        	<div align="right" style="width: 850px;">
            	<a href="enrollForm.th" class="btn btn-sm btn-secondary">글작성</a>
        	</div>
        </c:if>
        
        <c:choose>
        	<c:when test="${ empty list }">
            	<p align="center">조회된 게시글이 없습니다.</p>
			</c:when>
			<c:otherwise>
			    <div class="list-area">
			    	<c:forEach var="b" items="${ list }">
				    	<div class="thumbnail" align="center">
				        	<input type="hidden" value="${ b.boardNo }">
				            <img src="${ b.titleImg }" width="200" height="150">
				            <p>
				            	No.${ b.boardNo } ${ b.boardTitle } <br>
				                조회수 : ${ b.count }
				            </p>
				    	</div>
			    	</c:forEach>
		        </div>
	        </c:otherwise>
	    </c:choose>
    </div>
    
    <script>
    	$(".thumbnail").click(function(){ 																			// 클래스가 thumbnail인 요소가 클릭되었을 때
    		location.href = "detail.th?bno=" + $(this).children("input").val(); 					// bno : 자식중에 input요소인 것의 value값
    	})
    	
    </script>

</body>
</html>