package com.kh.board.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.board.model.vo.Reply;
import com.kh.common.model.vo.PageInfo;

import static com.kh.common.JDBCTemplate.*;

/**
 * BoardDao.java
 * @author ksj
 * @since 2023.02.20
 * @version 1.0
 */
public class BoardDao {
	
	private Properties prop = new Properties();
	
	public BoardDao() {
		try {
			prop.loadFromXML(new FileInputStream(BoardDao.class.getResource("/db/sql/board-mapper.xml").getPath())); 		// load가 아닌 loadFromXML임에 유의!
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 게시판 조회수 조회
	 * @param conn
	 * @return
	 */
	public int selectListCount(Connection conn) {
		// select문 => ResultSet 객체 (한개) => int형 변수
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectListCount");
		
		try {
			pstmt = conn.prepareStatement(sql); 		// 완성된 sql
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt("count"); 		// 쿼리문에서 별칭지정을 안했다면 COUNT(*)과 같이 긴 내용이 들어갔어야 함.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/*JDBCTemplate.*/close(rset);
			close(pstmt);
		}
		
		return listCount;
		
	}
	
	/**
	 * 게시판 목록 조회
	 * @param conn
	 * @param pi
	 * @return
	 */
	public ArrayList<Board> selectList(Connection conn, PageInfo pi) {
		// select문 => ResultSet객체 (여러행) => ArrayList<Board>
		
		ArrayList<Board> list = new ArrayList<Board>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectList");
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성 sql
			
			/*
			 * currentPage(현재페이징번호) : 1 => 시작값 : 1  | 끝값 : 10
			 * currentPage(현재페이징번호) : 2 => 시작값 : 11 | 끝값 : 20
			 * currentPage(현재페이징번호) : 3 => 시작값 : 21 | 끝값 : 30
			 * 
			 * 시작값 : (currentPage - 1) * boardLimit + 1
			 * 끝값  : 시작값 + boardLimit - 1
			 */
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Board(rset.getInt("board_no"),
								   rset.getString("category_name"),
								   rset.getString("board_title"),
								   rset.getString("user_id"),
								   rset.getInt("count"),
								   rset.getString("create_date")
								   ));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
		
	}
	
	/**
	 * 게시판 목록 조회 - 부트스트랩 사용
	 * @param conn
	 * @return
	 */
	public ArrayList<Board> selectList(Connection conn) {
		// select문 => ResultSet객체 (여러행) => ArrayList<Board>
		
		ArrayList<Board> list = new ArrayList<Board>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectList2");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Board(rset.getInt("board_no"),
								   rset.getString("category_name"),
								   rset.getString("board_title"),
								   rset.getString("user_id"),
								   rset.getInt("count"),
								   rset.getString("create_date")
								   ));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
		
	}
	
	/**
	 * 카테고리 목록 조회
	 * @param conn
	 * @return
	 */
	public ArrayList<Category> selectCategoryList(Connection conn){
		// select문 => ResultSet객체 => 여러행 => ArrayList
		ArrayList<Category> list = new ArrayList<Category>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectCategoryList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Category(rset.getInt("category_no"),
									  rset.getString("category_name")
									  ));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;		
		
	}
	
	/**
	 * 게시판 등록
	 * @param conn
	 * @param b
	 * @return
	 */
	public int insertBoard(Connection conn, Board b) {
		// insert문 => 처리된 행수 => 트랜젝션 처리
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(b.getCategoryNo()));			// String 형태를 int 형으로 강제형변환
			pstmt.setString(2, b.getBoardTitle());
			pstmt.setString(3, b.getBoardContent());
			pstmt.setInt(4, Integer.parseInt(b.getBoardWriter()));
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 첨부파일 등록
	 * @param conn
	 * @param at
	 * @return
	 */
	public int insertAttachment(Connection conn, Attachment at) {
		// insert문 => 처리된 행수 => 트랜젝션 처리
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setString(3, at.getFilePath());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 조회수 증가
	 * @param conn
	 * @param boardNo
	 * @return
	 */
	public int increaseCount(Connection conn, int boardNo) {
		// update문 => 처리된 행수 => 트랜젝션 처리
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("increaseCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}
	
	/**
	 * 게시글 조회
	 * @param conn
	 * @param boardNo
	 * @return
	 */
	public Board selectBoard(Connection conn, int boardNo) {
		// select문 => ResultSet 객체 => 한행 => Board
		Board b = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				b = new Board(rset.getInt("board_no"),
							  rset.getString("category_name"),
							  rset.getString("board_title"),
							  rset.getString("board_content"),
							  rset.getString("user_id"),
							  rset.getString("create_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return b;
	}
	
	/**
	 * 첨부파일 조회
	 * @param conn
	 * @param boardNo
	 * @return
	 */
	public Attachment selectAttachment(Connection conn, int boardNo) {
		// select문 => ResultSet 객체 반환 => 한행 => Attachment 객체
		Attachment at = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				at = new Attachment(); // 기본생성자 만들고, 세터 메소드를 사용함으로써 매개변수 생성자를 별도로 만들 필요가 없어짐.
				at.setFileNo(rset.getInt("file_no"));
				at.setOriginName(rset.getString("origin_name"));
				at.setChangeName(rset.getString("change_name"));
				at.setFilePath(rset.getString("file_path"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return at;
		
	}
	
	/**
	 * 첨부파일 리스트 조회
	 * @param conn
	 * @param boardNo
	 * @return
	 */
	public ArrayList<Attachment> selectAttachmentList(Connection conn, int boardNo){		// 쿼리는 기존에 존재하던 것을 사용하지만 반환형이 다르므로 메소드 새로 만듬!
		ArrayList<Attachment> list = new ArrayList<Attachment>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Attachment at = new Attachment();
				at.setChangeName(rset.getString("change_name"));
				at.setFilePath(rset.getString("file_path"));
				
				list.add(at);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}
	
	/**
	 * 게시글 수정
	 * @param conn
	 * @param b
	 * @return
	 */
	public int updateBoard(Connection conn, Board b) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(b.getCategoryNo())); 							// b.getCategoryNo()가 String 형태이므로 강제형변환 필요!
			pstmt.setString(2, b.getBoardTitle());
			pstmt.setString(3, b.getBoardContent());
			pstmt.setInt(4, b.getBoardNo());
		
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 첨부파일 수정
	 * @param conn
	 * @param at
	 * @return
	 */
	public int updateAttachment(Connection conn, Attachment at) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("updateAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setString(3, at.getFilePath());
			pstmt.setInt(4, at.getFileNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 첨부파일 등록 - 게시글 수정
	 * @param conn
	 * @param at
	 * @return
	 */
	public int insertNewAttachment(Connection conn, Attachment at) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertNewAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, at.getRefBno());
			pstmt.setString(2, at.getOriginName());
			pstmt.setString(3, at.getChangeName());
			pstmt.setString(4, at.getFilePath());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	/**
	 * 사진게시판 등록
	 * @param conn
	 * @param b
	 * @return
	 */
	public int insertThBoard(Connection conn, Board b) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertThBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b.getBoardTitle());
			pstmt.setString(2, b.getBoardContent());
			pstmt.setInt(3, Integer.parseInt(b.getBoardWriter()));					// b.getBoardWriter()는 String 형이므로 강제형변환 필요!
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}
	
	/**
	 * 첨부파일 등록 - 사진게시판 등록
	 * @param conn
	 * @param list
	 * @return
	 */
	public int insertAttachmentList(Connection conn, ArrayList<Attachment> list) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertAttachmentList");
		
		try {
			
			for(Attachment at :list) {
				// 미완성
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, at.getOriginName());
				pstmt.setString(2, at.getChangeName());
				pstmt.setString(3, at.getFilePath());
				pstmt.setInt(4, at.getFileLevel());
				
				// 실행
				result = pstmt.executeUpdate();										// 쿼리가 여러번 돌면서 업데이트 될 것임.			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}
	
	public ArrayList<Board> selectThumbnailList(Connection conn){
		ArrayList<Board> list = new ArrayList<Board>();
		PreparedStatement pstmt = null;
		ResultSet rset = null; 														// 조회용이므로 ResultSet 필요!
		
		String sql = prop.getProperty("selectThumbnailList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			// 조회 결과를 뽑아서 list에 담아서 반환
			while(rset.next()) {
				list.add(new Board(rset.getInt("board_no"),
						  rset.getString("board_title"),
						  rset.getInt("count"),
						  rset.getString("titleimg")
						  ));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}
	
	public ArrayList<Reply> selectReplyList(Connection conn, int boardNo){
		ArrayList<Reply> list = new ArrayList<Reply>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectReplyList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Reply(rset.getInt("reply_no"),
								   rset.getString("reply_content"),
								   rset.getString("user_id"),
								   rset.getString("create_date")
								   ));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}
	
	public int insertReply(Connection conn, Reply r) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertReply");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, r.getReplyContent());
			pstmt.setInt(2, r.getRefBoardNo());
			pstmt.setInt(3, Integer.parseInt(r.getReplyWriter()));
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
}
