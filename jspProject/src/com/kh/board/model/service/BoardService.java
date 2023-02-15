package com.kh.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Board;
import com.kh.common.model.vo.PageInfo;

import static com.kh.common.JDBCTemplate.*;

public class BoardService {
	
	public int selectListCount() {
		Connection conn = /*JDBCTemplate.*/getConnection();
		
		int listCount = new BoardDao().selectListCount(conn);
		
		close(conn);
		return listCount; // 몇개인지 조회된 결과만 알려주므로 트랜젝션 처리 불필요함.
		
	}
	
	public ArrayList<Board> selectList(PageInfo pi){
		Connection conn = getConnection();
		ArrayList<Board> list = new BoardDao().selectList(conn, pi);
		
		close(conn);
		return list; // select이므로 트랜젝션 처리 불필요함.
	}
	
	public ArrayList<Board> selectList(){
		Connection conn = getConnection();
		ArrayList<Board> list = new BoardDao().selectList(conn);
		
		close(conn);
		return list; // select이므로 트랜젝션 처리 불필요함.
	}
	

	
	
}
