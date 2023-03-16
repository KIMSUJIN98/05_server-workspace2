<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    /*#enroll-form table{border: 1px solid red;} // box-sizing: border-box; 확인용 */
    #enroll-form input, #enroll-form textarea{
        
        width: 100%;
        box-sizing: border-box;
    }
</style>
</head>
<body>
    <jsp:include page="../common/menubar.jsp"/>

    <div class="outer" align="center">
        <br>
        <h2 align="center">공지사항 작성하기</h2>
        <br>

        <form action="insert.no" id="enroll-form" method="post"> <!-- get방식은 게시판에 적합하지 않음. 글자수 제한이 있어 내용이 짤릴 수 있다. -->
            <table>
                <tr>
                    <th width="50">제목</th>
                    <td width="450"><input type="text" name="title" required></td>
                </tr>

                <tr>
                    <th>내용</th>
                    <td></td>
                </tr>

                <tr>
                    <td colspan="2">
                        <textarea rows="10" name="content" style="resize: none;" required></textarea>
                    </td>
                </tr>
            </table>

            <br><br>
            <div>
                <button type="submit">등록하기</button>
                <button type="reset">초기화</button>
                <button type="button" onclick="history.back();">뒤로가기</button> <!-- "history.back();" : window 객체에서 제공하는 메소드 -->
            </div>

        </form>
    </div>

</body>
</html>