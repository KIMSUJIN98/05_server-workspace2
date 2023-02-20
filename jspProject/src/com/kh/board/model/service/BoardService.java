package com.kh.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.model.vo.PageInfo;

import static com.kh.common.JDBCTemplate.*;

public class BoardService {
	
	/**
	 * 일반게시판 조회수 조회
	 * @return
	 */
	public int selectListCount() {
		Connection conn = /*JDBCTemplate.*/getConnection();
		
		int listCount = new BoardDao().selectListCount(conn);
		
		close(conn);
		return listCount; // 몇개인지 조회된 결과만 알려주므로 트랜젝션 처리 불필요함.
		
	}
	
	/**
	 * 일반게시판 목록 조회
	 * @param pi
	 * @return
	 */
	public ArrayList<Board> selectList(PageInfo pi){
		Connection conn = getConnection();
		ArrayList<Board> list = new BoardDao().selectList(conn, pi);
		
		close(conn);
		return list; // select이므로 트랜젝션 처리 불필요함.
	}
	
	/**
	 * 일반게시판 목록 조회
	 * @return
	 */
	public ArrayList<Board> selectList(){
		Connection conn = getConnection();
		ArrayList<Board> list = new BoardDao().selectList(conn);
		
		close(conn);
		return list; // select이므로 트랜젝션 처리 불필요함.
	}
	
	/**
	 * 카테고리 목록 조회
	 * @return
	 */
	public ArrayList<Category> selectCategoryList(){
		Connection conn = getConnection();
		ArrayList<Category> list = new BoardDao().selectCategoryList(conn);
		
		close(conn);
		return list;
	}
	
	/**
	 * 게시판 등록
	 * @param b
	 * @param at
	 * @return
	 */
	public int insertBoard(Board b, Attachment at) {
		Connection conn = getConnection();
		
		int result1 = new BoardDao().insertBoard(conn, b);
		int result2 = 1;
		
		if(at != null) { // 첨부파일 있음
			result2 = new BoardDao().insertAttachment(conn, at);
		}
		
		if(result1 > 0 && result2 > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		return result1 * result2;
		
		
	}
	
	/**
	 * 조회수 증가
	 * @param boardNo
	 * @return
	 */
	public int increaseCount(int boardNo) {
		Connection conn = getConnection();
		int result = new BoardDao().increaseCount(conn, boardNo);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
		
	}
	
	/**
	 * 게시글 조회
	 * @param boardNo
	 * @return
	 */
	public Board selectBoard(int boardNo) {
		Connection conn = getConnection();
		Board b = new BoardDao().selectBoard(conn, boardNo);
		// select문이므로 트랜젝션 처리 불필요함.
		close(conn);
		return b;
	}
	
	/**
	 * 첨부파일 조회
	 * @param boardNo
	 * @return
	 */
	public Attachment selectAttachment(int boardNo) {
		Connection conn = getConnection();
		Attachment at = new BoardDao().selectAttachment(conn, boardNo);
		
		close(conn);
		return at;
		
	}
	
	/**
	 * 게시판 수정
	 * @param b
	 * @param at
	 * @return
	 */
	public int updateBoard(Board b, Attachment at) {
		Connection conn = getConnection();
		
		int result1 = new BoardDao().updateBoard(conn, b);							// 첨부파일 유뮤와 상관없이 공통적으로 수행해야 하는 작업임. 첨부파일이 없는 경우 이 작업만 수행함!
		
		int result2 = 1;
		
		if(at != null) { // 새로운 첨부파일이 있었을 경우
			
			if(at.getFileNo() != 0) { // 기존에 첨부파일이 있었을 경우						// DB의 FILE_NO은 시퀀스로 자동할당 (첨부파일이 없는 경우에 해당되는 기본값은 0이다)되므로 0이 아니면 첨부파일이 있는 경우에 해당함.
				result2 = new BoardDao().updateAttachment(conn, at);
			}else { // 기존에 첨부파일이 없는 경우 new!!
				result2 = new BoardDao().insertNewAttachment(conn, at);
			}
		}
		
		if(result1 > 0 && result2 > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result1 * result2;
		
		
		
	}
	

	
	
}
