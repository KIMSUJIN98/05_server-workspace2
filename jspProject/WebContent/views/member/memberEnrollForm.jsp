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
        margin: auto;
        margin-top: 50px;
    }

    #enroll-form table{margin: auto;}
    #enroll-form input{margin: 5px;}
</style>
</head>
<body>
    <%@ include file = "../common/menubar.jsp" %>

    <div class="outer">
        <br>
        <h2 align="center">회원가입</h2>

        <form id="enroll-form" action="<%= contextPath %>/insert.me" method="post"> <!-- menubar.jsp에 contextPath를 만들어뒀는데 menubar.jsp가 include 되어있으므로 contextPath 사용가능함. -->

            <table>
                <tr>
                    <td>* 아이디</td>
                    <td><input type="text" name="userId" maxlength="12" required></td>
                    <td><button type="button" onclick="idCheck();">중복확인</button></td>
                </tr>
                <tr>
                    <td>* 비밀번호</td>
                    <td><input type="password" name="userPwd" maxlength="15" required></td>
                    <td></td>
                </tr>
                <tr>
                    <td>* 비밀번호 확인</td>
                    <td><input type="password" maxlength="15" required></td>
                    <td></td>
                </tr>
                <tr>
                    <td>* 이름</td>
                    <td><input type="text" name="userName" maxlength="6" required></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;전화번호</td>
                    <td><input type="text" name="phone" placeholder="- 포함해서 입력"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;이메일</td>
                    <td><input type="email" name="email"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;주소</td>
                    <td><input type="text" name="address"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;관심분야</td>
                    <td colspan="2">
                        <input type="checkbox" name="interest" id="sports" value="운동">
                        <label for="sports">운동</label>

                        <input type="checkbox" name="interest" id="hiking" value="등산">
                        <label for="hiking">등산</label>

                        <input type="checkbox" name="interest" id="fishing" value="낚시">
                        <label for="fishing">낚시</label>
                        <br>
                        <input type="checkbox" name="interest" id="cooking" value="요리">
                        <label for="cooking">요리</label>

                        <input type="checkbox" name="interest" id="game" value="게임">
                        <label for="game">게임</label>

                        <input type="checkbox" name="interest" id="movie" value="영화">
                        <label for="movie">영화</label>
                    </td>
                </tr>
            </table>

            <br><br>

            <div align="center">
                <button type="submit" id="enroll-btn" disabled>회원가입</button>												<!-- disabled처럼 속성명과 값이 같은 경우에는 한번만 입력해도 된다. -->
                <button type="reset">초기화</button>
            </div>

            <br>

        </form>

    </div>
    
    <script>
    	function idCheck(){
    		// 중복확인 버튼 클릭시 사용자가 입력한 아이디 값을 넘겨서 조회 요청(존재하는지 안하는지) => 응답데이터 돌려받기
    		// 1) 사용불가능(NNNNN)일 경우 => alert로 메세지 출력, 다시 입력할 수 있도록 유도
    		// 2) 사용가능(NNNNY)일 경우 => 진짜 사용할껀지 의사를 물어볼꺼임 (confirm 메소드)
    		//						=> 사용하겠다는 경우 => 더이상 아이디 수정 못하게끔 픽스, 회원가입 버튼 활성화
    		//						=> 사용안하겠다는 경우 => 다시 입력할 수 있도록 유도
    		
    		// 입력한 아이디 input 요소 객체
    		const $idInput = $("#enroll-form input[name=userId]");
    		
    		$.ajax({
    			url:"idCheck.me",
    			data:{checkId:$idInput.val()},
    			success:function(result){
    				if(result == "NNNNN"){
    					alert("사용할 수 없는 아이디입니다.");
    					$idInput.val("");
    					$idInput.focus();
    				}else {
    					const useFlag = confirm("해당 아이디는 사용가능합니다. 사용하시겠습니까?");
    					if(useFlag == true){
    						// 사용하겠다
    						$idInput.attr("readonly", true);
    						$("#enroll-btn").attr("disabled", false);
    						//$("#enroll-btn").removeAttr("disabled");
    					}else{
    						// 사용안하겠다
    						$idInput.val("");
        					$idInput.focus();
    					}
    				}
    			},
    			error:function(){
    				console.log("아이디 중복체크용 ajax 통신 실패!");
    			}
    		});
    		
    	}
    
    </script>

</body>
</html>