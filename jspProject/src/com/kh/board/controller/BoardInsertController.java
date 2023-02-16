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
 * Servlet implementation class BoardInsertController
 */
@WebServlet("/insert.bo")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		// 일반방식이 아닌 multipart/form-data로 전송하는 경우 request로 부터 값 뽑기 불가!!
		// String boardTitle = request.getParameter("title"); -- 일반방식 예시
		
		// enctype이 multipart/form-data로 잘 전송되었을 경우 전반적인 내용들이 수행되도록
		if(ServletFileUpload.isMultipartContent(request)) {
			//System.out.println("잘 실행되나? 요청 잘 되나?");
			
			// 0. 파일 업로드를 위한 라이브러리 : cos.jar(com.oreilly.servlet 의 약자)
			// 							https://www.servlets.com 접속해서 다운로드
			
			// 1. 전달되는 파일을 처리할 작업내용 (전달되는 파일의 용량 제한, 전달된 파일을 저장시킬 폴더 경로)
			// 1_1) 전달되는 파일의 용량 제한 (int maxSize => byte 단위) => 10Mbyte로 제한
			
			/*
			 * byte => kbyte => mbyte => gbyte => tbyte																		// 바이트, 키로바이트, 메가바이트, 기가바이트, 테라바이트
			 * 1kbyte  == 1024byte
			 * 1mbyte  == 1024kbyte == 1024 * 1024 byte
			 * 10mbyte == 10*1024*1024 byte
			 * 
			 */
			
			int maxSize = 10*1024*1024; 																					// 10Mbyte로 제한
			
			// 1_2) 전달된 파일을 저장시킬 폴더의 경로 알아내기(String savePath)
			String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles/"); 			// board_upfiles 뒤에 '/' 반드시 필요! => 해당 폴더 '안'에 저장하겠다!
			//System.out.println(savePath); 																				// C:\05_server-workspace2\jspProject\WebContent\resources\board_upfiles\
			
			
			// 2. 전달된 파일의 파일명 수정 및 서버에 업로드(폴더에 저장) 작업
			
			/*
			 * >> HttpServletRequest request => MultipartRequest multiRequest 변환
			 * 
			 * 위 구문 한 줄 실행만으로 넘어온 첨부파일이 해당 폴더에 무조건 업로드 됨!!
			 * 
			 * 단, 업로드시 파일명 수정해주는게 일반적임! 그래서 파일명 수정시켜주는 객체 제시해야됨
			 * => 같은 파일명이 존재할 경우 덮어씌워질 수 있고, 파일명에 한글/특문/띄어쓰기가 포함 될 경우 서버에 따라 문제 발생 					// 덮어쓰기 방지를 위해 유니크하게 파일명을 수정시켜준다.
			 * 
			 * 기본적으로 파일명이 안겹치도록 수정작업 해주는 객체 있음
			 * => DefaultFileRenamePolicy 객체 (cos.jar 에서 제공하는 객체)														// cos.jar를 다운로드 받으면 사용할 수 있는 객체(자바에서 제공하는거 아님)
			 * => 내부적으로 해당 클래스에 rename() 메소드 실행 되면서 파일명 수정된 후 업로드
			 * 	  rename(원본파일){
			 * 		기존에 동일한 파일명이 존재할 경우
			 * 		파일명 뒤에 카운팅 된 숫자를 붙여줌
			 * 		ex) aaa.jpg, aaa1.jpg, aaa2.jpg
			 * 			하늘사진.jpg, 하늘사진1.jpg, 하늘사진2.jpg
			 * 		return 수정파일
			 * 
			 * 	  }
			 * 
			 *    나만의 방식대로 절대 안겹치도록 rename 할 수 있게 나만의 FileRenamePolicy 클래스 (rename 메소드 재정의) 만들기!
			 * 	  com.kh.common.MyFileRenamePolicy 클래스 만들기
			 * 
			 */
			
			// MultipartRequest multipartRequest = new MultipartRequest(request, 저장시킬폴더의경로, 용량제한, 인코딩값, 파일명수정시켜주는객체);
			MultipartRequest multipartRequest = new MultipartRequest(request, savePath, maxSize, "utf-8", new MyFileRenamePolicy());
			// 여기까지 하고 실행
			
			// 3. DB에 기록할 데이터를 뽑아서 vo에 주섬주섬 담기
			//  > 카테고리 번호, 제목, 내용, 작성자회원번호 뽑아서 Board insert
			//  > 넘어온 첨부파일이 있다면 원본명, 수정명, 저장폴더 경로 Attachment insert
			
			String category = multipartRequest.getParameter("category");												// request.getParameter("") 사용불가
			String boardTitle = multipartRequest.getParameter("title");
			String boardContent = multipartRequest.getParameter("content");
			String boardWriter = multipartRequest.getParameter("userNo");

			Board b = new Board();
			b.setCategoryNo(category);
			b.setBoardTitle(boardTitle);
			b.setBoardContent(boardContent);
			b.setBoardWriter(boardWriter);
			
			Attachment at = null; // 처음에는 null로 초기화, 넘어온 첨부파일이 있다면 생성 										// Attachment.java 클래스파일 생성하여 변수선언, 기본/매개변수생성자, 게터/세터, toString 구문 작성하기
			// multipartRequest.getOriginalFileName("키"); : 넘어온 첨부파일 있었을 경우 "원본명" | 없었을 경우 null
			if(multipartRequest.getOriginalFileName("upfile") != null) {
				at = new Attachment();
				at.setOriginName(multipartRequest.getOriginalFileName("upfile"));
				at.setChangeName(multipartRequest.getFilesystemName("upfile"));
				at.setFilePath("resources/board_upfiles/");
			}
			
			// 4. 서비스 요청 (요청처리)
			int result = new BoardService().insertBoard(b, at);
			
			// 5. 응답뷰 지정
			if(result > 0) {
				// 성공 => /jps/list.bo?cpage=1 url 재요청 => 목록페이지
				response.sendRedirect(request.getContextPath() + "/list.bo?cpage=1");
				
			}else {
				// 빨간거
				request.setAttribute("errorMsg", "일반게시판 등록 실패!");
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
