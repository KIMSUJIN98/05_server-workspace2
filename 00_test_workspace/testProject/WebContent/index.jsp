<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>20230227 테스트</h2>
    <form action="/insert.wt" method="post">
        <table>
            <tr>
                <th>브랜드</th>
                <td><input type="text" name="brand"></td>
            </tr>
            <tr>
                <th>가격</th>
                <td><input type="number" name="price"></td>
            </tr>
        </table>

        <br>

        <button type="submit">등록하기</button>
    </form>
    
</body>
</html>