<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Paginatejs | Ligne</title>
    <link rel="stylesheet" href="resources/css/paginate.css">
    <link rel="stylesheet" href="resources/css/ligne.css">
    <script type="text/javascript" src="resources/js/paginate.js"></script>
    
    <style>
        *{
            font-family: sans-serif;
        }
    </style>
</head>
<body>
<div class="container">
    <div>
        <h1>특별게시판</h1>
    </div>
    <div class="panel">
        <div class="body">
            <div class="input-group">
                <label for="searchBox">Filtrar</label>
                <input type="search" id="searchBox" placeholder="Filtrar...">
            </div>
        </div>
    </div>
    <table class="myTable table hover">
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
	    	<c:choose>
	    		<c:when test="${ empty list }">
		        	<tr>
		        		<td colspan="6">조회된 게시글이 없습니다.</td>
		        	</tr>
	         	</c:when>
	         	<c:otherwise>
	         		<c:forEach var="b" items="${ list }">
						<tr>
							<td>${ b.boardNo }</td>
							<td>${ b.categoryNo }</td>
							<td>${ b.boardTitle }</td>
							<td>${ b.boardWriter }</td>
							<td>${ b.count }</td>
							<td>${ b.createDate }</td>
						</tr>
					</c:forEach>
				</c:otherwise>
	       </c:choose>
		</tbody>
	</table>
</div>
<script>

    let options = {
        numberPerPage:10, //Cantidad de datos por pagina
        goBar:true, //Barra donde puedes digitar el numero de la pagina al que quiere ir
        pageCounter:true, //Contador de paginas, en cual estas, de cuantas paginas
    };

    let filterOptions = {
        el:'#searchBox' //Caja de texto para filtrar, puede ser una clase o un ID
    };

    paginate.init('.myTable',options,filterOptions);
</script>
</body>
</html>