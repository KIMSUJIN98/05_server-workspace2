package com.kh.member.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
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
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return m;
		
		
		
	}
		
}
