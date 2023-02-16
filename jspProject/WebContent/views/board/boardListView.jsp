<%@page import="com.kh.board.model.vo.Board"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.kh.common.model.vo.PageInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	PageInfo pi = (PageInfo)request.getAttribute("pi"); 						// object 뱉으므로 다운캐스팅, import 확실하게 처리
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list"); 	// object 뱉으므로 다운캐스팅, import 확실하게 처리

	int currentPage = pi.getCurrentPage(); // 현재페이지에 대한 정보를 변수에 저장하여 필요할때마다 변수를 호출해서 사용하고자 함.
	int startPage = pi.getStartPage(); // 시작페이지에 대한 정보를 변수에 저장하여 필요할때마다 변수를 호출해서 사용하고자 함.
	int endPage = pi.getEndPage(); // 끝페이지에 대한 정보를 변수에 저장하여 필요할때마다 변수를 호출해서 사용하고자 함.
	int maxPage = pi.getMaxPage(); // max 페이지에 대한 정보를 변수에 저장하여 필요할때마다 변수를 호출해서 사용하고자 함.
%>
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
        height: 600px;
        margin: auto;
        margin-top: 50px;
    }
    .list-area{
        border: 1px solid white;
        text-align: center;
    }
</style>
</head>
<body>
	<%@ include file = "../common/menubar.jsp" %>

    <div class="outer">
        <br>
        <h2 align="center">일반게시판</h2>
        <br>

        <!-- 로그인한 회원만 보여지는 div -->
        <% if(loginUser != null) { %>
        <div align="right" style="width: 860px;">
            <a href="<%=contextPath%>/enrollForm.bo" class="btn btn-sm btn-secondary">글작성</a>
            <br><br>
        </div>
        <% } %>

        <table align="center" class="list-area">
            <thead>
                <tr>
                    <th width="70">글번호</th>
                    <th width="80">카테고리</th>
                    <th width="300">제목</th>
                    <th width="100">작성자</th>
                    <th width="50">조회수</th>
                    <th width="100">작성일</th>
                </tr>
            </thead>
            <tbody>
            	<% if(list.isEmpty()) { %>
                <!-- case1. 게시글이 없을 경우 -->
	                <tr>
	                    <td colspan="6">조회된 게시글이 없습니다.</td>
	                </tr>
				<% }else { %>
	
                <!-- case2. 게시글이 있을 경우 -->
	                <% for(Board b: list) { %>
		                <tr>
		                    <td><%= b.getBoardNo() %></td>
		                    <td><%= b.getCategoryNo() %></td>
		                    <td><%= b.getBoardTitle() %></td>
		                    <td><%= b.getBoardWriter() %></td>
		                    <td><%= b.getCount() %></td>
		                    <td><%= b.getCreateDate() %></td>
		                </tr>
	                <% } %>
               <% } %>
            </tbody>
        </table>

        <br><br>

        <div class="paging-area" align="center"> <!-- 페이징바를 넣을 div -->
        
        	<% if(currentPage != 1){ %>
            	<button onclick="location.href ='<%=contextPath%>/list.bo?cpage=<%=currentPage-1%>';">&lt;</button>
            <% } %>
            
            <% for(int p = startPage; p<=endPage; p++){ %>
            	<% if(p == currentPage){ %>
            		<button style="background: pink" disabled><%= p %></button>
            	<% }else { %>
            		<button onclick = "location.href = '<%=contextPath%>/list.bo?cpage=<%=p%>';"><%= p %></button>
            	<% } %>
            <% } %>
            
            <% if(currentPage != maxPage){ %>
            	<button onclick="location.href ='<%=contextPath%>/list.bo?cpage=<%=currentPage+1%>';">&gt;</button>
        	<% } %>
        </div>


    </div>


</body>
</html>