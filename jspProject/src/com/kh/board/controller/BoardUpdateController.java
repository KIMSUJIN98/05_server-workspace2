package com.kh.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardUpdateController
 */
@WebServlet("/update.bo")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8"); 																						// content 등이 한글이 써지므로 인코딩 작업 필요함. 대소문자 구분 없음.
		
		if(ServletFileUpload.isMultipartContent(request)) {
			
			// 1_1. 전달되는 파일 용량 제한 (int maxSize)
			int maxSize = 10 * 1024 * 1024; // 10메가 바이트
			
			// 1_2. 전달되는 파일을 저장시킬 서버의 폴더 물리적인 경로 (String savePath)
			String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles/"); 					// board_upfiles뒤에 '/' 반드시 필요함! board_upfiles 폴더 '안'에 저장하겠다는 의미!! request --> session --> application에 순차적으로 접근하여 처리함!!
			
			// 2. 전달된 파일명 수정작업 후 서버에 업로드
			// HttpServletRequest (request) => MultipartRequest
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "utf-8", new MyFileRenamePolicy());	// 한줄의 코드만으로 수행됨. => sql문이 잘못되어 DB가 실패하더라도 WebContent에 파일은 업로드될 수 있다. 따라서 지우고자 하면 에러발생시 파일을 지워주는 별도의 코드를 기술해야함!
			
			// 3. 본격적으로 sql문 실행할 때 필요한 값 (요청시 전달값) 뽑아서 vo에 기록
			
			// >> 공통적으로 수행 : Update Board
			int boardNo = Integer.parseInt(multiRequest.getParameter("bno")); 														// getParameter가 String형을 뱉으므로 강제형변환!
			String category = multiRequest.getParameter("category");
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			
			Board b = new Board(); 																									// 별도의 매개변수 생성자 생성 없이 세터 메소드를 활용하고자 함!
			b.setBoardNo(boardNo);
			b.setCategoryNo(category);
			b.setBoardTitle(boardTitle);
			b.setBoardContent(boardContent);
			
			Attachment at = null; // 처음에는 null로 초기화, 넘어온 새 첨부파일이 있을 경우 그때 생성
			
			// 첨부파일이 넘어왔는지 확인하는 방법..
			if(multiRequest.getOriginalFileName("upfile") != null) {																// upfile(boardUpdateForm.jsp에서 첨부파일의 키(name)값)이라는 키값으로 넘어온 파일이 있는지 
				// 새로 넘어온 첨부파일이 있을 경우
				at = new Attachment();
				at.setOriginName(multiRequest.getOriginalFileName("upfile"));														// 원본 파일명을 "upfile"이라는 키를 통해 가져온다.
				at.setChangeName(multiRequest.getFilesystemName("upfile")); 														// 변경된 파일명을 "upfile"이라는 키를 통해 가져온다.
				at.setFilePath("resources/board_upfiles/");
				
				if(multiRequest.getParameter("originFileNo") != null) {
					// 기존에 첨부파일이 있었을 경우 => Update Attachment (기존의 첨부파일 번호 필요)											// DB의 BOARD_NO
					at.setFileNo(Integer.parseInt(multiRequest.getParameter("originFileNo")));	 									// multiRequest.getParameter()는 String을 뱉는데, setFileNo은 int형이므로 강제형변환 필요!
					
				}else {
					// 기존에 첨부파일이 없었을 경우 => Insert Attachment (현재 게시글 번호 필요) 												// DB의 REF_BNO
					at.setRefBno(boardNo);
				}
			}
			
			// 새로 넘어온 첨부파일이 없었다면 at는 null 일 꺼임..
			
			int result = new BoardService().updateBoard(b, at);
			// 새로운 첨부파일 x						 => b, null 				=> Board Update
			// 새로운 첨부파일 o, 기존의 첨부파일 o		 => b, fileNo이 담긴 at		=> Board Update, Attachment Update
			// 새로운 첨부파일 o, 기존의 첨부파일 x		 => b, refBoardNo이 담긴 at	=> Board Update, Attachment Insert
			
			if(result > 0) {
				// 성공! => /jsp/detail.bo?bno=현재게시글번호 url 재요청																	// 상세페이지로 url 재요청
				request.getSession().setAttribute("alertMsg", "성공적으로 수정되었습니다!");
				response.sendRedirect(request.getContextPath() + "/detail.bo?bno=" + boardNo);
			}else {
				// 실패 => 빨간거
				request.setAttribute("errorMsg", "일반게시판 수정 실패!");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			}	
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
