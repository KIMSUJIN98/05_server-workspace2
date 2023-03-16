package com.kh.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.vo.Member;
import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeInsertController
 */
@WebServlet("/insert.no")
public class NoticeInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String noticeTitle = request.getParameter("title");
		String noticeContent = request.getParameter("content");
		
		// 로그인한 회원 정보를 얻어내는 방법
		// 1. input type = "hidden" 으로 애초에 요청시 숨겨서 전달하기
		// 2. session에 담겨있는 loginUser 활용하는 방법
		
		HttpSession session = request.getSession();
		int userNo = ((Member)session.getAttribute("loginUser")).getUserNo(); // getAttribute는 Object 타입을 반환하므로 다운캐스팅 후, getUserNo를 가져온다.
		
		Notice n = new Notice();
		n.setNoticeTitle(noticeTitle);
		n.setNoticeContent(noticeContent);
		
		// n.setNoticeWriter(userNo + ""); 						// NoticeWriter는 String형만 담을 수 있으므로, int 타입인 userNo를 String형으로 바꿔주기 위해 + ""를 추가하여 문자열로 변경함.
		n.setNoticeWriter(String.valueOf(userNo)); 				// int => String으로 변경해주는 메소드 valueOf
		
		int result = new NoticeService().insertNotice(n);
		
		if(result > 0) { // 성공 => 목록조회 화면으로 url 재요청
			response.sendRedirect(request.getContextPath() + "/list.no");
		}else { // 실패 => 에러문구(공지사항 등록 실패!) 담아서 에러메세지 포워딩(에러페이지)
			request.setAttribute("errorMsg", "공지사항 등록 실패!");					// errorPage.jsp를 확인해보면 request로 받고 있으므로 session.setAttribute가 아닌 request.setAttribute여야 함.
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
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
