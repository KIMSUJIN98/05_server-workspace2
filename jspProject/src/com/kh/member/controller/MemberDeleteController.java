package com.kh.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;

/**
 * Servlet implementation class MemberDeleteController
 */
@WebServlet("/delete.me")
public class MemberDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		int result = new MemberService().deleteMember(userId, userPwd);
		HttpSession session = request.getSession();
		
		if(result > 0) {
			session.setAttribute("alertMsg", "성공적으로 회원 탈퇴 되었습니다. 그동안 이용해주셔서 감사합니다.");
			// session.invalidate(); 세션이 전부 만료되어 alertMsg 출력이 안됨!
			session.removeAttribute("loginUser"); 									// "alertMsg" 세션은 살려야하므로..
			response.sendRedirect(request.getContextPath()); 						// url 재요청		
		}else {
			session.setAttribute("alertMsg", "회원 탈퇴에 실패했습니다.");
			response.sendRedirect(request.getContextPath() + "/myPage.me"); 		// url 재요청
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
