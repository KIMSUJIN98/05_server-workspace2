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
 * Servlet implementation class NoticeDetailController
 */
@WebServlet("/detail.no")
public class NoticeDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int noticeNo = Integer.parseInt(request.getParameter("num")); 		// num은 String형이므로 int로 형변환 필요함.
		
		// 조회수 증가
		int result = new NoticeService().increaseCount(noticeNo); 			// 사용자가 선택한 공지사항의 조회수만 증가되도록 noticeNo를 가져가야한다.
		
		if(result > 0) { // 성공 == 조회가능한 공지사항 맞다 => 상세페이지

			// 해당 공지사항 조회용 서비스
			Notice n = new NoticeService().selectNotice(noticeNo);			// 상세정보의 경우, noticeNo를 가진 Notice 단일객체 하나만 필요하다. (NoticeList 아님.)
			
			request.setAttribute("notice", n);
			request.getRequestDispatcher("views/notice/noticeDetailView.jsp").forward(request, response); 			// ctrl+c (noticeDetailView.jsp) => ctrl+shift+r 검색으로 오탈자 체크
			
			
		}else { // 실패 == 조회 불가능한 공지사항
			// 에러문구 보여지는 에러페이지
			request.setAttribute("errorMsg", "공지사항 상세 조회 실패!");
			request.getRequestDispatcher("views/common/errorPage.jsp")
							.forward(request, response);
			
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
