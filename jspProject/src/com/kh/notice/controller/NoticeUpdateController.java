package com.kh.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeUpdateController
 */
@WebServlet("/update.no")
public class NoticeUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 제목, 내용, 글번호
		request.setCharacterEncoding("UTF-8");
		
		int noticeNo = Integer.parseInt(request.getParameter("num"));
		String noticeTitle = request.getParameter("title");
		String noticeContent = request.getParameter("content");
		
		Notice n = new Notice(); 			// 빈 객체
		
		n.setNoticeNo(noticeNo); 			// 공지사항 번호가 담김
		n.setNoticeTitle(noticeTitle); 		// 제목이 담김
		n.setNoticeContent(noticeContent); 	// 내용이 담김
		
		int result = new NoticeService().updateNotice(n);	
		
		// HttpSession session = request.getSession(); // 세션을 if문이 true일때만 사용하므로 이와같이 작성하지 않고 if문 true 실행문 내부에 바로 작성함.
		if(result > 0) { // 성공 => /jsp/detail.no?num=현재글번호 => 현재 공지글에 대한 상세보기 페이지
			request.getSession().setAttribute("alertMsg", "성공적으로 공지사항 수정 되었습니다.");
			response.sendRedirect(request.getContextPath() + "/detail.no?num=" + noticeNo);	
		}else { // 실패
			request.setAttribute("errorMsg", "공지사항 수정 실패!");
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
