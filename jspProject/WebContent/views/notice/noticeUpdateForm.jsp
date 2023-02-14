<%@page import="com.kh.notice.model.vo.Notice"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Notice n = (Notice)request.getAttribute("n"); // object 객체를 뱉음(다운캐스팅)
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
        height: 500px;
        margin: auto;
        margin-top: 50px;
    }
    /*#update-form table{border: 1px solid red;} // box-sizing: border-box; 확인용 */
    #update-form input, #update-form textarea{
        
        width: 100%;
        box-sizing: border-box;
    }
</style>
</head>
<body>
    <%@ include file = "../common/menubar.jsp" %>

    <div class="outer" align="center">
        <br>
        <h2 align="center">공지사항 수정하기</h2>
        <br>

        <form action="<%= contextPath %>/update.no" id="update-form" method="post"> <!-- get방식은 게시판에 적합하지 않음. 글자수 제한이 있어 내용이 짤릴 수 있다. -->
            <input type="hidden" name="num" value="<%= n.getNoticeNo() %>"> <!-- key값 확인(name="num") 필요함 / 사용자에게 입력받을 필요는 없지만 쿼리 where 조건문에서 사용되기때문에 hidden으로 값을 넘겨줌 -->
            <table>
                <tr>
                    <th width="50">제목</th>
                    <td width="450"><input type="text" name="title" required value="<%= n.getNoticeTitle() %>"></td>
                </tr>

                <tr>
                    <th>내용</th>
                    <td></td>
                </tr>

                <tr>
                    <td colspan="2">
                        <textarea rows="10" name="content" style="resize: none;" required><%= n.getNoticeContent() %></textarea>
                    </td>
                </tr>
            </table>

            <br><br>
            <div>
                <button type="submit">수정하기</button>
                <button type="button" onclick="history.back();">뒤로가기</button> <!-- "history.back();" : window 객체에서 제공하는 메소드 -->
            </div>

        </form>
    </div>
</body>
</html>