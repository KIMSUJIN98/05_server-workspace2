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
        height: auto;
        margin: auto;
        margin-top: 50px;
    }
</style>
</head>
<body>
	<jsp:include page="../common/menubar.jsp"/>

    <div class="outer">
        <br>
        <h2 align="center">일반게시판 상세보기</h2>
        <br>

        <table id="detail-area" border="1" align="center">
            <!-- (tr>th+td+th+td)*4 -->
            <tr>
                <th width="70">카테고리</th>
                <td width="70">${ b.categoryNo }</td>
                <th width="70">제목</th>
                <td width="350">${ b.boardTitle }</td>
            </tr>
            <tr>
                <th>작성자</th>
                <td>${ b.boardWriter }</td>
                <th>작성일</th>
                <td>${ b.createDate }</td>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="3" style="height: 200px;">${ b.boardContent }</td>
            </tr>
            <tr>
                <th>첨부파일</th>
                <td colspan="3">
                	<c:choose>
                		<c:when test="${ empty at }">
	                    	첨부파일이 없습니다.
                    	</c:when>
                    	<c:otherwise>
                    		<a download="${ at.originName }" href="${at.filePath}${at.changeName}">${ at.originName }</a>			<!-- 다운로드시 수정파일명이 아닌 원본파일명으로 저장하고 싶다면 다운로드 뒤에 원본값 으로 지정해줘야 함 --> <!-- jsp/resources/board_upfiles/xxxx.png 경로 : 현재 at.getFilePath()가 "resources/board_upfiles/" 처럼 board_upfiles 뒤에 '/'를 포함한 경로를 가지고 있음 -->
                    	</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>
        <br>

        <div align="center">
            <a href="list.bo?cpage=1" class="btn btn-sm btn-secondary">목록가기</a>

            <!-- 로그인한 사용자가 게시글 작성자일 경우 -->
            <c:if test="${ not empty loginUser and loginUser.userId eq b.boardWriter }">
                <a href="updateForm.bo?bno=${ b.boardNo }" class="btn btn-sm btn-warning">수정하기</a>
                <a href="#" class="btn btn-sm btn-danger">삭제하기</a>
            </c:if>
        </div>

	    <br>
	    
	    <div id="reply-area">
	    	<table border="1" align="center">
	    		<thead>
	    			<tr>
	    				<th>댓글작성</th>
	    				
	    				<c:choose>
	    					<c:when test="${ not empty loginUser }">
			    				<td>
			    					<textarea id="replyContent" rows="3" cols="50" style="resize:none;"></textarea>
			    				</td>
			    				<td><button onclick="insertReply();">댓글등록</button></td>
		    				</c:when>
		    				<c:otherwise>
			    				<td>
			    					<textarea rows="3" cols="50" style="resize:none;" readonly>로그인 후 이용가능한 서비스 입니다.</textarea>
			    				</td>
			    				<td><button disabled>댓글등록</button></td>
		    				</c:otherwise>
		    			</c:choose>
	    			</tr>
	    		</thead>
	    		
	    		<tbody>
	    			<!-- 아래 스크립트의 value값이 들어올 자리 -->
	    		</tbody>
	    		
	    	</table>
	    	
	    	
	    	<script>
	    	
	    		$(function(){ 																		// $ : 모든 그림이 랜더링되고나서 selectReplyList() 함수가 실행됨
	    			selectReplyList();
	    		
	    			setInterval(selectReplyList, 1000); 											// setInterval(괄호없이함수명, 몇ms간격?)
	    		})
	    		
	    		// ajax로 댓글 작성용
	    		function insertReply(){
	    			
	    			$.ajax({
	    				url:"rinsert.bo",
	    				data:{
	    					content:$("#replyContent").val(),													// key:value
	    					bno:${b.boardNo}
	    					// userNo : 로그인 안한 경우, loginUser null인 경우에는 널포인트 날 수도 있음					// (로그인 안하면 작성을 못하게끔 막아놓긴 했지만 만일의 사태 대비하여 확실하게 하기 위함)
	    				},
	    				type:"post",
	    				success:function(result){
	    					if(result > 0){ // 댓글 작성 성공
	    						selectReplyList(); 																// 작성됐는지 리스트에 추가되어 조회되게끔 만들기
	    						$("#replyContent").val("");														// 새로운 내용을 입력받을 수 있도록 작성했던 내용이 지워지게 만들기
	    					}
	    					
	    				},
	    				error:function(){
	    					console.log("댓글작성용 ajax 통신 실패!");
	    				}
	    				
	    			})
	    		}
	    		
	    		
	    		
	    		
	    		
	    	
	    		// ajax로 해당 게시글에 딸린 댓글 목록 조회용
	    		function selectReplyList(){
	    			$.ajax({
	    				url:"rlist.bo",
	    				data:{bno:${b.boardNo}},
	    				success:function(list){
	    					let value = "";
	    					for(let i=0; i<list.length; i++){
	    						value += "<tr>"
	    						 		   + "<td>" + list[i].replyWriter + "</td>"
	    						 		   + "<td>" + list[i].replyContent + "</td>"
	    						 		   + "<td>" + list[i].createDate + "</td>"
	    						 		   + "</tr>";
	    						$("#reply-area tbody").html(value);
	    					}
	    				},
	    				error:function(){
	    					console.log("ajax 통신 실패!");
	    				}
	    			})
	    		}
	    	</script>
	    	
	    	
	    </div>
	    
	    
    </div>
    

</body>
</html>