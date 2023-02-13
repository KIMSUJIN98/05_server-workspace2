package com.kh.notice.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import static com.kh.common.JDBCTemplate.*;
import com.kh.notice.model.vo.Notice;

public class NoticeDao {
	
	private Properties prop = new Properties();
	
	public NoticeDao() {
		// xml 파일 읽기
		try {
			prop.loadFromXML(new FileInputStream(
						NoticeDao.class.getResource("/db/sql/notice-mapper.xml").getPath())); // 서버가 배포될때 파일은 WebContent 안에 있는 class파일이 올라감.
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<Notice> selectNoticeList(Connection conn) {
		// select문 => ResultSet객체 => 1행? : 단일객체 | N행? : ArrayList(객체1, 객체2, ..)
		
		ArrayList<Notice> list = new ArrayList<>(); // 텅빈 리스트
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectNoticeList");
		
		try {
			pstmt = conn.prepareStatement(sql); 	// 완성된 sql문 : 대치시킬 ?가 없다!
			rset = pstmt.executeQuery();
			
			while(rset.next()) { 					// if(rset.next())면 한번만 실행되므로, while(rset.next())로 데이터가 없기 전까지는 계속 돌게끔 만들어준다.
				list.add(new Notice(rset.getInt("notice_no"),
									rset.getString("notice_title"),
									rset.getString("user_id"),
									rset.getInt("count"),
									rset.getDate("create_date")
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
	
	public int insertNotice(Connection conn, Notice n) {
		// insert문 => 처리된 행수 => 트랜젝션 처리
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertNotice");
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성 sql
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeContent());
			pstmt.setInt(3, Integer.parseInt(n.getNoticeWriter())); // 문자열인 n.getNoticeWriter()을 숫자형으로 만들기 위해 parseInt 사용
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}

}
