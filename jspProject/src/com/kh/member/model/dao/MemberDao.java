package com.kh.member.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static com.kh.common.JDBCTemplate.*;
import com.kh.member.model.vo.Member;

public class MemberDao {
	
	private Properties prop = new Properties();
	
	public MemberDao() { 																			// 실행시마다 생성자가 한번 호출되면서 xml파일을 다시 읽음으로써 member-mapper.xml에서 sql이 수정되어도 문제없이 반영되게끔 만들어준다.
		String filePath = MemberDao.class.getResource("/db/sql/member-mapper.xml").getPath(); 		// 최상위 폴더 속에 들어있으므로 db 앞에 "/"가 필요함! // WebContent/WEB-INF/classes/db/sql/member-mapper.xml
		try {
			prop.loadFromXML(new FileInputStream(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public Member loginMember(Connection conn, String userId, String userPwd) {
		// select문 => ResultSet 객체 (한행) => Member 객체
		Member m = null;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("loginMember");
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성된 sql문
			
			pstmt.setString(1, userId); 							// 첫번째 물음표를 userId의 값으로 바꿔준다
			pstmt.setString(2, userPwd);							// 두번째 물음표를 userPwd의 값으로 바꿔준다
			
			rset = pstmt.executeQuery(); // 조회 결과가 있다면 한 행 | 조회 결과가 없다면 아무것도 안담김			// 괄호 안은 비우기!!
			
			// 커서 움직이기
			if(rset.next()) {
				m = new Member(rset.getInt("user_no"), 				// 대소문자 구분없음
							   rset.getString("user_id"),
							   rset.getString("user_pwd"),
							   rset.getString("user_name"),
							   rset.getString("phone"),
							   rset.getString("email"),
							   rset.getString("address"),
							   rset.getString("interest"),
							   rset.getDate("enroll_date"),
							   rset.getDate("modify_date"),
							   rset.getString("status"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 반납하기
			/*JDBCTemplate.*/close(rset);
			/*JDBCTemplate.*/close(pstmt);
		}
		
		return m;
		
		
		
	}
	
	public int insertMember(Connection conn, Member m) {
		// insert문 => 처리된 행수 => 트랜젝션 처리
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertMember");
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성된 sql문
			
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getPhone());
			pstmt.setString(5, m.getEmail());
			pstmt.setString(6, m.getAddress());
			pstmt.setString(7, m.getInterest());
			
			result = pstmt.executeUpdate(); // 몇행이 업데이트 됬는지 숫자를 뱉음
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/*JDBCTemplate.*/close(pstmt);
		}
		
		return result;
		
	}
	
	public int updateMember(Connection conn, Member m) {
		// update문 => 처리된 행수 => 트랜젝션 처리
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateMember"); // 미완성된 sql문 (?가 존재함)
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성된 sql문
			
			pstmt.setString(1, m.getUserName());
			pstmt.setString(2, m.getPhone());
			pstmt.setString(3, m.getEmail());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getInterest());
			pstmt.setString(6, m.getUserId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}
	
	public Member selectMember(Connection conn, String userId) {
		// select문 => ResultSet => 한행 => Member 객체					// 아이디는 고유값이므로 한행만 나옴.
		Member m = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectMember");
		try {
			pstmt = conn.prepareStatement(sql); // 미완성
			
			pstmt.setString(1, userId);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) { 										// rset.next()가 true인 경우 => 조회에 성공한 경우
				m = new Member(rset.getInt("user_no"), 				// 대소문자 구분없음
						   rset.getString("user_id"),
						   rset.getString("user_pwd"),
						   rset.getString("user_name"),
						   rset.getString("phone"),
						   rset.getString("email"),
						   rset.getString("address"),
						   rset.getString("interest"),
						   rset.getDate("enroll_date"),
						   rset.getDate("modify_date"),
						   rset.getString("status"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return m;
	}
	
	public int updatePwd(Connection conn, String userId, String userPwd, String updatePwd) {
		// update문 => 처리된 행수 => 트랜젝션 처리
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("updatePwd");
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성된 sql
			pstmt.setString(1, updatePwd);
			pstmt.setString(2, userId);
			pstmt.setString(3, userPwd);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
		
	}
	
	public int deleteMember(Connection conn, String userId, String userPwd) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("deleteMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
		
}
