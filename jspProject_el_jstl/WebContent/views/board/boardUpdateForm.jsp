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
        height: 550px;
        margin: auto;
        margin-top: 50px;
    }
    /* #update-form table{border: 1px solid white;} */
    #update-form input, #update-form textarea{
        width: 100%;
        box-sizing: border-box;
    }
</style>
</head>
<body>
	<jsp:include page="../common/menubar.jsp"/>

    <div class="outer">
        <br>
        <h2 align="center">일반게시판 수정하기</h2>
        <br>

        <form id="update-form" action="update.bo" method="post" enctype="multipart/form-data"> <!-- 파일 자체를 넘기려면 enctype 필요 // 첨부파일은 무조건 post방식 -->
			<input type = "hidden" name = "bno" value = "${ b.boardNo }">
            <!-- 카테고리, 제목, 내용, 첨부파일 한 개 -->
            <table align="center">
                <!-- (tr>th+td)*4 -->
                <tr>
                    <th width="70">카테고리</th>
                    <td width="500">
                        <select name="category">
                        <!-- category 테이블에서 조회해오기 -->
                        	<c:forEach var="c" items="${list}">
                            	<option value="${ c.categoryNo }">${ c.categoryName }</option>
                            </c:forEach>
                        </select>
                        
                        <script>
                        	$(function(){ 													// 수정하기 눌렀을때 저장된 카테고리 정보를 불러오기 위한 스크립트 구문
                        		$("#update-form option").each(function(){ 					// 그 옵션을 하나씩 각각 탐색한다
                        			if($(this).text() == "${b.categoryNo}"){		/* 옵션안의 글자가 b.getCategoryNo()과 같은지 확인한다 */
                        				$(this).attr("selected", true);
                        			}
                        			
                        		})
                        	})
                        </script>
                    </td>
                </tr>
                <tr>
                    <th>제목</th>
                    <td><input type="text" name="title" required value="${ b.boardTitle }"></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td><textarea rows="10" name="content" style="resize: none;" required>${ b.boardContent }</textarea></td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td>
                        <!-- 현재 이 게시글에 붙은 첨부파일이 있을경우 -->
                        <c:if test="${ not empty at }">
                        	${ at.originName }
                        	<input type = "hidden" name="originFileNo" value="${ at.fileNo }">
                        </c:if>
                        <input type="file" name="upfile"></td>
                </tr>
            </table>
            <br>

            <div align="center">
                <button type="submit">수정하기</button>
                <button type="reset">취소하기</button>
            </div>
            

        </form>
    </div>

</body>
</html>